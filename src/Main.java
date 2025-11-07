import Clases.Cliente;
import Clases.Hotel;
import Clases.SistemaUsuarios;
import Clases.Usuario;
import Enums.TipoHabitacion;
import Exceptions.ExceptionCredencialesInvalidas;
import Exceptions.ExceptionHabitacionDuplicada;
import Exceptions.ExceptionUsuarioDuplicado;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        Hotel miHotel=new Hotel("Las Ostias");

        try{
            miHotel.getSistemaUsuarios().registrarCliente("Anibal",29763522, "Argentina","Martinez 3340", "admin", "admin");
        }catch (ExceptionUsuarioDuplicado e){
            System.out.println(e.getMessage());
        }

        try {
            miHotel.agregarHabitacion("1", TipoHabitacion.SIMPLE,10000);

        }catch (ExceptionHabitacionDuplicada e){
            e.getMessage();
        }





        Scanner teclado=new Scanner(System.in);
        int opcion;

        do{
            System.out.println("Sistema Hotel\n");
            System.out.println("1- Login");
            System.out.println("2- Registrar Nuevo Usuario");
            System.out.println("0- Salir\n");

            System.out.println("Ingrese opcion: ");
            opcion= teclado.nextInt();

            switch (opcion){
                case 1-> {
                    teclado.nextLine();
                    System.out.println("Ingrese eMail: ");

                    String mail=teclado.nextLine();
                    System.out.println("Ingrese contraseña:");

                    String contra= teclado.nextLine();

                    try {
                        Usuario u = miHotel.getSistemaUsuarios().login(mail, contra);
                        if (u != null) {
                            System.out.println("Bienvenido " + u.getNombre() + " !");
                            u.mostrarMenu(miHotel);
                        }
                    }catch (ExceptionCredencialesInvalidas e){
                        System.out.println(e.getMessage());
                    }

                }
                case 2->{

                    teclado.nextLine();

                    System.out.println("Ingrese nombre: ");
                    String nombre= teclado.nextLine();

                    System.out.println("Ingrese dni:");
                    int dni= teclado.nextInt();
                    teclado.nextLine();

                    System.out.println("Ingrese origen: ");
                    String origen= teclado.nextLine();

                    System.out.println("Ingrese direccion Origen: ");
                    String direccion= teclado.nextLine();

                    System.out.println("Ingrese Mail: ");
                    String email= teclado.nextLine();

                    System.out.println("Ingrese contraseña: ");
                    String contra= teclado.nextLine();

                    try{

                        System.out.println(miHotel.getSistemaUsuarios().registrarCliente(nombre,dni,origen,direccion,email,contra));
                    }catch (ExceptionUsuarioDuplicado e){
                        System.out.println(e.getMessage());
                    }


                }
            }



        }while(opcion!=0);


        System.out.println("Sistema Hotel\n");

    }

}