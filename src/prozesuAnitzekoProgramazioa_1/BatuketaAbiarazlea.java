package prozesuAnitzekoProgramazioa_1;

public class BatuketaAbiarazlea {
    public void abiaraziBatuketa(Integer n1,
                    Integer n2){
            String klasea="prozesuAnitzekoProgramazioa_1.Batuketa";
            ProcessBuilder pb;
            try {
                    pb = new ProcessBuilder(
                                    "java",klasea,
                                    n1.toString(),
                                    n2.toString());
                    pb.start();
            } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            }
    }
    public static void main(String[] args){
            Batuketa l=new Batuketa();
            int emaitza1 = l.batu(1, 51);
            int emaitza2 = l.batu(51, 100);
            System.out.println("Emaitza prozesu 1: " + emaitza1);
            System.out.println("Emaitza prozesu 2: " + emaitza2);
    }
}