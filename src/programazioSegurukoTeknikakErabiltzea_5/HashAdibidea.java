package programazioSegurukoTeknikakErabiltzea_5;

import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

/**
 * Hash funtzio kriptografikoen adibidea Hash funtzioak mezu bat hartu eta
 * luzera finkoko irteera bat sortzen du.
 * Ezinezkoa da hash-etik jatorrizko mezua berreskuratzea
 */
public class HashAdibidea {
	public static void main(String[] args) {
		try {
			// Probatu nahi ditugun mezuak
			String[] mezuak = { 
					"Kaixo mundua", 
					"Kaixo mundua!", // Karaktere bat aldatuta, aldaketa txiki batek hash guztiz ezberdina sortzen du
					"Mezu luzeago bat hash funtzioaren luzera aldatzen ez dela ikusteko" };

			// Hash algoritmo ezberdinak probatu
			String[] algoritmoak = { "MD5", "SHA-256", "SHA-512" };

			for (String mezua : mezuak) {
				System.out.println("\n=== Mezua: " + mezua + " ===");

				for (String algoritmoa : algoritmoak) {
					// MessageDigest objektua sortu algoritmo bakoitzarentzat
					MessageDigest digest = MessageDigest.getInstance(algoritmoa);

					// Mezua hash-era bihurtu
					byte[] hashBytes = digest.digest(mezua.getBytes(StandardCharsets.UTF_8));

					// Hash-a hexadezimalera bihurtu
					StringBuilder hashHex = new StringBuilder();
					for (byte b : hashBytes) {
						hashHex.append(String.format("%02x", b));
							//%02x formatuaren esanahia:
							//%x: zenbakia hexadezimal formatuan adierazten du (0-9 eta a-f letrak erabiliz)
							//0: hutsuneak zeroekin beteko dira
							//2: gutxienez 2 karaktere erabili behar dira (behar izanez gero, zeroak gehituko dira ezkerrean)
					}

					// Emaitzak erakutsi
					System.out.println(algoritmoa + " hash-a: " + hashHex.toString());
					System.out.println(algoritmoa + " hash luzeera: " + (hashBytes.length * 8) + " bit");
				}
			}

		} catch (Exception e) {
			System.out.println("Errorea: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
