package prozesuAnitzekoProgramazioa_1;

public class Batuketa {
    public int batu(int n1, int n2){
            int emaitza=0;
            for (int i=n1;i<=n2;i++){
                    emaitza=emaitza+i;
            }
            //System.out.println("Prozesua: " + ProcessHandle.current().pid() + " Emaitza: " + emaitza);
            return emaitza;
    }
}