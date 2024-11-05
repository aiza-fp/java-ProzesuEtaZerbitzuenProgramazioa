package sarekoKomunikazioakProgramatzea_3.bezeroZerbitzariHariekin;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * Klase honek zerbitzari bat jartzen du eskariak entzuten makinaren 5000 portuan
 */
public class Zerbitzaria {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Zerbitzaria 5000 portuan entzuten...");

            while (true) {
                Socket socket = serverSocket.accept();
                // Sortu hari berri bat bezero bakoitzarentzat
                BezeroKudeatzailea bezeroKudeatzailea = new BezeroKudeatzailea(socket);
                new Thread(bezeroKudeatzailea).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}