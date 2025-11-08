package Clases;

import Enums.EstadoHabitacion;
import Enums.EstadoReserva;
import Exceptions.ExceptionUsuarioDuplicado;
import Interfaces.MetodosUsuarios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Recepcionista extends Usuario implements MetodosUsuarios {

    public Recepcionista(String nombre, int dni, String origen, String direccionOrigen, String eMail, String contrasenia) {
        super(nombre, dni, origen, direccionOrigen, eMail, contrasenia);
    }


    public String  getTipoUsuario() {
        return "Recepcionista";
    }


    @Override
    public void mostrarMenu(Hotel hotel) {

        SistemaUsuarios sistemaUsuarios = new SistemaUsuarios();
        Scanner teclado = new Scanner(System.in);
        int opcion;

        do{
            String str="";
            str += "Menu:\n";
            str += "1 - Agregar Cliente\n";
            str += "2 - Crear Reserva\n";
            str += "3 - Ver habitaciones disponibles\n";
            str += "4 - Ver estado habitaciones \n";
            str += "5 - Realizar CHECK-IN\n";
            str += "6 - Realizar CHECK-OUT\n";
            str += "7 - Cancelar reserva\n";
            str += "0 - Salir\n\n";
            str += "Ingrese opcion: ";
            System.out.println(str);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1 -> {
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

                        }

                    }
                }


                case 2 -> {
                    crearReserva(hotel);

                }
                case 3 -> {
                    mostrarHabitacionesDisponibles(hotel);

                }
                case 4 -> {
                    mostrarHabitacionesEstado(hotel);

                }
                case 5 -> {
                    realizarChkIn(hotel);


                }
                case 6 -> {
                    realizarChkOut(hotel);

                }
                case 7 ->{
                    System.out.println("Cancelar reserva por DNI (opcion 1) o por Id de reserva (opcion 2) ?");
                    System.out.println("Ingrese opcion: ");
                    int opcion2 = teclado.nextInt();
                    teclado.nextLine();
                    switch (opcion2) {
                        case 1 -> {cancelarReservaPorDni(hotel);}
                        case 2 -> {cancelarReservaPorId(hotel);}

                    }
                }
                case 0->{
                    System.out.println("Cerrando sesion...");
                }

            }

        }while(opcion!=0);

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
                    cliente = sistemaUsuarios.buscarPorDni(dniCliente);
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

    public void mostrarHabitacionesEstado(Hotel hotel) {
        Scanner teclado = new Scanner(System.in);

        StringBuilder sb = new StringBuilder();
        sb.append("=== Lista de Habitaciones ===\n");
        for (Habitacion h : hotel.getHabitaciones()) {
            sb.append("Habitación: ").append(h.getId())
                    .append(" | Estado: ").append(h.getEstado());
            if (h.getEstado() == EstadoHabitacion.FUERA_DE_SERVICIO) {
                sb.append(" | Motivo: ").append(h.getMotivoFueraServicio());
            }
            sb.append("\n");
        }

        System.out.println(sb);

        String sino = "";
        System.out.println(" Desea cambiar estado de alguna habitacion? s/n");
        sino = teclado.nextLine();
        if (sino.equalsIgnoreCase("s")) {
            System.out.print("Ingrese ID de habitación a cambiar estado: ");
            String opcion = teclado.nextLine();
            boolean encontrada = false;

            for (Habitacion h : hotel.getHabitaciones()) {
                if (opcion.equalsIgnoreCase(h.getId())) {
                    encontrada = true;
                    System.out.println("Elija nuevo estado:");
                    System.out.println("1 - Disponible");
                    System.out.println("2 - Desinfección");
                    System.out.println("3 - Fuera de servicio");

                    int opcion2 = teclado.nextInt();
                    teclado.nextLine();

                    switch (opcion2) {
                        case 1 -> h.setEstado(EstadoHabitacion.DISPONIBLE);
                        case 2 -> h.setEstado(EstadoHabitacion.DESINFECCION);
                        case 3 -> {
                            System.out.print("Ingrese motivo de fuera de servicio: ");
                            String motivo = teclado.nextLine();
                            h.setEstado(EstadoHabitacion.FUERA_DE_SERVICIO);
                            h.setearMotivoFueraServicio(motivo);
                        }
                        default -> System.out.println("Opción inválida.");
                    }

                    System.out.println("Estado de la habitación actualizado.");
                    break;
                }
            }

            if (!encontrada) {
                System.out.println("No se encontró una habitación con el ID ingresado.");
            }

        } else if (sino.equals("n")) {
            System.out.println("Operación cancelada.");
        } else {
            System.out.println("Respuesta no válida. Debe ingresar 's' o 'n'.");
        }


    }

    public void cancelarReservaPorDni(Hotel hotel) {
        Scanner teclado = new Scanner(System.in);

        System.out.print("Ingrese DNI del cliente para cancelar reserva: ");
        int nroDni = teclado.nextInt();

        teclado.nextLine();

        Cliente cliente=hotel.getSistemaUsuarios().buscarPorDni(nroDni);

        if (cliente != null) {

            for (Reserva r : hotel.getReservas()) {
                if (r.getEstadoReserva()==EstadoReserva.PENDIENTE && r.getCliente().getDni() == nroDni) {
                    System.out.println(r.toString());
                }
            }
            System.out.println("Ingrese el nro de reserva a cancelar: ");
            int nroReserva = teclado.nextInt();
            for (Reserva r : hotel.getReservas()) {
                if (r.getId() == nroReserva) {

                    if (r.getEstadoReserva() == EstadoReserva.CANCELADA) {
                        System.out.println("La reserva ya está Cancelada.");

                    } else {
                        r.setEstadoReserva(EstadoReserva.CANCELADA);
                        System.out.println("Reserva N° " + r.getId() + "Cancelada con exito...");
                    }
                    break;
                }

        }



        }else {

            System.out.println("No se encontró ningún cliente con el dni ingresado.");

        }










    }

    public void cancelarReservaPorId(Hotel hotel) {
        Scanner teclado = new Scanner(System.in);

        System.out.print("Ingrese número de reserva a Cancelar: ");
        int nroReserva = teclado.nextInt();
        teclado.nextLine();


        boolean encontrada = false;

        for (Reserva r : hotel.getReservas()) {
            if (r.getId() == nroReserva) {
                encontrada = true;
                if (r.getEstadoReserva() == EstadoReserva.CANCELADA) {
                    System.out.println("La reserva ya está Cancelada.");

                } else {
                    r.setEstadoReserva(EstadoReserva.CANCELADA);
                    System.out.println("Reserva N° " + r.getId() + "Cancelada con exito...");
                }
                break;
            }
        }

        if (!encontrada) {
            System.out.println("No se encontró ninguna reserva con el número ingresado.");
        }
    }

    public void realizarChkIn(Hotel hotel) {
        Scanner teclado = new Scanner(System.in);

        System.out.print("Ingrese número de reserva para realizar CHECK-IN: ");
        int nroReserva = teclado.nextInt();
        teclado.nextLine();


        boolean encontrada = false;

        for (Reserva r : hotel.getReservas()) {
            if (r.getId() == nroReserva) {
                encontrada = true;
                if (r.getEstadoReserva() == EstadoReserva.ACTIVA) {
                    System.out.println("La reserva ya está activa.");
                } else if (r.getEstadoReserva() == EstadoReserva.CANCELADA) {
                    System.out.println("No se puede hacer CHECK-IN: la reserva está cancelada.");
                } else {
                    r.setEstadoReserva(EstadoReserva.ACTIVA);
                    System.out.println("CHECK-IN realizado correctamente para la reserva N° " + r.getId());
                }
                break;
            }
        }

        if (!encontrada) {
            System.out.println("No se encontró ninguna reserva con el número ingresado.");
        }
    }

    public void realizarChkOut(Hotel hotel) {

        Scanner teclado = new Scanner(System.in);

        System.out.print("Ingrese número de reserva para realizar CHECK-OUT: ");
        int nroReserva = teclado.nextInt();
        teclado.nextLine();


        boolean encontrada = false;

        for (Reserva r : hotel.getReservas()) {
            if (r.getId() == nroReserva) {
                encontrada = true;
                if (r.getEstadoReserva() == EstadoReserva.TERMINADA) {
                    System.out.println("La reserva ya está Terminada.");
                } else if (r.getEstadoReserva() == EstadoReserva.CANCELADA) {
                    System.out.println("No se puede hacer CHECK-OUT: la reserva está cancelada.");
                } else {
                    r.setEstadoReserva(EstadoReserva.TERMINADA);
                    System.out.println("CHECK-OUT realizado correctamente para la reserva N° " + r.getId());
                }
                break;
            }
        }

        if (!encontrada) {
            System.out.println("No se encontró ninguna reserva con el número ingresado.");
        }

    }

}





