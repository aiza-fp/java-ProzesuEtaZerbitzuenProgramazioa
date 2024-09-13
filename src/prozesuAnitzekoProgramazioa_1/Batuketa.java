package prozesuAnitzekoProgramazioa_1;

public class Batuketa {

    public static void main(String[] args) {
        long pid = ProcessHandle.current().pid();
        System.out.println("Nire PID da: " + pid + " - Emaitza: " +  batura(args));
    }
    public static int batura(String[] args) {
    	return Integer.valueOf(args[0]) + Integer.valueOf(args[1]); 
    }
}