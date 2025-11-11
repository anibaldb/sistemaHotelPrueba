import Clases.Hotel;
import Clases.JSONUtiles;
import Clases.Usuario;
import Enums.TipoHabitacion;
import Exceptions.ExceptionCredencialesInvalidas;
import Exceptions.ExceptionHabitacionDuplicada;
import Exceptions.ExceptionUsuarioDuplicado;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;
import Clases.ConsolaUtils;
import org.json.JSONObject;

public class Main {
    public static void main(String[] args) {


        Hotel miHotel;
        File archivo = new File("hotelDatos.json");
        if (archivo.exists()) {
            System.out.println("Cargando datos del hotel...");
            String contenido = JSONUtiles.downloadJSON("hotelDatos");
            JSONObject jsonHotelLeido = new JSONObject(contenido);
            miHotel = Hotel.fromJSON(jsonHotelLeido); }
        else { System.out.println("Creando un nuevo hotel vacío...");
                miHotel = new Hotel("Gran Hotel");

            try{
                miHotel.RegistrarCliente("Anibal",29763522, "Argentina","Martinez 3340", "cli", "cli");
                miHotel.registrarRecepcionista("Mabel",29040871,"Argentina","Martinez 3340", "recep", "recep");
                miHotel.RegistrarAdministrador("Carlos",29880648,"Cuba","Martinez 3340", "admin", "admin");
            }catch (ExceptionUsuarioDuplicado e){
                System.out.println(e.getMessage());
            }

            try {
                miHotel.agregarHabitacion(TipoHabitacion.SIMPLE, 10000);
                miHotel.agregarHabitacion(TipoHabitacion.SIMPLE, 10000);
                miHotel.agregarHabitacion(TipoHabitacion.DOBLE, 15000);
                miHotel.agregarHabitacion( TipoHabitacion.DOBLE, 15000);
                miHotel.agregarHabitacion(TipoHabitacion.DOBLE, 20000);
                miHotel.agregarHabitacion(TipoHabitacion.SIMPLE, 20000);
                miHotel.agregarHabitacion(TipoHabitacion.SUITE, 30000);
                miHotel.agregarHabitacion( TipoHabitacion.SUITE, 30000);
                miHotel.agregarHabitacion(TipoHabitacion.SIMPLE, 10000);
                miHotel.agregarHabitacion( TipoHabitacion.DOBLE, 15000);

            }catch (ExceptionHabitacionDuplicada e){
                e.getMessage();
            }
        }

        miHotel.guardarEnJSON();





        Scanner teclado=new Scanner(System.in);
        int opcion = -1;

        do{
            System.out.println("Sistema Hotel\n");
            System.out.println("1- Login");
            System.out.println("2- Registrar Nuevo Usuario");
            System.out.println("0- Salir\n");

            opcion = ConsolaUtils.leerEntero(teclado, "Ingrese opcion: ");

            switch (opcion) {
                case 1 -> {

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



                    System.out.println("Ingrese nombre: ");
                    String nombre = teclado.nextLine();

                    int dni = ConsolaUtils.leerEntero(teclado, "Ingrese dni:");

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





        }while(opcion!=0);




    }

}