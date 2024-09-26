package prozesuAnitzekoProgramazioa_1.komunikazioa;

public class ProzesuA {
    public static void main(String[] args) {
        try {
            // Mensaje al usuario (salida estándar)
            System.out.println("Proceso A: Realizando una operación...");

            // Realiza una operación (por ejemplo, suma de dos números)
            int result = 5 + 3;
            Thread.sleep(2000);
            // Mensaje al usuario (salida estándar)
            System.out.println("Proceso A: El resultado es " + result);

            // Enviar el resultado al Proceso Principal (usando System.err para separarlo de los mensajes)
            System.err.println(result);  // System.err se utiliza solo para pasar el resultado al Proceso Principal
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
