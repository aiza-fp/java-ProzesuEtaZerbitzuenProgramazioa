package prozesuAnitzekoProgramazioa_1;
/**
 * Tarte batean dauden zenbakien arteko batura egiten du
 * Zenbaki horiek programaren lehen bi parametro bezala pasatuko zaizkio
 */
public class Batuketa {

    public static void main(String[] args) {
        long pid = ProcessHandle.current().pid();
        System.out.println("Nire PID da: " + pid + " - Sarrerak: " + args[0] + ", " + args[1]);
        System.out.println("Nire PID da: " + pid + " - Emaitza: " +  batura(args));
    }
    public static long batura(String[] args) {
        long emaitza=0;
        for (int i=Integer.valueOf(args[0]);i<=Integer.valueOf(args[1]);i++){
        	emaitza+=i;
        }
        return emaitza; 
    }
}