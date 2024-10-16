package hariAnitzekoProgramazioa_2.sinkronizazioa;
/**
 * Synchronized erabiliz Javan hariak sinkronizatzeko modua erakusten du blokeoen bidez
 */
public class Sinkronizazioa {

    private static final Object blokeoa = new Object(); //Blokeo obketu elkarbanatua

    public static void main(String[] args) throws InterruptedException {
        
        // 1 haria: blokeoa eutsi 5 segundutan zehar
        Thread haria_1 = new Thread(() -> {
            synchronized (blokeoa) {
                System.out.println(Thread.currentThread().getName() + ": Baliabidea blokeatuta...");
                try {
                    Thread.sleep(5000); 
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ": Baliabide blokeatua utzi.");
            }
        });
        haria_1.setName("1 HARIA");
        
        // 2. haria: Blokeo berdina eskuratzen saiatuko da eta blokeatuta geratuko da
        Thread haria_2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() +": Baliabidea hartzen saiatzen...");
            synchronized (blokeoa) {
                System.out.println(Thread.currentThread().getName() +": Blokeoa eskuratuta!");
            }
        });
        haria_2.setName("2 HARIA");
        
        haria_1.start(); // 1 haria abiarazi, blokeoari eutsiko dio 5 segundutan
        Thread.sleep(1000); // Itxaron segundu bat lehen hariak blokeoa eskuratu duela ziurtatzeko
        haria_2.start(); // 2. haria abiarazi, blokeatuta gertauko dena

        // Programa nagusian bigarren haria monitorizatuko dugu
        while (haria_2.getState() != Thread.State.TERMINATED) {
            System.out.println(haria_2.getName() +" hariaren egoera: " + haria_2.getState());
            Thread.sleep(500); // // segundu erdi bakoitzean frogatu egoera
        }

        System.out.println("Programa bukatuta.");
    }
}
