package hariAnitzekoProgramazioa_2.hariakSortu_2;

/**
 * Hariak sortu eta abiarazi Runnable inplementatzen duten klaseetan
 */
public class HariakSortu {

	public static void main(String[] args) {

		Runnable task = new RunnableInplementatuz();
		Thread hilo = new Thread(task);
		hilo.start();

		System.out.println(
				"Nire izena " + Thread.currentThread().getName() + " da, egoera: " + Thread.currentThread().getState());
	}
}
