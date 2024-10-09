package hariAnitzekoProgramazioa_2.hariakSortu_1;
/**
 * Hariak sortu eta abiarazi Thread-etik hedatzen diren klaseetan
 */
public class HariakSortu {
    public static void main(String[] args) {

        Thread hilo1 = new ThreadHedatuz();
        hilo1.start();
        Thread hilo2 = new ThreadHedatuz();
        hilo2.start();

        System.out.println("Nire izena " + Thread.currentThread().getName()
                + " da, egoera: " + Thread.currentThread().getState());
    }
}
