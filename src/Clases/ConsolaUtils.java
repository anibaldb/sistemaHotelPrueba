package Clases;
import java.util.InputMismatchException;
import java.util.Scanner;
public class ConsolaUtils {
    //Métodos para validar opciones que tienen que ser nuemros si o sí..

    public static int leerEntero(Scanner scanner, String mensaje) {
        int numero = 0;
        boolean esValido = false;

        while (!esValido) {
            try {
                System.out.println(mensaje);
                numero = scanner.nextInt();
                esValido = true;
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número. Intente de nuevo");
            } finally {
                scanner.nextLine();
            }
        }
        return numero;
    }

    public static double leerDouble(Scanner scanner, String mensaje) {
        double numero = 0.0;
        boolean esValido = false;

        while (!esValido) {
            try {
                System.out.println(mensaje);
                numero = scanner.nextDouble();
                esValido = true;
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número. Intente de nuevo");
            } finally {
                scanner.nextLine(); // Limpiar el buffer
            }
        }
        return numero;
    }

}
