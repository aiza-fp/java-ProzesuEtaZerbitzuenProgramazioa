package hariAnitzekoProgramazioa_2.hariakItxaron;

import java.lang.Thread.State;

/**
 * Nola ITXARON hari bat bukatu arte edo denbora bat itxaron eta jarraitu
 */
public class HariakItxaron_waiting {

	public static void main(String[] args) throws InterruptedException {
		
		Thread hariNagusia = Thread.currentThread();
		
		//  haria
		Thread haria_1 = new Thread(() -> {
			System.out.println(Thread.currentThread().getName() + " hasi da. Programa nagusiaren egoera egoera monitorizatzen segunduro.");
			try {
				for (int i=5; i>0; i--) {
					System.out.println(hariNagusia.getName() + " hariaren egoera: " + hariNagusia.getState());
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " haria bukatu da.");
			});
		haria_1.setName("HARIA");
		haria_1.start();

		System.out.println("Programa nagusia, " + haria_1.getName() + " haria bukatu arte itxaroten.");

		// 1 haria itxaron bukatu arte
		haria_1.join();

		System.out.println("Programa nagusia, " + haria_1.getName() + " haria bukatu da");
					
		System.out.println("Programa nagusia bukatu da.");

	}
}