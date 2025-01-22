package programazioSegurukoTeknikakErabiltzea_5;

import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import java.io.*;
import java.net.SocketException;

/**
 * SSL Socket seguruen adibidea - Zerbitzaria
 */
public class SocketSeguroaZerbitzariaEten {
	public static void main(String[] args) {
		// SSL propietateak ezarri
		System.setProperty("javax.net.ssl.keyStore", "server.keystore");
		System.setProperty("javax.net.ssl.keyStorePassword", "pasahitzaServerKeyStore");
		// Garrantzitsua: Produkzio inguruneetan, pasahitza ez litzateke kodean zuzenean
		// idatzi behar, baizik eta konfigurazio fitxategi edo ingurune aldagai batetik hartu

		try {
			SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			SSLServerSocket serverSocket = (SSLServerSocket) factory.createServerSocket(9999);

			System.out.println("Zerbitzaria martxan dago...");

			boolean[] running = { true };
			System.out.println("Sakatu 'q' eta Intro irteteko");

			// Hari bat sortu q tekla sakatu dela detektatuko duena
			// Zerbitzariaren entzute prozesua geratuko du.
			Thread keyListener = new Thread(() -> {
				try (BufferedReader keyReader = new BufferedReader(new InputStreamReader(System.in))) {
					while (running[0]) {
						if (keyReader.readLine().equalsIgnoreCase("q")) {
							running[0] = false;
							serverSocket.close();
						}
					}
				} catch (IOException e) {
					// It's normal to get an exception when closing
					if (!serverSocket.isClosed()) {
						e.printStackTrace();
					}
				}
			});
			keyListener.start();

			try {
				while (running[0]) {
					try {
						SSLSocket clientSocket = (SSLSocket) serverSocket.accept();
						BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
						PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

						String mezua = in.readLine();
						System.out.println("Jasotako mezua: " + mezua);
						out.println("Mezua jasota");

						clientSocket.close();
					} catch (SocketException e) {
						// This exception is expected when socket is closed
						if (running[0]) {
							e.printStackTrace();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Zerbitzaria bukatuta");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}