package hariAnitzekoProgramazioa_2.itxaron;
/**
 * Nola itxaron hari bat bukatu arte edo denbora bat itxaron eta jarraitu
 */
public class HariakItxaron {

	public static void main(String[] args) {
		
		// 4 segundu iraungo dituen haria
		Thread haria_1 = new Thread(() -> {
			System.out.println(Thread.currentThread().getName() + " haria hasi da.");
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " haria bukatu da.");
			});
		haria_1.start();
		
		// 10 segundu iraungo dituen haria
		Thread haria_2 = new Thread(() -> {
			System.out.println(Thread.currentThread().getName() + " haria hasi da.");
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " haria bukatu da.");
			});
		haria_2.start();

		System.out.println("Programa nagusia, " + haria_1.getName() + " haria bukatu arte itxaroten.");
		try {
			haria_1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Programa nagusia, " + haria_1.getName() + "  haria bukatu da");
		
		System.out.println("Programa nagusia, " + haria_2.getName() + "  haria bukatu arte itxaroten 7 segundutan zehar.");
		try {
			haria_1.join(7000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Programa nagusia, " + haria_2.getName() + "  haria 7 segundu itxaron ondoren.");

	}
}