package programazioSegurukoTeknikakErabiltzea_5;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.*;

/**
 * SSL Socket seguruen adibidea - Bezeroa
 */
public class SocketSeguroaBezeroa {
    public static void main(String[] args) {
        // SSL propietateak ezarri
        System.setProperty("javax.net.ssl.trustStore", "client.truststore");
        System.setProperty("javax.net.ssl.trustStorePassword", "pasahitzaClientTrustStore");
        // Garrantzitsua: Produkzio inguruneetan, pasahitza ez litzateke kodean zuzenean idatzi behar,
        //baizik eta konfigurazio fitxategi edo ingurune aldagai batetik hartu
        
        try {
            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket socket = (SSLSocket) factory.createSocket("localhost", 9999);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println("Kaixo, mezu segurua!");
            String erantzuna = in.readLine();
            System.out.println("Zerbitzariaren erantzuna: " + erantzuna);

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 