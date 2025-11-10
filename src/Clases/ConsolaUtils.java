package Clases;
import java.util.InputMismatchException;
import java.util.Scanner;
public class ConsolaUtils {
    //Lee un número entero de la consola de forma segura.
    // Repite la pregunta hasta que el usuario ingrese un número válido.
    public static int leerEntero(Scanner scanner, String mensaje) {
        int numero = 0;
        boolean esValido = false;

        while (!esValido) {
            try {
                System.out.println(mensaje);
                numero = scanner.nextInt();
                esValido = true; // Si llegamos aca, es un número, salimos del bucle
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número. Intente de nuevo.");
            } finally {
                scanner.nextLine(); //  Limpia el buffer del scanner siempre.
            }
        }
        return numero;
    }

}
