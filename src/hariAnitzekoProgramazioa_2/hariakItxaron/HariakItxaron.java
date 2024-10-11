package hariAnitzekoProgramazioa_2.hariakItxaron;
/**
 * Nola ITXARON hari bat bukatu arte edo denbora bat itxaron eta jarraitu
 */
public class HariakItxaron {

	public static void main(String[] args) throws InterruptedException {
		
		int haria_1_denbora_segundutan = 10;
		int haria_2_denbora_segundutan = 25;
		int programa_nagusia_haria_2_itxaron_segundutan = 100;
		
		// 1 haria
		Thread haria_1 = new Thread(() -> {
			System.out.println(Thread.currentThread().getName() + " haria hasi da, " + haria_1_denbora_segundutan + " segunduko iraupena.");
			try {
				Thread.sleep(haria_1_denbora_segundutan * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " haria bukatu da.");
			});
		haria_1.setName("1 HARIA");
		haria_1.start();
		
		// 2 haria
		Thread haria_2 = new Thread(() -> {
			System.out.println(Thread.currentThread().getName() + " haria hasi da, " + haria_2_denbora_segundutan + " segunduko iraupena.");
			try {
				Thread.sleep(haria_2_denbora_segundutan * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " haria bukatu da.");
			});
		haria_2.setName("2 HARIA");
		haria_2.start();

		System.out.println("Programa nagusia, " + haria_1.getName() + " haria bukatu arte itxaroten.");

		// 1 haria itxaron bukatu arte
		haria_1.join();

		System.out.println("Programa nagusia, " + haria_1.getName() + " haria bukatu da");
		
		System.out.println("Programa nagusia, " + haria_2.getName() + " haria bukatu arte itxaroten " +  programa_nagusia_haria_2_itxaron_segundutan + " segundutan zehar.");

		// 2 haria itxaron denbora jakin bat
		haria_2.join(programa_nagusia_haria_2_itxaron_segundutan * 1000);

		System.out.println("Programa nagusia bukatu da.");

	}
}