package prozesuAnitzekoProgramazioa_1;

public class ProzesuAbiarazlea {
    public void exekutatu(String ruta){

            ProcessBuilder pb;
            try {
                    pb = new ProcessBuilder(ruta);
                    pb.start();
            } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            }

    }
    /**
     * @param args
     */
    public static void main(String[] args) {
            String ruta=
                    "C:\\Program Files\\Adobe\\Acrobat DC\\Acrobat\\Acrobat.exe";
            ProzesuAbiarazlea pa=new ProzesuAbiarazlea();
            pa.exekutatu(ruta);
            System.out.println("Bukatuta");
    }

}