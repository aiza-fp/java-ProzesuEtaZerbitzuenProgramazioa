package hariAnitzekoProgramazioa_2.pipeline;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipelineAdibidea {
    public static void main(String[] args) {
        try {
            // PipedOutputStream eta PipedInputStream instantziak sortu
            PipedOutputStream pipedOutputStream = new PipedOutputStream();
            PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);
            
            // Hari idazlea (produktorea)
            Thread writerThread = new Thread(() -> {
                try {
                    String mezua = "Hari idazlea naiz!";
                    pipedOutputStream.write(mezua.getBytes());
                    pipedOutputStream.close(); // Stream-a itxi idaztea bukatzean
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            // Hari irakurlea (konsumitzailea)
            Thread readerThread = new Thread(() -> {
                try {
                    int data;
                    StringBuilder mezua = new StringBuilder();
                    while ((data = pipedInputStream.read()) != -1) {
                    	mezua.append((char) data);
                    }
                    System.out.println("Hari irakurlea, jasotako mezua: " + mezua);
                    pipedInputStream.close(); // Stream-a itxi irakurtzea bukatzean
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            // Hariak abiarari
            writerThread.start();
            readerThread.start();
            
            // Hariak bukatu arte itxaron
            writerThread.join();
            readerThread.join();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
