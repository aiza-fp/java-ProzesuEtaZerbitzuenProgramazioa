package prozesuAnitzekoProgramazioa_1.komunikazioa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProzesuB {
    public static void main(String[] args) {
        try {
        	
            // Erabiltzaileari mezua (irteera estandarra, SYSTEM.OUT)
            System.out.println("B Prozesua: A Prozesuaren emaitza itxaroten...");

            // Emaitza sarrera estandarretik irakurri (SYSTEM.IN)
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String emaitza = reader.readLine();  // Prozesu Nagusiak bidali dion emaitza irakurri

            // Erabiltzaileari mezua (irteera estandarra, SYSTEM.OUT)
            System.out.println("B Prozesua: Jasotako emaitza: " + emaitza);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
