package sarekoKomunikazioakProgramatzea_3.bezeroZerbitzariUDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ZerbitzariaUDP {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(9000)) {
            System.out.println("UDP zerbitzaria martxan 9000 portuan...");

            byte[] jasotzekoDatuak = new byte[1024];
            DatagramPacket jasotzekoPacket = new DatagramPacket(jasotzekoDatuak, jasotzekoDatuak.length);

            // Paketea jaso
            socket.receive(jasotzekoPacket);
            String mezua = new String(jasotzekoPacket.getData(), 0, jasotzekoPacket.getLength());
            System.out.println("Jasotako mezua: " + mezua);

            // Erantzuna bidali
            String erantzuna = "Mezua jasota!";
            byte[] bidaltzekoDatuak = erantzuna.getBytes();
            DatagramPacket bidaltzekoPaketea = new DatagramPacket(
                bidaltzekoDatuak, 
                bidaltzekoDatuak.length,
                jasotzekoPacket.getAddress(),
                jasotzekoPacket.getPort()
            );
            socket.send(bidaltzekoPaketea);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}