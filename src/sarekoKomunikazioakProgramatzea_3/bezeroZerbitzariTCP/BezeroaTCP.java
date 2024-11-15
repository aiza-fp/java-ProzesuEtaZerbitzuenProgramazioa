package sarekoKomunikazioakProgramatzea_3.bezeroZerbitzariTCP;

import java.io.*;
import java.net.*;
/**
 * Klase hau makina lokalean 5000 portuan entzuten dagoen zerbitzari batera
 * konektatzen da, mezu bat bidaltzen dio eta erantzuna itxaroten du.
 */
public class BezeroaTCP {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000)) {
            // Sortu sarrera eta irteera Stream-ak
            PrintWriter irteera = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader sarrera = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

            // Bidali mezua zerbitzariari
            irteera.println("BEZEROAREN MEZUA");

            // Irakurri zerbitzariaren erantzuna
            String erantzuna = sarrera.readLine();
            System.out.println("Zerbitzariaren erantzuna: " + erantzuna);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} 