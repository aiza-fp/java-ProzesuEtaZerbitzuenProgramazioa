package prozesuAnitzekoProgramazioa_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BatuketaAbiarazlea {
    public static void main(String[] args) {
        try {
            // Specify the classpath (e.g., "bin" if using Eclipse, adjust accordingly)
            String classpath = "bin";  // Adjust this if necessary (e.g., `.` if in the same directory)

            // Batuketa klasea abiarazi
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "java", "-cp", classpath, "prozesuAnitzekoProgramazioa_1.Batuketa", "6", "7");

            // Start the process
            Process process = processBuilder.start();

            // Capture the standard output (stdout) from the process
            BufferedReader stdOutput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            // Capture the error output (stderr) from the process
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String line;

            // Read and print the standard output (if any)
            System.out.println("Prozesuaren mezua:");
            while ((line = stdOutput.readLine()) != null) {
                System.out.println(line);
            }

            // Read and print the error output (if any)
            System.out.println("Errore mezua (baldin badago):");
            while ((line = stdError.readLine()) != null) {
                System.out.println(line);
            }

            // Wait for the process to finish
            int exitCode = process.waitFor();
            System.out.println("\nProzesua bukatu da irteera kode honekin: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}