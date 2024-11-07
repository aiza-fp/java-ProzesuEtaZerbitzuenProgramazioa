package sarekoKomunikazioakProgramatzea_3.bezeroZerbitzariHariekin;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
class BezeroKudeatzailea implements Runnable {
    private final Socket socket;

    public BezeroKudeatzailea(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            System.out.println("Bezeroa konektatu da: " + socket.getInetAddress().getHostAddress());

            // Sortu sarrera eta irteera Stream-ak
            BufferedReader sarrera = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            PrintWriter irteera = new PrintWriter(socket.getOutputStream(), true);

            // Irakurri bezeroaren mezua
            String mezua = sarrera.readLine();
            System.out.println("Bezerotik jasotako mezua: " + mezua);

            // Bidali erantzuna bueltan bezeroari
            irteera.println("Zerbitzariak mezua jaso du: " + mezua);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}