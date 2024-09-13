package prozesuAnitzekoProgramazioa_1;

public class BatuketaAbiarazlea {
	public Process abiaraziBatuketa(Integer n1, Integer n2) {
		String klasea = "prozesuAnitzekoProgramazioa_1.Batuketa";
		ProcessBuilder pb;
		Process prozesua = null;
		try {
			pb = new ProcessBuilder("java", klasea, n1.toString(), n2.toString());           

            prozesua = pb.start();
            
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prozesua;
	}

	public static void main(String[] args) {
		BatuketaAbiarazlea ba = new BatuketaAbiarazlea();
		ba.abiaraziBatuketa(1, 50);
		ba.abiaraziBatuketa(51, 100);
		
		System.out.println("Main Bukatuta");
	}
}