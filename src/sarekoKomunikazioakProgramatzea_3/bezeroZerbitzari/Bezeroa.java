package sarekoKomunikazioakProgramatzea_3.bezeroZerbitzari;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
/**
 * Klase hau makina lokalean 5000 portuan entzuten dagoen zerbitzari batera
 * konektatzen da, mezu bat bidaltzen dio eta erantzuna itxaroten du.
 */
public class Bezeroa {
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