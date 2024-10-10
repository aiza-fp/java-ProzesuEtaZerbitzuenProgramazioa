package hariAnitzekoProgramazioa_2.hariakEten;

/**
 * Nola ETEN hari baten exekuzioa
 * {@HariakItxaron} programan oinarrituta
 * 
*/
public class HariakEten {

	public static void main(String[] args) throws InterruptedException {
		
		int haria_1_denbora_segundutan = 25;
		int haria_2_denbora_segundutan = 10;
		int programa_nagusia_haria_1_itxaron_segundutan = 8;
		
		// 1 haria
		Thread haria_1 = new Thread(() -> {
			System.out.println(Thread.currentThread().getName() + " haria hasi da, " + haria_1_denbora_segundutan + " segunduko iraupena.");
			try {
				Thread.sleep(haria_1_denbora_segundutan * 1000);
			} catch (InterruptedException e) {
				System.out.println(Thread.currentThread().getName() + " haria eten egin da," + e.getLocalizedMessage());
			}
			System.out.println(Thread.currentThread().getName() + " haria bukatu da.");
			});
		haria_1.start();
		
		// 2. haria
		Thread haria_2 = new Thread(() -> {
			System.out.println(Thread.currentThread().getName() + " haria hasi da, " + haria_2_denbora_segundutan + " segunduko iraupena.");
			try {
				Thread.sleep(haria_2_denbora_segundutan * 1000);
			} catch (InterruptedException e) {
				System.out.println(Thread.currentThread().getName() + " haria eten egin da," + e.getLocalizedMessage());
			}
			System.out.println(Thread.currentThread().getName() + " haria bukatu da.");
			});
		haria_2.start();

		System.out.println("Programa nagusia, " + haria_2.getName() + " haria bukatu arte itxaroten.");

		// 2 haria itxaron bukatu arte
		haria_2.join();

		System.out.println("Programa nagusia, " + haria_2.getName() + " haria bukatu da");
		
		System.out.println("Programa nagusia, " + haria_1.getName() + " haria bukatu arte itxaroten " +  programa_nagusia_haria_1_itxaron_segundutan + " segundutan zehar.");

		// 2 haria itxaron denbora jakin bat
		haria_2.join(programa_nagusia_haria_1_itxaron_segundutan * 1000);
		// 2 haria ETEN
		haria_2.interrupt();
		
		// segundu bat itxarongo dugu azken mezua ez nahasteko harien mezuekin.
		Thread.sleep(1000);
		System.out.println("Programa nagusia bukatu da.");

	}
}
