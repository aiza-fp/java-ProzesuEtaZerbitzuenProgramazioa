package hariAnitzekoProgramazioa_2.hariakSortu_3;
/**
 * Nola sortu eta abiarazi hariak programa nagusitik
 */
public class HariakSortu {

	public static void main(String[] args) {

		Thread haria = new Thread(() -> System.out.println("Nire izena " + Thread.currentThread().getName()
				+ " da, egoera: " + Thread.currentThread().getState()));
		haria.start();

		new Thread(() -> System.out.println(
				"Nire izena " + Thread.currentThread().getName() + " da, egoera: " + Thread.currentThread().getState()))
				.start();
	}
}