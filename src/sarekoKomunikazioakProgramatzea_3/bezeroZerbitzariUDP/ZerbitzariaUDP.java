package sarekoKomunikazioakProgramatzea_3.bezeroZerbitzariUDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ZerbitzariaUDP {
    public static void main(String[] args) throws InterruptedException {
        try (DatagramSocket socket = new DatagramSocket(9000)) {
            System.out.println("UDP zerbitzaria martxan 9000 portuan...");

            byte[] jasotzekoDatuak = new byte[1024];
            DatagramPacket jasotzekoPacket = new DatagramPacket(jasotzekoDatuak, jasotzekoDatuak.length);

            // Paketea jaso
            socket.receive(jasotzekoPacket);
            String mezua = new String(jasotzekoPacket.getData(), 0, jasotzekoPacket.getLength());
            System.out.println("Jasotako mezua: " + mezua);

            System.out.println("10 segundu itxaroten");
            Thread.sleep(10000);
            
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
            
            System.out.println("Erantzuna bidalita");
            System.out.println("Bukatuta.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}