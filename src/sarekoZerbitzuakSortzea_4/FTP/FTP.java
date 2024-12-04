package sarekoZerbitzuakSortzea_4.FTP;

import org.apache.commons.net.ftp.FTPClient;
import java.io.*;

/**
 * Liburutegia:
 * 
 * https://dlcdn.apache.org//commons/net/binaries/commons-net-3.11.1-bin.zip
 * 
 * Barruan dagoen commons-net-3.11.1.jar gehitu proiektura
 * 
 * FTP zerbitzari bezala Filezilla Server instalatu daiteke, portua 14148
 */
public class FTP {
    public static void main(String[] args) {
        String server = "localhost";
        int port = 21;
        String user = "gonbidatua";
        String pass = "pasahitza";

        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);

            System.out.println("Konektatuta: " + ftpClient.getReplyString());

            ftpClient.enterLocalPassiveMode();

            // Fitxategi bat deskargatzea
            String remoteFile = "/karpeta/fitxategia.txt";
            File downloadFile = new File("fitxategia_lokalean.txt");
            try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(downloadFile))) {
                if (ftpClient.retrieveFile(remoteFile, outputStream)) {
                    System.out.println("Fitxategia deskargatu da.");
                } else {
                    System.out.println("Fitxategia deskargatzean errorea.");
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

