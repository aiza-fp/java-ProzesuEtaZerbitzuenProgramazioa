package hariAnitzekoProgramazioa_2.hariakSortu_3;
/**
 * Nola sortu eta abiarazi hariak programa nagusitik
 * 'lambda' expresioak erabiliz, ez ditugu klaseak sortu behar
 */
public class HariakSortu {

	public static void main(String[] args) {
		
		Thread haria = new Thread(() -> {
			System.out.println(Thread.currentThread().getName() + ", haria hainbat lerrotan programatuz");
			System.out.println(Thread.currentThread().getName()
				+ ", egoera: " + Thread.currentThread().getState());
			});
		haria.start();

		new Thread(() -> System.out.println(
				Thread.currentThread().getName() + ", haria lerro bakarrean, egoera: " + Thread.currentThread().getState()))
				.start();
	}
}