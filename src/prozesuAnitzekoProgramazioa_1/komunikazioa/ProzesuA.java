package prozesuAnitzekoProgramazioa_1.komunikazioa;

public class ProzesuA {
    public static void main(String[] args) {
        try {
            // Mensaje al usuario (salida est�ndar)
            System.out.println("Proceso A: Realizando una operaci�n...");

            // Realiza una operaci�n (por ejemplo, suma de dos n�meros)
            int result = 5 + 3;
            Thread.sleep(2000);
            // Mensaje al usuario (salida est�ndar)
            System.out.println("Proceso A: El resultado es " + result);

            // Enviar el resultado al Proceso Principal (usando System.err para separarlo de los mensajes)
            System.err.println(result);  // System.err se utiliza solo para pasar el resultado al Proceso Principal
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
