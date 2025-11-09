import Clases.Hotel;
import Clases.Usuario;
import Enums.TipoHabitacion;
import Exceptions.ExceptionCredencialesInvalidas;
import Exceptions.ExceptionHabitacionDuplicada;
import Exceptions.ExceptionUsuarioDuplicado;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        Hotel miHotel=new Hotel("Las Ostias");

        try{
            miHotel.RegistrarCliente("Anibal",29763522, "Argentina","Martinez 3340", "cli", "cli");
            miHotel.registrarRecepcionista("Mabel",29040871,"Argentina","Martinez 3340", "recep", "recep");
            miHotel.RegistrarAdministrador("Carlos",29880648,"Cuba","Martinez 3340", "admin", "admin");
        }catch (ExceptionUsuarioDuplicado e){
            System.out.println(e.getMessage());
        }

        try {
            miHotel.agregarHabitacion("1", TipoHabitacion.SIMPLE, 10000);
            miHotel.agregarHabitacion("2", TipoHabitacion.SIMPLE, 10000);
            miHotel.agregarHabitacion("3", TipoHabitacion.DOBLE, 15000);
            miHotel.agregarHabitacion("4", TipoHabitacion.DOBLE, 15000);
            miHotel.agregarHabitacion("5", TipoHabitacion.DOBLE, 20000);
            miHotel.agregarHabitacion("6", TipoHabitacion.SIMPLE, 20000);
            miHotel.agregarHabitacion("7", TipoHabitacion.SUITE, 30000);
            miHotel.agregarHabitacion("8", TipoHabitacion.SUITE, 30000);
            miHotel.agregarHabitacion("9", TipoHabitacion.SIMPLE, 10000);
            miHotel.agregarHabitacion("10", TipoHabitacion.DOBLE, 15000);

        }catch (ExceptionHabitacionDuplicada e){
            e.getMessage();
        }





        Scanner teclado=new Scanner(System.in);
        int opcion = -1;

        do{
            System.out.println("Sistema Hotel\n");
            System.out.println("1- Login");
            System.out.println("2- Registrar Nuevo Usuario");
            System.out.println("0- Salir\n");

            try {
                System.out.println("Ingrese opcion: ");
                opcion = teclado.nextInt();

            switch (opcion) {
                case 1 -> {
                    teclado.nextLine();
                    System.out.println("Ingrese eMail: ");

                    String mail = teclado.nextLine();
                    System.out.println("Ingrese contraseña:");

                    String contra = teclado.nextLine();

                    try {
                        Usuario u = miHotel.login(mail, contra);
                        if (u != null) {
                            System.out.println("Bienvenido " + u.getNombre() + " !");
                            u.mostrarMenu(miHotel);
                        }
                    } catch (ExceptionCredencialesInvalidas e) {
                        System.out.println(e.getMessage());
                    }

                }
                case 2 -> {

                    teclado.nextLine();

                    System.out.println("Ingrese nombre: ");
                    String nombre = teclado.nextLine();

                    System.out.println("Ingrese dni:");
                    int dni = teclado.nextInt();
                    teclado.nextLine();

                    System.out.println("Ingrese origen: ");
                    String origen = teclado.nextLine();

                    System.out.println("Ingrese direccion Origen: ");
                    String direccion = teclado.nextLine();

                    System.out.println("Ingrese Mail: ");
                    String email = teclado.nextLine();

                    System.out.println("Ingrese contraseña: ");
                    String contra = teclado.nextLine();


                    try {

                         String msg = miHotel.RegistrarCliente(nombre, dni, origen, direccion, email, contra);
                        System.out.println(msg);
                    } catch (ExceptionUsuarioDuplicado e) {
                        System.out.println(e.getMessage());
                    }


                }
            }

            }catch (InputMismatchException e){
                System.out.println("Error: Debe ingresar solo numeros, no letras");
                teclado.nextLine(); //PARA EVITAR CICLO INFINITO
            }



        }while(opcion!=0);




    }

}