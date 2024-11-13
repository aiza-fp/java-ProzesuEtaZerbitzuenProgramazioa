package sarekoKomunikazioakProgramatzea_3.bezeroZerbitzariUDP;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class BezeroaUDP {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName("localhost");
            
            System.out.println("Bezeroa abiarazita!");
            
            // Mezua bidali
            String mezua = "Kaixo UDP zerbitzaria!";
            byte[] bidaltzekoDatuak = mezua.getBytes();
            DatagramPacket bidaltzekoPaketea = new DatagramPacket(
                bidaltzekoDatuak,
                bidaltzekoDatuak.length,
                address,
                9000
            );
            socket.send(bidaltzekoPaketea);
            
            System.out.println("Datagrama bidalita!");

            // Erantzuna jaso
            byte[] jasotzekoDatuak = new byte[1024];
            DatagramPacket jasotzekoPacket = new DatagramPacket(jasotzekoDatuak, jasotzekoDatuak.length);
            socket.receive(jasotzekoPacket);
            
            String erantzuna = new String(jasotzekoPacket.getData(), 0, jasotzekoPacket.getLength());
            System.out.println("Zerbitzariaren erantzuna: " + erantzuna);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}