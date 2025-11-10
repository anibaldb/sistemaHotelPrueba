package Clases;

import Enums.TipoHabitacion;
import Exceptions.ExceptionHabitacionDuplicada;
import Exceptions.ExceptionUsuarioDuplicado;

import java.util.Scanner;

public class Administrador extends Usuario {

    public Administrador(String nombre, int dni, String origen, String direccionOrigen, String eMail, String contrasenia) {
        super(nombre, dni, origen, direccionOrigen, eMail, contrasenia);


    }





    @Override
    public String getTipoUsuario() {
        return "Administrador";
    }



    @Override
    public void  mostrarMenu(Hotel hotel) {

        Scanner teclado=new Scanner(System.in);
        int opcion;

        do{
            System.out.println("Menu:");
            System.out.println("1 - Crear cliente");
            System.out.println("2 - Crear Recepcionista");
            System.out.println("3 - Crear habitacion");
            System.out.println("4 - Listar usuarios");
            System.out.println("5 - Listar habitaciones");
            System.out.println("6 - Realizar Backup");
            System.out.println("0 - Salir");
            System.out.println("Ingrese opcion: (0 para salir del sistema) ");

            opcion= teclado.nextInt();

            switch (opcion){
                case 1: {
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

                        System.out.println(hotel.RegistrarCliente(nombre,dni,origen,direccion,email,contra));
                    }catch (ExceptionUsuarioDuplicado e){
                        System.out.println(e.getMessage());
                    }

                    break;
                }
                case 2: {
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

                        System.out.println(hotel.registrarRecepcionista(nombre,dni,origen,direccion,email,contra));
                    }catch (ExceptionUsuarioDuplicado e){
                        System.out.println(e.getMessage());
                    }

                    break;
                }
                case 3: {
                    teclado.nextLine();

                    System.out.println("Ingrese Id: ");
                    String id= teclado.nextLine();

                    System.out.println("Ingrese tipo: 1-simple, 2-doble. 3-suite");
                    TipoHabitacion tipo=null;
                    opcion= teclado.nextInt();
                    switch (opcion){
                        case 1: {
                            tipo=TipoHabitacion.SIMPLE;
                            break;
                        }

                        case 2: {
                            tipo=TipoHabitacion.DOBLE;
                            break;
                        }

                        case 3: {
                            tipo=TipoHabitacion.SUITE;
                            break;
                        }
                    }
                    teclado.nextLine();

                    System.out.println("Ingrese precio x noche: ");
                    double precio= teclado.nextDouble();



                    try{

                        System.out.println(hotel.agregarHabitacion(id,tipo,precio));
                    }catch (ExceptionHabitacionDuplicada e){
                        System.out.println(e.getMessage());
                    }

                    break;
                }
                case 4: {
                    mostrarUsuarios(hotel);


                    break;
                }

                case 5:{
                    mostrarHabitaciones(hotel);
                    break;
                }
                case 6:{
                    realizarBackup(hotel);
                    break;
                }
                case 0: {
                    System.out.println("Saliendo...");}

                default: {
                    System.out.println("Opcion invalida");

                    break;
                }
            }



        }while(opcion!=0);


    }

    public void  mostrarUsuarios(Hotel hotel){
      hotel.mostrarUsuarios();
    }

    public void  mostrarHabitaciones(Hotel hotel){
        hotel.mostrarHabitaciones();
    }


    public void realizarBackup(Hotel hotel){}



}
