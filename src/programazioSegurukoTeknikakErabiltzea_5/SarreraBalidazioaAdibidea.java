package programazioSegurukoTeknikakErabiltzea_5;

import java.util.regex.Pattern;

/**
 * Sarrera datuen balidazio adibidea
 */
public class SarreraBalidazioaAdibidea {
    public static void main(String[] args) {
        // Sarrera datuak
        String email = "erabiltzailea@adibidea.com";
        String telefonoa = "666555444";
        String testua = "<script>alert('XSS erasoa')</script>";

        // Balidazioak egin
        System.out.println("Email baliozkoa: " + emailaBalidatu(email));
        System.out.println("Telefono baliozkoa: " + telefonoaBalidatu(telefonoa));
        System.out.println("Testu segurua: " + testuaGarbitu(testua));
    }

    private static boolean emailaBalidatu(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.compile(regex).matcher(email).matches();
    }

    private static boolean telefonoaBalidatu(String telefonoa) {
        return telefonoa.matches("\\d{9}");
    }

    private static String testuaGarbitu(String testua) {
        // XSS erasoak ekiditeko karaktere bereziak ordezkatu
        return testua.replaceAll("&", "&amp;")
        			.replaceAll("<", "&lt;")
                    .replaceAll(">", "&gt;")
                    .replaceAll("\"", "&quot;")
                    .replaceAll("'", "&#x27;");            
    }
} 