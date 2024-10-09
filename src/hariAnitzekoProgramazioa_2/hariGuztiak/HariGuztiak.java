package hariAnitzekoProgramazioa_2.hariGuztiak;
/**
 * Prozesu honi lotuta dauden hari guztiak zerrendatu hainbat ezaugarrirekin.
 */
public class HariGuztiak {

    public static void main(String[] args) {

        Thread.getAllStackTraces().keySet().forEach(thread ->{
            System.out.println("ID: " + thread.getId());
            System.out.println("Izena: " + thread.getName());
            System.out.println("Egoera: " + thread.getState());
            System.out.println("Bizirik: " + thread.isAlive());
            System.out.println("Etenda: " + thread.isInterrupted());
            System.out.println("Lehentasuna: " + thread.getPriority());
            System.out.println("==========");
        });
    }
}
