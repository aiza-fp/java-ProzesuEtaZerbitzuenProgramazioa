package prozesuAnitzekoProgramazioa_1.komunikazioa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProzesuB {
    public static void main(String[] args) {
        try {
            // Mensaje al usuario (salida estándar)
            System.out.println("Proceso B: Esperando el resultado de Proceso A...");

            // Leer el resultado desde la entrada estándar (solo el resultado)
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String result = reader.readLine();  // Leer el resultado de A

            // Mostrar el resultado al usuario (salida estándar)
            System.out.println("Proceso B: Recibí el resultado " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
