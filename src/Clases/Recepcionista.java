package Clases;

import Enums.EstadoHabitacion;
import Exceptions.ExceptionUsuarioDuplicado;
import Exceptions.FechaInvalidaException;
import Interfaces.MetodosUsuarios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Recepcionista extends Usuario implements MetodosUsuarios {

    public Recepcionista(String nombre, int dni, String origen, String direccionOrigen, String eMail, String contrasenia) {
        super(nombre, dni, origen, direccionOrigen, eMail, contrasenia);
    }







    public String getTipoUsuario() {
        return "Recepcionista";
    }



    @Override
    public void mostrarMenu( Hotel hotel) {

        SistemaUsuarios sistemaUsuarios = new SistemaUsuarios();
        Scanner teclado = new Scanner(System.in);
        int opcion;

        String str="";

        str+="Menu:\n";
        str+="1 - Agregar Cliente\n";
        str+="2 - Crear Reserva\n";
        str+="3 - Ver habitaciones disponibles\n";
        str+="4 - Cambiar estado habitacion\n";
        str+="5 - Ver estado habitaciones\n";
        str+="6 - Realizar CHECK-IN\n";
        str+="7 - Realizar CHECK-OUT\n";
        str+="0 - Salir\n\n";
        str+="Ingrese opcion: ";
        opcion = teclado.nextInt();
        teclado.nextLine();

        switch (opcion) {
            case 1->{
                System.out.print("Ingrese el DNI del cliente: ");
                int dni = teclado.nextInt();
                teclado.nextLine();

                Cliente cliente = sistemaUsuarios.buscarPorDni(dni);

                if (cliente == null) {
                    System.out.println("No se encontró cliente con ese DNI.");
                    System.out.print("¿Ingresar datos de cliente nuevo: \n");


                    System.out.print("Nombre: ");
                    String nombre = teclado.nextLine();


                    System.out.print("Origen: ");
                    String origen = teclado.nextLine();

                    System.out.print("Dirección: ");
                    String direccion = teclado.nextLine();

                    System.out.print("Email: ");
                    String email = teclado.nextLine();

                    System.out.print("Contraseña: ");
                    String contra = teclado.nextLine();

                    try {
                        sistemaUsuarios.registrarCliente(nombre, dni, origen, direccion, email, contra);

                        System.out.println("Cliente registrado exitosamente.");
                    } catch (ExceptionUsuarioDuplicado e) {
                        System.out.println(e.getMessage());
                        return;
                    }

                }
            }




            case 2->{
                crearReserva(hotel);

            }
            case 3->{
                mostrarHabitacionesDisponibles(hotel);

            }
            case 4->{

            }
            case 5->{

            }
            case 6->{

            }

        }


        System.out.println(str);





    }

    @Override
    public void crearReserva(Hotel hotel) {
        Scanner teclado = new Scanner(System.in);
        SistemaUsuarios sistemaUsuarios = hotel.getSistemaUsuarios();

        System.out.print("Ingrese el DNI del cliente: ");
        int dniCliente = teclado.nextInt();
        teclado.nextLine();

        Cliente cliente = sistemaUsuarios.buscarPorDni(dniCliente);

        if (cliente == null) {
            System.out.println("No se encontró cliente con ese DNI.");
            System.out.print("¿Desea registrarlo? (s/n): ");
            String rta = teclado.nextLine();
            if (rta.equalsIgnoreCase("s")) {
                System.out.print("Nombre: ");
                String nombre = teclado.nextLine();



                System.out.print("Origen: ");
                String origen = teclado.nextLine();

                System.out.print("Dirección: ");
                String direccion = teclado.nextLine();

                System.out.print("Email: ");
                String email = teclado.nextLine();

                System.out.print("Contraseña: ");
                String contra = teclado.nextLine();

                try {
                    sistemaUsuarios.registrarCliente(nombre, dniCliente, origen, direccion, email, contra);
                    cliente =  sistemaUsuarios.buscarPorDni(dniCliente);
                    System.out.println("Cliente registrado exitosamente.");
                } catch (ExceptionUsuarioDuplicado e) {
                    System.out.println(e.getMessage());
                    return;
                }
            } else {
                System.out.println("Operación cancelada.");
                return;
            }
        }

        System.out.println("Creando reserva para el cliente: " + cliente.getNombre());


        LocalDate entrada = leerFecha("Ingrese fecha de entrada (AAAA-MM-DD): ");
        LocalDate salida = leerFecha("Ingrese fecha de salida (AAAA-MM-DD): ");



        if (!salida.isAfter(entrada)) {
            System.out.println("La fecha de salida debe ser posterior a la fecha de entrada.");
            return;
        }


        List<Habitacion> disponibles = new ArrayList<>();

        System.out.println("\nHabitaciones disponibles entre " + entrada + " y " + salida + ":\n");

        for (Habitacion h : hotel.getHabitaciones()) {
            boolean libre = true;

            for (Reserva r : hotel.getReservas()) {
                if (r.getHabitacion().equals(h)) {
                    boolean seCruzan = !(salida.isBefore(r.getFechaInicio()) ||
                            entrada.isAfter(r.getFechaEgreso().minusDays(1)));
                    if (seCruzan) {
                        libre = false;
                        break;
                    }
                }
            }

            if (libre) {
                disponibles.add(h);
                System.out.println(h);
            }
        }

        if (disponibles.isEmpty()) {
            System.out.println("No hay habitaciones disponibles para esas fechas.");
            return;
        }

        System.out.print("\nIngrese el ID de la habitación que desea reservar: ");
        String idSeleccionado = teclado.nextLine();

        Habitacion seleccionada = null;
        for (Habitacion h : disponibles) {
            if (h.getId().equalsIgnoreCase(idSeleccionado)) {
                seleccionada = h;
                break;
            }
        }

        if (seleccionada == null) {
            System.out.println("El ID ingresado no corresponde a ninguna habitación disponible.");
            return;
        }


        Reserva nueva = new Reserva(cliente, seleccionada, entrada, salida);
        hotel.getReservas().add(nueva);
        cliente.agregarReservaTomada(nueva);

        System.out.println("\nReserva creada exitosamente!");
        System.out.println("Desde " + entrada + " hasta " + salida);
        System.out.println("Habitación: " + seleccionada.getId() + " - " + seleccionada.getTipo());
        System.out.println("Cliente: " + cliente.getNombre());
    }

    private LocalDate leerFecha(String mensaje) {
        Scanner teclado = new Scanner(System.in);
        LocalDate fecha = null;
        boolean fechaValida = false;

        while (!fechaValida) {
            System.out.print(mensaje);
            String entrada = teclado.nextLine();

            try {
                fecha = LocalDate.parse(entrada);
                fechaValida = true;
            } catch (Exception e) {
                System.out.println("Formato inválido. Ingrese la fecha en formato AAAA-MM-DD (por ejemplo: 2025-11-06).");
            }
        }

        return fecha;
    }

    public void mostrarHabitacionesDisponibles(Hotel hotel) {
        LocalDate entrada = leerFecha("Ingrese fecha de entrada (AAAA-MM-DD): ");
        LocalDate salida = leerFecha("Ingrese fecha de salida (AAAA-MM-DD): ");



        if (!salida.isAfter(entrada)) {
            System.out.println("La fecha de salida debe ser posterior a la fecha de entrada.");
            return;
        }


        List<Habitacion> disponibles = new ArrayList<>();

        System.out.println("\nHabitaciones disponibles entre " + entrada + " y " + salida + ":\n");

        for (Habitacion h : hotel.getHabitaciones()) {
            boolean libre = true;

            for (Reserva r : hotel.getReservas()) {
                if (r.getHabitacion().equals(h)) {
                    boolean seCruzan = !(salida.isBefore(r.getFechaInicio()) ||
                            entrada.isAfter(r.getFechaEgreso().minusDays(1)));
                    if (seCruzan) {
                        libre = false;
                        break;
                    }
                }
            }

            if (libre) {
                disponibles.add(h);
                System.out.println(h);
            }
        }

        if (disponibles.isEmpty()) {
            System.out.println("No hay habitaciones disponibles para esas fechas.");
            return;
        }
    }

    public void cambiarEstadoHabitacion (Hotel hotel) {
        Scanner teclado = new Scanner(System.in);


        StringBuilder sb=new StringBuilder();
        for (Habitacion h : hotel.getHabitaciones()) {
            sb.append("Habitacion: "+h.getId()+" Estado: "+h.getEstado()+"\n");
            if (h.getEstado()==EstadoHabitacion.FUERA_DE_SERVICIO){
                sb.append(h.getMotivoFueraServicio());
            }
        }
        System.out.println(sb);
        System.out.println("Ingrese ID de habitacion a cambiar estado: ");
        String  opcion = teclado.nextLine();
        for (Habitacion h : hotel.getHabitaciones()) {

            if (opcion.equalsIgnoreCase(h.getId())) {
                System.out.println("Elija estado: 1 - Disponible, 2 - Desinfeccion, 3 - Fuera de servicio");
                int opcion2 = teclado.nextInt();
                switch (opcion2) {
                    case 1->{ h.setEstado(EstadoHabitacion.DISPONIBLE); }
                    case 2->{ h.setEstado(EstadoHabitacion.DESINFECCION);}
                    case 3->{
                        System.out.println("Ingrese motivo de fuera de servicio: ");
                        String motivo=teclado.nextLine();
                        h.setEstado(EstadoHabitacion.FUERA_DE_SERVICIO);
                        h.setearMotivoFueraServicio(motivo);
                    }
                }
            }
        }



    }
}
