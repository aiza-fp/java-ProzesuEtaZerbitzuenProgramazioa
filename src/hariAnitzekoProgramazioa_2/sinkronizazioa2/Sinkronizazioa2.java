package hariAnitzekoProgramazioa_2.sinkronizazioa2;

public class Sinkronizazioa2 {
	public static void main(String[] args) throws InterruptedException {
		
		Kontainer kontainerra = new Kontainer();

		// Mezua itxarongo duen haria
		Thread itxaronHaria = new Thread(() -> {
			Thread.currentThread().setName("ITXARON_HARIA");
			kontainerra.itxaronMezua();
		});

		// Mezua bidaliko duen haria 2 segundu itxaron ostean.
		Thread bidaliHaria = new Thread(() -> {
			try {
				Thread.currentThread().setName("BIDALI_HARIA");
				// Mezua bidali arte 2 segunduko atzerapena simulatu
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			kontainerra.bidaliMezua("Kaixo mezua BIDALTZEN duen haritik!");
		});

		// Hariak abiarazi
		itxaronHaria.start();
		bidaliHaria.start();
		
		itxaronHaria.join();
		System.out.println("Bukatuta.");
	}

}

