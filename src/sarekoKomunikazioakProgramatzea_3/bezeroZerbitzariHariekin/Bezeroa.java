package sarekoKomunikazioakProgramatzea_3.bezeroZerbitzariHariekin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
/**
 * Klase hau makina lokalean 5000 portuan entzuten dagoen zerbitzari batera
 * konektatzen da 'bezeroKopurua' aldiz, mezu bat bidaltzen dio
 * eta erantzuna itxaroten du.
 */
public class Bezeroa {
    public static void main(String[] args) {
        // Sortu hainbat bezero simulatu
        int bezeroKopurua = 5; // Aldatu nahi duzun bezero kopurura
        Thread[] bezeroak = new Thread[bezeroKopurua];

        for (int i = 0; i < bezeroKopurua; i++) {
            final int bezeroId = i + 1;
            bezeroak[i] = new Thread(() -> {
                bezeroaExekutatu(bezeroId);
            });
            bezeroak[i].start();
        }

        // Itxaron bezero guztiak bukatu arte
        for (Thread t : bezeroak) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void bezeroaExekutatu(int bezeroId) {
        try (Socket socket = new Socket("localhost", 5000)) {
            // Sortu sarrera eta irteera Stream-ak
            PrintWriter irteera = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader sarrera = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

            // Bidali mezua zerbitzariari
            String mezua = "BEZERO " + bezeroId + " -ren MEZUA";
            irteera.println(mezua);
            System.out.println("Bezero " + bezeroId + " mezua bidali du: " + mezua);

            // Irakurri zerbitzariaren erantzuna
            String erantzuna = sarrera.readLine();
            System.out.println("Bezero " + bezeroId + " erantzuna jaso du: " + erantzuna);

        } catch (IOException e) {
            System.err.println("Errorea " + bezeroId + " bezeroan: " + e.getMessage());
        }
    }
}