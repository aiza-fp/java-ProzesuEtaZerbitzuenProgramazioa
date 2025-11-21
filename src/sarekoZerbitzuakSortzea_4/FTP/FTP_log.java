package sarekoZerbitzuakSortzea_4.FTP;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 * Liburutegia:
 * 
 * https://dlcdn.apache.org//commons/net/binaries/commons-net-3.11.1-bin.zip
 * 
 * Barruan dagoen commons-net-3.11.1.jar gehitu proiektura
 * 
 * FTP zerbitzari bezala Filezilla Server instalatu daiteke, portua 14148
 */
public class FTP_log {
    public static void main(String[] args) {
        String server = "localhost";
        int port = 21;
        String user = "gonbidatua";
        String pass = "pasahitza";

        FTPClient ftpClient = new FTPClient();
        // Add protocol listener to print all FTP commands and server replies (very useful for debugging 550 errors)
        ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out), true));
        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);

            System.out.println("Konektatuta: " + ftpClient.getReplyString());
            System.out.println("Reply code: " + ftpClient.getReplyCode());

            // Use passive mode and set file type (binary is safe for most files)
            ftpClient.enterLocalPassiveMode();
            try {
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            } catch (IOException e) {
                System.out.println("Could not set file type: " + e.getMessage());
            }

            // Print current working directory
            try {
                String pwd = ftpClient.printWorkingDirectory();
                System.out.println("Remote working dir: " + pwd);
            } catch (IOException e) {
                System.out.println("Could not get working dir: " + e.getMessage());
            }

            // List files in the remote directory to get more info
            try {
                System.out.println("Listing root (/) files:");
                FTPFile[] rootFiles = ftpClient.listFiles("/");
                for (FTPFile f : rootFiles) {
                    System.out.println((f.isDirectory() ? "[DIR] " : "[FILE]") + f.getName());
                }

                System.out.println("Listing /karpeta files (if exists):");
                FTPFile[] karpetaFiles = ftpClient.listFiles("/karpeta");
                for (FTPFile f : karpetaFiles) {
                    System.out.println((f.isDirectory() ? "[DIR] " : "[FILE]") + f.getName());
                }
            } catch (IOException e) {
                System.out.println("Could not list files: " + e.getMessage());
            }

            // Try to download the file, and if it fails provide more diagnostics and try alternatives
            String remoteFile = "/karpeta/fitxategia.txt";
            File downloadFile = new File("fitxategia_lokalean.txt");
            try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(downloadFile))) {
                System.out.println("Attempting to retrieve: " + remoteFile);
                if (ftpClient.retrieveFile(remoteFile, outputStream)) {
                    System.out.println("Fitxategia deskargatu da.");
                } else {
                    System.out.println("Fitxategia deskargatzean errorea. Reply: " + ftpClient.getReplyString() + " (code: " + ftpClient.getReplyCode() + ")");

                    // Try alternative paths
                    String[] alternatives = {"karpeta/fitxategia.txt", "fitxategia.txt"};
                    for (String alt : alternatives) {
                        System.out.println("Trying alternative path: " + alt);
                        File altFile = new File("fitxategia_lokalean_" + alt.replace('/', '_'));
                        try (OutputStream out2 = new BufferedOutputStream(new FileOutputStream(altFile))) {
                            if (ftpClient.retrieveFile(alt, out2)) {
                                System.out.println("Downloaded using alternative path: " + alt);
                                break;
                            } else {
                                System.out.println("Alternative failed. Reply: " + ftpClient.getReplyString() + " (code: " + ftpClient.getReplyCode() + ")");
                            }
                        } catch (IOException ioe) {
                            System.out.println("I/O when trying alternative " + alt + ": " + ioe.getMessage());
                        }
                    }

                    // Additional diagnostics: check listNames, try retrieveFileStream and MLST
                    try {
                        System.out.println("Diagnostic: listNames for: " + remoteFile);
                        String[] names = ftpClient.listNames(remoteFile);
                        if (names != null) {
                            for (String n : names) System.out.println("listNames entry: " + n);
                        } else {
                            System.out.println("listNames returned null");
                        }

                        System.out.println("Diagnostic: retrieveFileStream for: " + remoteFile);
                        InputStream in = ftpClient.retrieveFileStream(remoteFile);
                        if (in != null) {
                            byte[] buf = new byte[64];
                            int r = in.read(buf);
                            System.out.println("Read bytes from stream: " + r);
                            in.close();
                            boolean completed = ftpClient.completePendingCommand();
                            System.out.println("completePendingCommand returned: " + completed + "; reply: " + ftpClient.getReplyString());
                        } else {
                            System.out.println("retrieveFileStream returned null. Reply: " + ftpClient.getReplyString() + " (code: " + ftpClient.getReplyCode() + ")");
                        }

                        int mlst = ftpClient.sendCommand("MLST", remoteFile);
                        System.out.println("MLST command reply: " + mlst + " - " + ftpClient.getReplyString());
                    } catch (IOException ioe) {
                        System.out.println("Diagnostic I/O error: " + ioe.getMessage());
                    }

                    // As final diagnostic, try to change into the directory and list it
                    try {
                        if (ftpClient.changeWorkingDirectory("/karpeta")) {
                            System.out.println("Changed to /karpeta. Listing files there:");
                            FTPFile[] files = ftpClient.listFiles();
                            for (FTPFile f : files) {
                                System.out.println((f.isDirectory() ? "[DIR] " : "[FILE]") + f.getName());
                            }
                        } else {
                            System.out.println("Could not change to /karpeta. Reply: " + ftpClient.getReplyString() + " (code: " + ftpClient.getReplyCode() + ")");
                        }
                    } catch (IOException ioe) {
                        System.out.println("Error changing/listing /karpeta: " + ioe.getMessage());
                    }
                }
            }

            ftpClient.logout();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}