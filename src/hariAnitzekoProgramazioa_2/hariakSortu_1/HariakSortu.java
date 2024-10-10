package hariAnitzekoProgramazioa_2.hariakSortu_1;
/**
 * Hariak sortu eta abiarazi Thread-etik hedatzen diren klaseetan
 */
public class HariakSortu {
    public static void main(String[] args) {

        Thread haria_1 = new ThreadHedatuz();
        haria_1.start();
        Thread haria_2 = new ThreadHedatuz();
        haria_2.start();

        System.out.println("Nire izena " + Thread.currentThread().getName()
                + " da, egoera: " + Thread.currentThread().getState());
    }
}
