package prozesuAnitzekoProgramazioa_1.komunikazioa;

public class ProzesuA {
	public static void main(String[] args) {
		try {
			
			System.out.println("A Prozesua: Eragiketak egiten...");

			// Eragiketak, zereginak egin
			int emaitza = 5 + 3;
			Thread.sleep(2000);
			
			// Erabiltzaileari mezua (irteera estandarra, SYSTEM.OUT)
			System.out.println("A Prozesua: Emaitza da " + emaitza);

			// Emaitza Prozesu Nagusira bidali (SYSTEM.ERR erabiliz mezuetatik bereizteko)
			System.err.println(emaitza); // System.err bakarrik erabiliko da emaitza Prozesu Nagusiari pasatzeko
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
