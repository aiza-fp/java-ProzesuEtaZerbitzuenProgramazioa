package programazioSegurukoTeknikakErabiltzea_5;

import java.util.HashMap;
import java.util.Map;

/**
 * Sarbide kontrola eta erabiltzaile rolen adibide sinplea
 * - Datu-baserik gabe
 * - Saioa mantendu gabe
 * - Kredentzialak eskatu gabe
 * - Zifratu gabe
 */
public class SarbideKontrolSinplea {
    private static Map<String, String> erabiltzaileak = new HashMap<>();
    private static Map<String, String> rolak = new HashMap<>();

    public static void main(String[] args) {
        // Erabiltzaileak eta pasahitzak gehitu
        erabiltzaileak.put("admin", "admin123");
        erabiltzaileak.put("erabiltzailea", "pass123");

        // Rolak esleitu
        rolak.put("admin", "ADMIN");
        rolak.put("erabiltzailea", "ERABILTZAILEA");

        // Sarbide kontrola probatu
        sarbideaKonprobatu("admin", "admin123", "ADMIN");
        sarbideaKonprobatu("erabiltzailea", "pass123", "ERABILTZAILEA");
        sarbideaKonprobatu("admin", "oker", "ADMIN");
        sarbideaKonprobatu("erabiltzailea", "pass123", "ADMIN");
    }

    private static void sarbideaKonprobatu(String erabiltzailea, String pasahitza, String beharrezkoRola) {
        // Sarrera balidazioa
        if (erabiltzailea == null || pasahitza == null) {
            System.out.println("Errorea: Erabiltzailea eta pasahitza beharrezkoak dira");
            return;
        }

        // Erabiltzailea existitzen den konprobatu
        if (!erabiltzaileak.containsKey(erabiltzailea)) {
            System.out.println("Errorea: Erabiltzailea ez da existitzen");
            return;
        }

        // Pasahitza konprobatu
        if (!erabiltzaileak.get(erabiltzailea).equals(pasahitza)) {
            System.out.println("Errorea: Pasahitza okerra");
            return;
        }

        // Rola konprobatu
        String erabiltzaileRola = rolak.get(erabiltzailea);
        if (!erabiltzaileRola.equals(beharrezkoRola)) {
            System.out.println("Errorea: Ez duzu beharrezko baimenik");
            return;
        }

        System.out.println("Ongi etorri " + erabiltzailea + "! Zure rola: " + erabiltzaileRola);
    }
} 