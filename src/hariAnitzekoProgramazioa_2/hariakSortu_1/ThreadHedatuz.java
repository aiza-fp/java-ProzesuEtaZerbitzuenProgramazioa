package hariAnitzekoProgramazioa_2.hariakSortu_1;

public class ThreadHedatuz extends Thread{
    @Override
    public void run() {
        System.out.println("Nire izena " + this.getName() + " da, egoera: " + this.getState());
    }
}
