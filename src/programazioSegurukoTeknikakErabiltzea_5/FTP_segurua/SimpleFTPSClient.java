package programazioSegurukoTeknikakErabiltzea_5.FTP_segurua;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * FTPS bezero sinplea Java-ren SSL socket-ak erabiliz
 * Honek TLS saioaren berrerabiltzea onartzen du datu konexioetarako
 */
public class SimpleFTPSClient {
    
    private SSLSocket controlSocket = null;
    private SSLSession controlSession = null;
    private SSLContext sslContext = null;
    private BufferedReader controlReader = null;
    private PrintWriter controlWriter = null;
    private boolean connected = false;
    
    /**
     * Zerbitzarira konektatzen da explicit TLS erabiliz
     * Garrantzitsua: Explicit TLS-n, hasieran TCP konexio arrunta erabiltzen da,
     * eta AUTH TLS komandoaren ondoren bihurtzen da TLS konexio
     */
    public void connect(String host, int port, SSLContext sslContext, TrustManager trustManager) throws IOException {
        this.sslContext = sslContext;
        
        // Lehenik, TCP konexioa arrunta sortu (ez SSL)
        Socket plainSocket = new Socket(host, port);
        
        try {
            // I/O stream-ak sortu (TCP arruntarekin)
            // Oharra: Ez dugu BufferedReader/PrintWriter erabili behar hemen,
            // socket-a SSL bihurtuko dugu laster eta stream-ak automatikoki itxiko dira
            // Linter warning-a false positive da: createSocket(..., true) jabetza hartzen du
            @SuppressWarnings("resource")
            InputStream plainInput = plainSocket.getInputStream();
            @SuppressWarnings("resource")
            OutputStream plainOutput = plainSocket.getOutputStream();
            
            // Zerbitzariaren erantzuna irakurri (FTP greeting - lerro anitzeko izan daiteke)
            // FTP erantzunak "220-" formatuan etortzen badira, lerro gehiago daude
            // Azken lerroa "220 " formatuan etortzen da (espazioa, ez gidoia)
            String response;
            do {
                StringBuilder line = new StringBuilder();
                int c;
                while ((c = plainInput.read()) != -1) {
                    if (c == '\n') {
                        break;
                    }
                    if (c != '\r') {
                        line.append((char) c);
                    }
                }
                response = line.toString();
                System.out.println("Zerbitzariaren erantzuna: " + response);
                
                // "220 " (espazioa) formatua azken lerroa da
                // "220-" (gidoia) formatua erdiko lerroak dira
            } while (response.startsWith("220-"));
            
            // AUTH TLS komandoa bidali (explicit TLS aktibatzeko)
            String authCommand = "AUTH TLS\r\n";
            plainOutput.write(authCommand.getBytes());
            plainOutput.flush();
            
            // AUTH TLS erantzuna irakurri
            StringBuilder authResponse = new StringBuilder();
            int c;
            while ((c = plainInput.read()) != -1) {
                if (c == '\n') {
                    break;
                }
                if (c != '\r') {
                    authResponse.append((char) c);
                }
            }
            response = authResponse.toString();
            System.out.println("AUTH TLS: " + response);
            
            if (!response.startsWith("234")) {
                throw new IOException("AUTH TLS huts egin du: " + response);
            }
            
            // ORAIN SSL socket bihurtu (AUTH TLS komandoaren ondoren)
            // Oharra: createSocket-ek plainSocket-aren jabetza hartzen du (autoClose=true)
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            this.controlSocket = (SSLSocket) sslSocketFactory.createSocket(plainSocket, host, port, true);
            plainSocket = null; // Jabetza SSL socket-ari transferitu da
        } catch (Exception e) {
            // Errore bat gertatzen bada, TCP socket-a itxi
            if (plainSocket != null) {
                plainSocket.close();
            }
            throw e;
        }
        
        // SSL bezero modua aktibatu
        controlSocket.setUseClientMode(true);
        
        // TLS protokoloak konfiguratu - TLS 1.2 bakarrik erabili
        controlSocket.setEnabledProtocols(new String[]{"TLSv1.2"});
        
        // SSL handshake hasi
        controlSocket.startHandshake();
        
        // Kontrol konexioaren TLS saioa gorde
        controlSession = controlSocket.getSession();
        
        // I/O stream-ak berriro sortu (SSL socket-arekin)
        controlReader = new BufferedReader(new InputStreamReader(controlSocket.getInputStream()));
        controlWriter = new PrintWriter(controlSocket.getOutputStream(), true);
        
        connected = true;
        System.out.println("TLS konexioa ezarrita. Session ID: " + 
            java.util.Arrays.toString(controlSession.getId()));
    }
    
    /**
     * Login egiten du
     */
    public boolean login(String username, String password) throws IOException {
        sendCommand("USER " + username);
        String response = readResponse();
        
        if (response.startsWith("331")) {
            sendCommand("PASS " + password);
            response = readResponse();
            if (response.startsWith("230")) {
                System.out.println("Login arrakastatsua");
                return true;
            }
        }
        System.out.println("Login huts egin du: " + response);
        return false;
    }
    
    /**
     * PBSZ komandoa bidaltzen du
     */
    public void execPBSZ(long size) throws IOException {
        sendCommand("PBSZ " + size);
        String response = readResponse();
        System.out.println("PBSZ: " + response);
    }
    
    /**
     * PROT komandoa bidaltzen du
     */
    public void execPROT(String level) throws IOException {
        sendCommand("PROT " + level);
        String response = readResponse();
        System.out.println("PROT: " + response);
    }
    
    /**
     * Pasibo modua aktibatzen du eta datu konexioaren informazioa itzultzen du
     */
    public DataConnectionInfo enterPassiveMode() throws IOException {
        sendCommand("PASV");
        String response = readResponse();
        System.out.println("PASV: " + response);
        
        if (!response.startsWith("227")) {
            throw new IOException("PASV huts egin du: " + response);
        }
        
        // PASV erantzuna parseatu: 227 Entering Passive Mode (h1,h2,h3,h4,p1,p2)
        int start = response.indexOf('(');
        int end = response.indexOf(')');
        if (start == -1 || end == -1) {
            throw new IOException("PASV erantzuna ezin da parseatu: " + response);
        }
        
        String data = response.substring(start + 1, end);
        StringTokenizer st = new StringTokenizer(data, ",");
        int h1 = Integer.parseInt(st.nextToken().trim());
        int h2 = Integer.parseInt(st.nextToken().trim());
        int h3 = Integer.parseInt(st.nextToken().trim());
        int h4 = Integer.parseInt(st.nextToken().trim());
        int p1 = Integer.parseInt(st.nextToken().trim());
        int p2 = Integer.parseInt(st.nextToken().trim());
        
        String host = h1 + "." + h2 + "." + h3 + "." + h4;
        int port = p1 * 256 + p2;
        
        return new DataConnectionInfo(host, port);
    }
    
    /**
     * Fitxategi bat deskargatzen du
     */
    public boolean retrieveFile(String remoteFile, OutputStream outputStream) throws IOException {
        // Pasibo modua aktibatu
        DataConnectionInfo dataInfo = enterPassiveMode();
        
        // Datu konexioa sortu (kontrol konexioaren TLS saioa berrerabiltzeko)
        // Garrantzitsua: Datu konexioa OSATU behar da RETR bidali AURREAN
        // FTP protokoloan, datu konexioa TCP mailan konektatu behar da lehenik,
        // gero SSL handshake egin, eta ONDOREN bidali RETR komandoa
        SSLSocket dataSocket = createDataConnection(dataInfo.host, dataInfo.port);
        
        // Datu konexioa osatuta dagoela egiaztatu (TCP konektatuta eta SSL handshake bukatuta)
        if (!dataSocket.isConnected()) {
            dataSocket.close();
            throw new IOException("Datu konexioa TCP mailan ezin da konektatu");
        }
        
        // SSL handshake bukatuta dagoela egiaztatu
        SSLSession dataSession = dataSocket.getSession();
        if (dataSession == null || !dataSession.isValid()) {
            dataSocket.close();
            throw new IOException("Datu konexioaren SSL handshake huts egin du");
        }
        
        // Garrantzitsua: Datu konexioa OSATU behar da RETR bidali AURREAN
        // FileZilla Server-ek datu konexioa erregistratu behar du lehenik
        // Momentu bat itxaron datu konexioa guztiz prest egon dadin
        try {
            Thread.sleep(200); // 200ms itxaron datu konexioa erregistratu dadin
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // RETR komandoa bidali (datu konexioa prest dagoenean)
        sendCommand("RETR " + remoteFile);
        String response = readResponse();
        
        if (!response.startsWith("150") && !response.startsWith("125")) {
            dataSocket.close();
            System.out.println("RETR huts egin du: " + response);
            System.out.println("Oharra: Hau gertatzen bada, datu konexioa ez da onartu (session resumption huts egin duelako?)");
            return false;
        }
        
        // Datuak irakurri eta idatzi
        try (InputStream dataInput = dataSocket.getInputStream();
             BufferedInputStream bufferedInput = new BufferedInputStream(dataInput);
             BufferedOutputStream bufferedOutput = new BufferedOutputStream(outputStream)) {
            
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = bufferedInput.read(buffer)) != -1) {
                bufferedOutput.write(buffer, 0, bytesRead);
            }
            bufferedOutput.flush();
        }
        
        dataSocket.close();
        
        // Zerbitzariaren erantzuna irakurri
        response = readResponse();
        System.out.println("Transferentzia: " + response);
        
        return response.startsWith("226");
    }
    
    /**
     * Datu konexioa sortzen du, kontrol konexioaren TLS saioa berrerabiltzeko konfiguratuta
     */
    private SSLSocket createDataConnection(String host, int port) throws IOException {
        // TCP konexioa sortu
        Socket plainSocket = null;
        try {
            plainSocket = new Socket(host, port);
            
            // SSL socket sortu, kontrol konexioaren SSL kontextua erabiliz
            // Garrantzitsua: SSL kontextu BERA erabili behar da session resumption aktibatzeko
            // Oharra: createSocket-ek plainSocket-aren jabetza hartzen du (autoClose=true)
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            SSLSocket dataSocket = (SSLSocket) sslSocketFactory.createSocket(plainSocket, host, port, true);
            plainSocket = null; // Jabetza SSL socket-ari transferitu da
        
            // SSL bezero modua aktibatu
            dataSocket.setUseClientMode(true);
            
            // TLS protokoloak konfiguratu (kontrol konexioaren berdinak)
            // Garrantzitsua: TLSv1.2 bakarrik erabili (TLSv1.3-ekin bateragarritasun arazoak daude)
            dataSocket.setEnabledProtocols(new String[]{"TLSv1.2"});
            
            // Session resumption aktibatzeko - Stack Overflow soluzioa aplikatzen
            // Garrantzitsua: SSL socket sortu ONDOREN baina handshake hasi AURREAN egin behar da
            // FileZilla Server-ek datu konexioak kontrol konexioaren TLS saioa berrerabiltzea eskatzen du
            // Java-ren SSL implementazioak ez du automatikoki egiten, beraz erreflexio bidez
            // session-a cache-an sartu behar da datu konexioaren host:port konbinazioarekin
            // 
            // Garrantzitsua: SSLContext-aren client session context erabili behar da,
            // ez bakarrik kontrol saioaren context-a
            if (controlSession != null && controlSession.isValid() && sslContext != null) {
                try {
                    // SSLContext-aren client session context erabili
                    final SSLSessionContext context = sslContext.getClientSessionContext();
                    if (context != null) {
                        // sessionHostPortCache eremua eskuratu erreflexio bidez
                    final java.lang.reflect.Field sessionHostPortCache = context.getClass()
                        .getDeclaredField("sessionHostPortCache");
                    sessionHostPortCache.setAccessible(true);
                    final Object cache = sessionHostPortCache.get(context);
                        
                    // put metodoa eskuratu
                    final java.lang.reflect.Method putMethod = cache.getClass()
                        .getDeclaredMethod("put", Object.class, Object.class);
                    putMethod.setAccessible(true);
                    
                    // Host informazioa eskuratu SSL socket-etik
                    String peerHost = null;
                    try {
                        java.lang.reflect.Method getPeerHostMethod = dataSocket.getClass().getMethod("getPeerHost");
                        getPeerHostMethod.setAccessible(true);
                        peerHost = (String) getPeerHostMethod.invoke(dataSocket);
                    } catch (Exception e) {
                        peerHost = host;
                    }
                    
                    final InetAddress iAddr = dataSocket.getInetAddress();
                    final int socketPort = dataSocket.getPort();
                    
                    // Session-a cache-an sartu host:port konbinazio desberdinekin
                    String[] keysToTry = new String[4];
                    int keyIndex = 0;
                    
                    if (peerHost != null) {
                        keysToTry[keyIndex++] = String.format("%s:%s", peerHost, socketPort).toLowerCase(Locale.ROOT);
                    }
                    if (iAddr.getHostName() != null && !iAddr.getHostName().equals(peerHost)) {
                        keysToTry[keyIndex++] = String.format("%s:%s", iAddr.getHostName(), socketPort).toLowerCase(Locale.ROOT);
                    }
                    keysToTry[keyIndex++] = String.format("%s:%s", iAddr.getHostAddress(), socketPort).toLowerCase(Locale.ROOT);
                    if (host.equals("localhost") || host.equals("127.0.0.1")) {
                        keysToTry[keyIndex++] = String.format("localhost:%s", socketPort).toLowerCase(Locale.ROOT);
                    }
                    
                        for (int i = 0; i < keyIndex; i++) {
                            putMethod.invoke(cache, keysToTry[i], controlSession);
                        }
                    }
                } catch (final Exception e) {
                    // Erreflexioak huts egin badu
                    System.out.println("Oharra: Session-a ezin da cache-an sartu: " + e.getMessage());
                    e.printStackTrace();
                }
            }
            
            // SSL handshake hasi
            dataSocket.startHandshake();
            
            // Datu konexioaren saioa lortu
            SSLSession dataSession = dataSocket.getSession();
            
            // Session resumption egiaztatu
            if (controlSession != null && dataSession != null) {
                byte[] controlId = controlSession.getId();
                byte[] dataId = dataSession.getId();
                
                if (controlId != null && dataId != null && 
                    java.util.Arrays.equals(controlId, dataId)) {
                    System.out.println("TLS saioaren berrerabiltzea arrakastatsua!");
                } else {
                    System.out.println("ERROREA: TLS saioaren ID-ak ez dira berdinak - session resumption huts egin du");
                    System.out.println("  Kontrol: " + java.util.Arrays.toString(controlId));
                    System.out.println("  Datu: " + java.util.Arrays.toString(dataId));
                    System.out.println("  FileZilla Server-ek session resumption eskatzen du, beraz datu konexioa ezeztatuko da");
                    // Hala ere, saiatu konexioa erabiltzen
                }
            }
            
            return dataSocket;
        } finally {
            // Errore bat gertatzen bada, TCP socket-a itxi
            if (plainSocket != null) {
                plainSocket.close();
            }
        }
    }
    
    /**
     * Komando bat bidaltzen du
     */
    private void sendCommand(String command) throws IOException {
        if (controlWriter == null) {
            throw new IOException("Ez dago konektatuta");
        }
        controlWriter.println(command);
    }
    
    /**
     * Zerbitzariaren erantzuna irakurtzen du
     */
    private String readResponse() throws IOException {
        if (controlReader == null) {
            throw new IOException("Ez dago konektatuta");
        }
        return controlReader.readLine();
    }
    
    /**
     * Konexioa itxi
     */
    public void disconnect() throws IOException {
        if (connected) {
            try {
                sendCommand("QUIT");
                readResponse();
            } catch (Exception e) {
                // Ignore
            }
        }
        
        // Stream-ak itxi
        if (controlReader != null) {
            try {
                controlReader.close();
            } catch (Exception e) {
                // Ignore
            }
            controlReader = null;
        }
        
        if (controlWriter != null) {
            controlWriter.close();
            controlWriter = null;
        }
        
        // Socket-a itxi
        if (controlSocket != null && !controlSocket.isClosed()) {
            controlSocket.close();
            controlSocket = null;
        }
        
        connected = false;
    }
    
    /**
     * Datu konexioaren informazioa
     */
    public static class DataConnectionInfo {
        public final String host;
        public final int port;
        
        public DataConnectionInfo(String host, int port) {
            this.host = host;
            this.port = port;
        }
    }
}

