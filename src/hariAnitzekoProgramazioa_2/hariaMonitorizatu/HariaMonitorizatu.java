package hariAnitzekoProgramazioa_2.hariaMonitorizatu;

import java.util.List;

public class HariaMonitorizatu {

    static List<String> mezuak = List.of("mezu1", "mezu2", "mezu3", "mezu4");
    static long pazientzia = 8000;

    public static void main(String[] args) throws InterruptedException {

    	//izen bat eman hariari
        Thread.currentThread().setName("Hari Nagusia");

        long hasiera = System.currentTimeMillis();

        Thread bigarren_haria = new Thread(() -> {
            Thread.currentThread().setName("Bigarren Haria");
            print("Lana hasten");
            try {
                for(String mezua: mezuak){
                    Thread.sleep(4000);
                    print(mezua);
                }
                print("Zereginen bukaera");
            } catch (InterruptedException e) {
                print("haria ETEN da.");
            }
        });
        bigarren_haria.start();

        // Haria martxan dagoen bitartean, segundu bakoitzero pasa den denbora
        // totala kalkulatu eta 'pazientzia' denboratik pasatu bada, haria eten.
        while(bigarren_haria.isAlive()){
            print("Itxaroten nago");
            bigarren_haria.join(1000L);
            long bukaera = System.currentTimeMillis();
            long denbora = bukaera - hasiera;
            if (denbora > pazientzia && bigarren_haria.isAlive()){
                print("Ez dut gehiago itxarongo!");
                bigarren_haria.interrupt();
                bigarren_haria.join();
            }
        }
        print("BUKAERA");

    }

    static void print(String mezua){
        String hariIzena = Thread.currentThread().getName();
        System.out.format("%s: %s%n", hariIzena, mezua);
    }
}
