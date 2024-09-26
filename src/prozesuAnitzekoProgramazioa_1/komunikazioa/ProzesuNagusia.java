package prozesuAnitzekoProgramazioa_1.komunikazioa;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.ProcessBuilder.Redirect;

public class ProzesuNagusia {

    public static void main(String[] args) {
        try {
            // Crear el proceso A (que genera el resultado)
            ProcessBuilder processBuilderA = new ProcessBuilder("java", "-cp", "bin", "prozesuAnitzekoProgramazioa_1.komunikazioa.ProzesuA");
            
            processBuilderA.inheritIO();
            processBuilderA.redirectError(Redirect.PIPE);
            // Iniciar el proceso A
            Process processA = processBuilderA.start();
            System.out.println("NAGUSIA: Comienza Proceso A.");
       
            // Crear el proceso B (que recibe el resultado)
            ProcessBuilder processBuilderB = new ProcessBuilder("java", "-cp", "bin", "prozesuAnitzekoProgramazioa_1.komunikazioa.ProzesuB");

            processBuilderB.inheritIO();
            processBuilderB.redirectInput(Redirect.PIPE);
            // Iniciar el proceso B
            Process processB = processBuilderB.start();
            System.out.println("NAGUSIA: Comienza Proceso B.");

            // Capturar el resultado (error stream) de Proceso A
            BufferedReader errorAReader = new BufferedReader(new InputStreamReader(processA.getErrorStream()));
            String resultA = errorAReader.readLine();  // Leer el resultado del error stream (solamente el resultado)
            System.out.println("NAGUSIA: Resultado obtenido de Proceso A: " + resultA);

            // Esperar a que el Proceso A termine
            int exitCodeA = processA.waitFor();
            System.out.println("NAGUSIA: Proceso A terminó con código: " + exitCodeA);

            // Pasar el resultado del Proceso A al Proceso B (a través de su entrada estándar)
            OutputStream outputToProcessB = processB.getOutputStream();
            outputToProcessB.write(resultA.getBytes());  // Escribir el resultado de A
            outputToProcessB.write("\n".getBytes());  // Asegurarse de que se envíe un salto de línea
            outputToProcessB.flush();  // Asegurarse de que el dato sea enviado
            outputToProcessB.close();  // Cerrar la entrada de B para indicar que no habrá más datos

            // Esperar a que el Proceso B termine
            int exitCodeB = processB.waitFor();
            System.out.println("NAGUSIA: Proceso B terminó con código: " + exitCodeB);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
