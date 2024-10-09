package hariAnitzekoProgramazioa_2.hariakSortu_2;

public class RunnableInplementatuz implements Runnable{
    @Override
    public void run() {
        System.out.println("Nire izena " + Thread.currentThread().getName()
                + " da, egoera: " + Thread.currentThread().getState());    }
}
