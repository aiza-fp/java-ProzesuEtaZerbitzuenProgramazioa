package sarekoKomunikazioakProgramatzea_3.bezeroZerbitzariTCP;

import java.io.*;
import java.net.*;
/**
 * Klase honek zerbitzari bat jartzen du eskariak entzuten makinaren 5000 portuan
 */
public class ZerbitzariaTCP {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Zerbitzaria 5000 portuan entzuten...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Bezeroa konektatu da: " + socket.getInetAddress());

                // Sortu sarrera eta irteera Stream-ak
                BufferedReader sarrera = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
                PrintWriter irteera = new PrintWriter(socket.getOutputStream(), true);

                // Irakurri bezeroaren mezua
                String mezua = sarrera.readLine();
                System.out.println("Bezerotik jasotako mezua: " + mezua);

                // Bidali erantzuna bueltan bezeroari
                irteera.println("Zerbitzariak mezua jaso du: " + mezua);

                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}