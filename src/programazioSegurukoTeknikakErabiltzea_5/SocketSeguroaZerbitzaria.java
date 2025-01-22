package programazioSegurukoTeknikakErabiltzea_5;

import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import java.io.*;

/**
 * SSL Socket seguruen adibidea - Zerbitzaria
 */
public class SocketSeguroaZerbitzaria {
    public static void main(String[] args) {
        // SSL propietateak ezarri
        System.setProperty("javax.net.ssl.keyStore", "server.keystore");
        System.setProperty("javax.net.ssl.keyStorePassword", "pasahitzaServerKeyStore");
        // Garrantzitsua: Produkzio inguruneetan, pasahitza ez litzateke kodean zuzenean idatzi behar,
        //baizik eta konfigurazio fitxategi edo ingurune aldagai batetik hartu

        try {
            SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            SSLServerSocket serverSocket = (SSLServerSocket) factory.createServerSocket(9999);
            
            System.out.println("Zerbitzaria martxan dago...");
            
            while (true) {
                SSLSocket clientSocket = (SSLSocket) serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                String mezua = in.readLine();
                System.out.println("Jasotako mezua: " + mezua);
                out.println("Mezua jasota");

                clientSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 