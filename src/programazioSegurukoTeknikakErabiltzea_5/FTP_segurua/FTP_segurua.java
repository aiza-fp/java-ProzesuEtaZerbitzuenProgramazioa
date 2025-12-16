/*
 * Exekutatzerakoan, argumentu hauek gehitu behar dira:
 * "--add-opens=java.base/sun.security.ssl=ALL-UNNAMED --add-opens=java.base/sun.security.util=ALL-UNNAMED"
*/

package programazioSegurukoTeknikakErabiltzea_5.FTP_segurua;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.security.cert.X509Certificate;

/**
 * Liburutegia:
 * 
 * https://dlcdn.apache.org//commons/net/binaries/commons-net-3.11.1-bin.zip
 * 
 * Barruan dagoen commons-net-3.11.1.jar gehitu proiektura
 * 
 * FTP zerbitzari bezala Filezilla Server instalatu daiteke, portua 14148
 * 
 * Programa honek explicit FTP over TLS (FTPS) erabiltzen du konexio segurua egiteko
 * Java-ren SSL socket-ak erabiliz, TLS saioaren berrerabiltzea onartuz
 */
public class FTP_segurua {
    public static void main(String[] args) {
        // Java 8u161 edo goragokoetan, extended master secret desgaitu
        // Honek TLS saioaren berrerabiltzearekin bateragarritasun arazoak konpontzen ditu
        System.setProperty("jdk.tls.useExtendedMasterSecret", "false");
        // Java 17-an beharrezkoa: session ticket extension desgaitu
        // Honek FileZilla Server-ekin bateragarritasuna bermatzen du
        System.setProperty("jdk.tls.client.enableSessionTicketExtension", "false");
        
        // TLS 1.2 behartu - beste TLS bertsioak desgaitu
        System.setProperty("jdk.tls.client.protocols", "TLSv1.2");
        //System.setProperty("https.protocols", "TLSv1.2");
        //System.setProperty("jdk.tls.server.protocols", "TLSv1.2");
        
        String server = "localhost";
        int port = 21;
        String user = "gonbidatua";
        String pass = "pasahitza";

        // SimpleFTPSClient erabili (Java-ren SSL socket-ak erabiliz)
        SimpleFTPSClient ftpClient = new SimpleFTPSClient();
        
        try {
            // SSL kontextua konfiguratu - TLS 1.2 bakarrik erabili
            // Adibide honetan, ziurtagiri guztiak onartzen dira (garapenerako bakarrik)
            // Produkzioan, KeyStore egokia erabili behar da
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() { return null; }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) { }
                    public void checkServerTrusted(X509Certificate[] certs, String authType) { }
                }
            };
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            
            // SSL kontextuaren session cache konfiguratu (TLS saioaren berrerabiltzea baimenduz)
            // Garrantzitsua: Honek kontrol eta datu konexioek TLS saioa partekatzea ahalbidetzen du
            javax.net.ssl.SSLSessionContext clientContext = sslContext.getClientSessionContext();
            if (clientContext != null) {
                clientContext.setSessionCacheSize(0); // 0 = mugagabea
                clientContext.setSessionTimeout(86400); // 24 ordu
                System.out.println("SSL session cache konfiguratuta - tamaina: " + clientContext.getSessionCacheSize());
            }
            
            // Lehenetsitako SSL kontextua ezarri
            // Honek Java-ren SSL implementazioak session cache-a erabiltzea bermatzen du
            javax.net.ssl.SSLContext.setDefault(sslContext);
            System.out.println("Lehenetsitako SSL kontextua ezarrita");
            
            // Zerbitzarira konektatu (explicit TLS)
            ftpClient.connect(server, port, sslContext, trustAllCerts[0]);
            
            // Login egitu
            if (!ftpClient.login(user, pass)) {
                System.out.println("Login-ak huts egin du");
                return;
            }
            
            // PBSZ komandoa exekutatu (Protection Buffer Size - 0 balioa erabiltzen da)
            ftpClient.execPBSZ(0);
            
            // Datu konexioak babestu (PROT P = Protected)
            ftpClient.execPROT("P");

            // Fitxategi bat deskargatzea
            String remoteFile = "/karpeta/fitxategia.txt";
            File downloadFile = new File("fitxategia_lokalean.txt");
            try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(downloadFile))) {
                if (ftpClient.retrieveFile(remoteFile, outputStream)) {
                    System.out.println("Fitxategia deskargatu da.");
                } else {
                    System.out.println("Fitxategia deskargatzean errorea.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

