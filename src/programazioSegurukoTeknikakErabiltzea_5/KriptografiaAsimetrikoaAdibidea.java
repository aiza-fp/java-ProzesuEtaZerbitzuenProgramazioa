package programazioSegurukoTeknikakErabiltzea_5;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;
import java.util.Base64;

/**
 * Gako publiko eta pribatuen erabilera kriptografian
 */
public class KriptografiaAsimetrikoaAdibidea {
    public static void main(String[] args) {
        try {
            // RSA gako parea sortu
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048); // 245 byte-eko muga mezuan
            KeyPair pair = keyGen.generateKeyPair();
            
            PublicKey publikoa = pair.getPublic();
            PrivateKey pribatua = pair.getPrivate();

            // Mezu laburragoa erabili RSA-rekin bateragarria izateko
            String mezuOriginala = "Mezu sekretua - RSA probatzen";
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publikoa);
            byte[] zifratutakoDatuak = cipher.doFinal(mezuOriginala.getBytes());
            String zifratutakaMezua = Base64.getEncoder().encodeToString(zifratutakoDatuak);
            
            System.out.println("Mezu Originala: " + mezuOriginala);
            System.out.println("Zifratutako Mezua: " + zifratutakaMezua);
            System.out.println("Zifratutako Mezuaren tamaina: " + zifratutakaMezua.length());

            // Mezua deszifratu gako pribatuarekin
            cipher.init(Cipher.DECRYPT_MODE, pribatua);
            byte[] deszifratutakoDatuak = cipher.doFinal(Base64.getDecoder().decode(zifratutakaMezua));
            String deszifratutakoMezua = new String(deszifratutakoDatuak);
            
            System.out.println("Deszifratutako Mezua: " + deszifratutakoMezua);

        } catch (Exception e) {
            System.out.println("Errorea: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 