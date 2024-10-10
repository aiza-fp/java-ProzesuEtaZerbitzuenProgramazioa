package hariAnitzekoProgramazioa_2.sinkronoAsinkrono;

/**
 * Harien exekuzio sinkrono eta asinkronoaren arteko diferentzia
 */
public class SinkronoAsinkrono {

    public static void main(String[] args) throws InterruptedException {

        Thread hari1 = new Thread(() -> System.out.println("Kaixo 1"));
        Thread hari2 = new Thread(() -> System.out.println("Kaixo 2"));
        Thread hari3 = new Thread(() -> System.out.println("Kaixo 3"));

        // ASINKRONOA
        System.out.println("Hasiera Asinkronoa");
        hari1.start();        
        hari2.start();
        hari3.start();        
        System.out.println("Bukaera Asinkronoa");

        //Segundu bat itxaron asinkronoak bukatzeko:
        Thread.sleep(1000);
        
        // SINKRONOA: hari nagusia blokeatuta geratzen da abiarazi duen haria bukatu arte
        System.out.println("Hasiera Sinkronoa");        
        hari1.run();
        hari2.run();
        hari3.run();
        System.out.println("Bukaera Sinkronoa");
    }
}
