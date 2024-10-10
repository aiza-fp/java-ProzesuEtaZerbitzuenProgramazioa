package hariAnitzekoProgramazioa_2.hariakSortu_2;

/**
 * Hariak sortu eta abiarazi Runnable inplementatzen duten klaseetan
 */
public class HariakSortu {

	public static void main(String[] args) {

		Runnable zeregina = new RunnableInplementatuz();
		Thread haria = new Thread(zeregina);
		haria.start();

		System.out.println(
				"Nire izena " + Thread.currentThread().getName() + " da, egoera: " + Thread.currentThread().getState());
	}
}
