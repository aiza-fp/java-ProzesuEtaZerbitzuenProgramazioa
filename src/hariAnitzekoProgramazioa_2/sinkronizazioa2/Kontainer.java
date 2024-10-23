package hariAnitzekoProgramazioa_2.sinkronizazioa2;

/**
 * WAIT eta NOTIFY metodoen erabilera hariak sinkronizatzeko Wait () eta notify
 * () metodoak "synchronized" bloke edo metodo sinkronizatu baten barrutik
 * aipatu behar dira. Kasu honetan, metodoak sinkronizatzeak "itxaronMezua()"
 * eta "bidaliMezua()" ziurtatzen du harien arteko koordinazioa eta baliabide
 * partekatua (mezua) modu seguruan eta kontsistentean erabiltzea.
 * 
 * Javako objektu bakoitzak monitore (edo "giltzarrapo") bat du lotuta,
 * sinkronizazioa kontrolatzeko erabiltzen dena. wait() eta notify() metodoek
 * objektu horren monitorean eragiten dute, eta horrek esan nahi du bi hariek
 * sinkronizatuta egon behar dutela monitore berean (objektu berean) koordinatu
 * ahal izateko.
 * 
 * 
 */
class Kontainer {
	private String mezua;

	// Mezu bat jasotzeko metodoa
	public synchronized void itxaronMezua() {
		try {
			System.out.println(Thread.currentThread().getName() + ": Mezu bat itxaroten...");
			// Itxaron beste hari batek notify() deitu arte
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + ": Mezua jasota: " + mezua);
	}

	// Itxaroten dagoen hariari mezua bidali eta jakinarazi
	public synchronized void bidaliMezua(String msg) {
		this.mezua = msg;
		System.out.println(Thread.currentThread().getName() + ": Mezua bidaltzen: " + mezua);
		// Itxaroten dagoen haria jakinarazten du
		notify();
	}
}
