package programazioSegurukoTeknikakErabiltzea_5;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Kriptografia simetrikoaren adibidea AES algoritmoa erabiliz Kriptografia
 * simetrikoan gako BERDINA erabiltzen da zifratzeko eta deszifratzeko
 */
public class KriptografiaSimetrikoaAdibidea {
	public static void main(String[] args) {
		try {
			// 1. AES gako simetrikoa sortu (256 bit)
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(256); // Gako tamaina: 256 bit
			SecretKey gakoSimetrikoa = keyGen.generateKey();

			// 2. Zifratu nahi dugun mezua
			String mezuOriginala = "Hau da mezu sekretu luze bat, kriptografia simetrikoarekin zifratu eta deszifratzeko!!!";

			// 3. Zifratzeko objektua sortu
			Cipher cipher = Cipher.getInstance("AES");

			// 4. Mezua zifratu
			cipher.init(Cipher.ENCRYPT_MODE, gakoSimetrikoa);
			byte[] zifratutakoDatuak = cipher.doFinal(mezuOriginala.getBytes());
			String zifratutakoMezua = Base64.getEncoder().encodeToString(zifratutakoDatuak);

			// 5. Mezua deszifratu
			cipher.init(Cipher.DECRYPT_MODE, gakoSimetrikoa);
			byte[] deszifratutakoDatuak = cipher.doFinal(Base64.getDecoder().decode(zifratutakoMezua));
			String deszifratutakoMezua = new String(deszifratutakoDatuak);

			// 6. Emaitzak erakutsi
			System.out.println("=== KRIPTOGRAFIA SIMETRIKOA (AES) ===");
			System.out.println("Gako Simetrikoa: " + Base64.getEncoder().encodeToString(gakoSimetrikoa.getEncoded()));
			System.out.println("\nMezu Originala: " + mezuOriginala);
			System.out.println("Zifratutako Mezua: " + zifratutakoMezua);
			System.out.println("Deszifratutako Mezua: " + deszifratutakoMezua);

			// 7. Egiaztatu mezua ondo deszifratu den
			System.out.println("\nMezua ondo deszifratu da? " + mezuOriginala.equals(deszifratutakoMezua));

		} catch (Exception e) {
			System.out.println("Errorea: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Gakoa String bihurtu
	 * 
	 * @param gakoa
	 * @return
	 */
	public static String gakoaStringBihurtu(SecretKey gakoa) {
		byte[] gakoBytes = gakoa.getEncoded();
		return Base64.getEncoder().encodeToString(gakoBytes);
	}

	/**
	 * String-etik SecretKey sortu
	 * 
	 * @param gakoaString
	 * @return
	 */
	public static SecretKey stringetikGakoaSortu(String gakoaString) {
		byte[] decodedKey = Base64.getDecoder().decode(gakoaString);
		return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
	}
}
