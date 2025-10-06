package hariAnitzekoProgramazioa_2.hariakSortu_1;

/**
 * Hariak sortu eta abiarazi Thread-etik hedatzen diren klaseetan
 */
public class HariakSortu {
	public static void main(String[] args) {
		System.out.println(
				"Nire izena " + Thread.currentThread().getName() + " da, egoera: " + Thread.currentThread().getState());
		
		Thread haria_1 = new ThreadHedatuz();
		System.out.println("Hariaren izena " + haria_1.getName() + " da, egoera: " + haria_1.getState());
		
		haria_1.start();
		Thread haria_2 = new ThreadHedatuz();
		
		System.out.println("Hariaren izena " + haria_2.getName() + " da, egoera: " + haria_2.getState());
		haria_2.start();
		
		try {
			// Itxaron 1 haria bukatu arte:
			haria_1.join();
			System.out.println("Hariaren izena " + haria_1.getName() + " da, egoera: " + haria_1.getState());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(
				"Nire izena " + Thread.currentThread().getName() + " da, egoera: " + Thread.currentThread().getState());
	}
}
