package prozesuAnitzekoProgramazioa_1.komunikazioa;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.ProcessBuilder.Redirect;
/**
 * Klase honek egingo duena da: A Prozesua abiaratu
 */
public class ProzesuNagusia {

    public static void main(String[] args) {
        try {
            // Crear el proceso A (que genera el resultado)
            ProcessBuilder processBuilderA = new ProcessBuilder("java", "-cp", "bin", "prozesuAnitzekoProgramazioa_1.komunikazioa.ProzesuA");
            
            processBuilderA.inheritIO();
            // Erroreen irteera (SYSTEM.ERR) erabiliko dugu subprozesuarekin komunikatzeko (emaitza irakurtzeko)
            processBuilderA.redirectError(Redirect.PIPE);
            
            // A Prozesua abiarazi
            Process processA = processBuilderA.start();
            System.out.println("NAGUSIA: A Prozesua abiarazi da.");
       
            // B Prozesua sortu (emaitzA jasoko duena)
            ProcessBuilder processBuilderB = new ProcessBuilder("java", "-cp", "bin", "prozesuAnitzekoProgramazioa_1.komunikazioa.ProzesuB");

            processBuilderB.inheritIO();
            // B Prozesuaren sarrera estandarrarekin gu komunikatuko gara, Prozesu Nagusia, emaitza bidaltzeko
            processBuilderB.redirectInput(Redirect.PIPE);
            
            // B Prozesua abiarazi
            Process processB = processBuilderB.start();
            System.out.println("NAGUSIA: B Prozesua abiarazi da.");

            // Emaitza jaso (getErrorStream) A Prozesutik
            BufferedReader errorAReader = new BufferedReader(new InputStreamReader(processA.getErrorStream()));
            String resultA = errorAReader.readLine();
            System.out.println("NAGUSIA: A Prozesutik lortu den emaitza: " + resultA);

            // Itxaron A Prozesua amaitu arte
            int exitCodeA = processA.waitFor();
            System.out.println("NAGUSIA: A Prozesua bukatu da irteera kode honekin: " + exitCodeA);

            // A Prozesutik lortu dugun emaitza B Prozesura pasatu (bere sarrera estandarraren bidez)
            OutputStream outputToProcessB = processB.getOutputStream();
            outputToProcessB.write(resultA.getBytes());  // A Prozesuaren emaitza idatzi
            outputToProcessB.write("\n".getBytes());  // Lerro bukaera adierazten duen karakterea bidaltzen dela ziurtatu
            outputToProcessB.flush();  // Ziurtatu datuak bidali direla
            outputToProcessB.close();  // B Prozesuaren sarrera itxi datu gehiago ez direla egongo adierazteko

            // Itxaron B Prozesua amaitu arte
            int exitCodeB = processB.waitFor();
            System.out.println("NAGUSIA: B Prozesua bukatu da irteera kode honekin: " + exitCodeB);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
