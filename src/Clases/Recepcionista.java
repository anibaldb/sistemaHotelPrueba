package Clases;

import Enums.EstadoHabitacion;
import Enums.EstadoReserva;
import Exceptions.ExceptionUsuarioDuplicado;
import Interfaces.MetodosUsuarios;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Recepcionista extends Usuario implements MetodosUsuarios {

    public Recepcionista(String nombre, int dni, String origen, String direccionOrigen, String eMail, String contrasenia) {
        super(nombre, dni, origen, direccionOrigen, eMail, contrasenia, "Recepcionista");
    }


    @Override
    public String geteMail() {
        return super.geteMail();
    }

    @Override
    public void mostrarMenu(Hotel hotel) {

        SistemaUsuarios sistemaUsuarios = new SistemaUsuarios();
        Scanner teclado = new Scanner(System.in);
        int opcion;
        do {
            String str = "";
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
            opcion = ConsolaUtils.leerEntero(teclado, "Ingrese una opcion: ");


            switch (opcion) {
                case 1 -> {

                    int dni = ConsolaUtils.leerEntero(teclado, "Ingrese DNI del cliente:");


                    Cliente cliente = hotel.getSistemaUsuarios().buscarPorDni(dni);

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

                    }else{
                        System.out.println("Cliente existente.");
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
                case 7 -> {
                    System.out.println("Cancelar reserva por DNI (opcion 1) o por Id de reserva (opcion 2) ?");

                    int opcion2 = ConsolaUtils.leerEntero(teclado, "Ingrese opcion: ");

                    switch (opcion2) {
                        case 1 -> {
                            cancelarReservaPorDni(hotel);
                        }
                        case 2 -> {
                            cancelarReservaPorId(hotel);
                        }

                    }
                }
                case 0 -> {
                    System.out.println("Cerrando sesion...");
                }
                default ->{
                    System.out.println("ERROR, DEBE INGRESAR: 1,2,3,4,5,6,7 O 0");
                }

            }

        } while (opcion != 0);

    }

    @Override
    public void crearReserva(Hotel hotel) {
        Scanner teclado = new Scanner(System.in);


        int dniCliente = ConsolaUtils.leerEntero(teclado, "Ingrese el DNI del cliente: ");


        Cliente cliente = hotel.buscarClientePorDni(dniCliente);

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
                    hotel.RegistrarCliente(nombre, dniCliente, origen, direccion, email, contra);
                    cliente = hotel.buscarClientePorDni(dniCliente);
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


        List<Habitacion> disponibles = hotel.obtenerHabitacionesDisponibles(entrada, salida);

        if (disponibles.isEmpty()) {
            System.out.println("No hay habitaciones disponibles para esas fechas");
            return;
        }

        System.out.println("\nHabitaciones disponibles entre " + entrada + " y " + salida + ":\n");
        disponibles.forEach(System.out::println);

        System.out.print("\nIngrese el ID de la habitación que desea reservar: ");
        int idSeleccionado = teclado.nextInt();

        Habitacion seleccionada = hotel.buscarHabitacionPorId(idSeleccionado);
        if (seleccionada == null || !disponibles.contains(seleccionada)) {
            System.out.println("El ID ingresado no corresponde a ninguna habitación disponible.");
            return;
        }


        Reserva nueva = new Reserva(hotel, dniCliente, idSeleccionado, entrada, salida);

        try {
            hotel.agregarReserva(nueva);
        } catch (Exception e) {
            e.getMessage();
        }

        cliente.agregarReservaTomada(nueva);

        hotel.getSistemaUsuarios().guardarEnJSON();
        hotel.guardarEnJSON();

        System.out.println("\nReserva creada exitosamente!");
        System.out.println("Desde " + entrada + " hasta " + salida);
        System.out.println("Habitación: " + seleccionada.getId() + " - " + seleccionada.getTipo());
        System.out.println("Cliente: " + cliente.getNombre());
        System.out.println("Cantidad noches: " + nueva.getCantNoches());
        System.out.println("Monto: $" + nueva.getPrecioReserva());
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


        List<Habitacion> disponibles = hotel.obtenerHabitacionesDisponibles(entrada, salida);

        if (disponibles.isEmpty()) {
            System.out.println("No hay habitaciones disponibles para esas fechas.");
            return;
        } else {
            System.out.println("\nHabitaciones disponibles entre " + entrada + " y " + salida + ":\n");
            disponibles.forEach(System.out::println);
        }
    }

    public void mostrarHabitacionesEstado(Hotel hotel) {
        Scanner teclado = new Scanner(System.in);

        System.out.println("=== Lista de Habitaciones ===");
        for (Habitacion h : hotel.obtenerHabitaciones()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Habitación: ").append(h.getId())
                    .append(" | Estado: ").append(h.getEstado());
            if (h.getEstado() == EstadoHabitacion.FUERA_DE_SERVICIO) {
                sb.append(" | Motivo: ").append(h.getMotivoFueraServicio());
            }
            System.out.println(sb);
        }

        System.out.print("\n¿Desea cambiar el estado de alguna habitación? (s/n): ");
        String sino = teclado.nextLine().trim();

        if (!sino.equalsIgnoreCase("s")) {
            System.out.println("Operación cancelada.");
            return;
        }

        // Buscar habitación
        System.out.print("Ingrese ID de habitación a cambiar estado: ");
        int idHabitacion = teclado.nextInt();
        teclado.nextLine(); // limpiar buffer

        Habitacion habitacion = hotel.buscarHabitacionPorId(idHabitacion);

        if (habitacion == null) {
            System.out.println("No se encontró una habitación con ese ID.");
            return;
        }

        // Selección de nuevo estado
        System.out.println("\nElija nuevo estado:");
        System.out.println("1 - Disponible");
        System.out.println("2 - Desinfección");
        System.out.println("3 - Fuera de servicio");

        int opcion = teclado.nextInt();
        teclado.nextLine(); // limpiar buffer

        switch (opcion) {
            case 1 -> {
                habitacion.setEstado(EstadoHabitacion.DISPONIBLE);
                habitacion.setearMotivoFueraServicio(null);
            }
            case 2 -> {
                habitacion.setEstado(EstadoHabitacion.DESINFECCION);
                habitacion.setearMotivoFueraServicio(null);
            }
            case 3 -> {
                System.out.print("Ingrese motivo de fuera de servicio: ");
                String motivo = teclado.nextLine();
                habitacion.setEstado(EstadoHabitacion.FUERA_DE_SERVICIO);
                habitacion.setearMotivoFueraServicio(motivo);
            }
            default -> {
                System.out.println("Opción inválida.");
                return;
            }
        }

        System.out.println("Estado de la habitación actualizado correctamente.");
    }

    public void cancelarReservaPorDni(Hotel hotel) {
        Scanner teclado = new Scanner(System.in);
        int nroDni = ConsolaUtils.leerEntero(teclado, "Ingrese DNI del cliente para cancelar reserva:");
        Cliente cliente = hotel.buscarClientePorDni(nroDni);

        if (cliente == null) {
            System.out.println("No se encontro el cliente con ese DNI.");
            return;
        }


        List<Reserva> reservasCliente = hotel.buscarReservasPorDni(nroDni);
        if (reservasCliente.isEmpty()) {
            System.out.println("El cliente no tiene reservas registradas.");
            return;
        }

        System.out.println("\n=== Reservas del cliente ===");
        for (Reserva r : reservasCliente) {
            System.out.println(r);
        }

        System.out.print("Ingrese el número de reserva a cancelar: ");
        int idReserva = teclado.nextInt();
        teclado.nextLine();

        boolean exito = hotel.cancelarReserva(idReserva);

        if (exito) {
            System.out.println("Reserva N° " + idReserva + " cancelada con éxito.");
        } else {
            System.out.println("No se pudo cancelar la reserva (ya cancelada o inexistente).");
        }

    }


    public void cancelarReservaPorId(Hotel hotel) {
        Scanner teclado = new Scanner(System.in);

        System.out.print("Ingrese número de reserva a Cancelar: ");
        int nroReserva = teclado.nextInt();
        teclado.nextLine();


        boolean encontrada = hotel.cancelarReserva(nroReserva);

        if (encontrada) {
            System.out.println("Reserva N° " + nroReserva + " cancelada con éxito.");
        } else {
            // Podría no existir o ya estar cancelada
            Reserva reserva = hotel.buscarReservaPorId(nroReserva);
            if (reserva == null) {
                System.out.println("No se encontró ninguna reserva con el número ingresado.");
            } else if (reserva.getEstadoReserva() == EstadoReserva.CANCELADA) {
                System.out.println("La reserva ya estaba cancelada.");
            }
        }
    }

    public void realizarChkIn(Hotel hotel) {
        Scanner teclado = new Scanner(System.in);


        int nroReserva = ConsolaUtils.leerEntero(teclado,"Ingrese número de reserva para realizar CHECK-IN:");

        Reserva reserva = hotel.buscarReservaPorId(nroReserva);

        if (reserva == null) {
            System.out.println("No se encontró ninguna reserva con el número ingresado.");
            return;
        }

        // Verificamos el estado sin acceder a listas internas
        if (reserva.getEstadoReserva() == EstadoReserva.ACTIVA) {
            System.out.println("La reserva ya está activa.");
        } else if (reserva.getEstadoReserva() == EstadoReserva.CANCELADA) {
            System.out.println("No se puede hacer CHECK-IN: la reserva está cancelada.");
        } else {
            reserva.setEstadoReserva(EstadoReserva.ACTIVA);
            System.out.println("CHECK-IN realizado correctamente para la reserva N° " + reserva.getId());
        }
    }

    public void realizarChkOut(Hotel hotel) {

        Scanner teclado = new Scanner(System.in);

        System.out.print("Ingrese número de reserva para realizar CHECK-OUT: ");
        int nroReserva = teclado.nextInt();
        teclado.nextLine();


        Reserva reserva = hotel.buscarReservaPorId(nroReserva);

        if (reserva == null) {
            System.out.println("No se encontró ninguna reserva con el número ingresado.");
            return;
        }
        // Lógica del check-out sin acceder a estructuras internas
        if (reserva.getEstadoReserva() == EstadoReserva.TERMINADA) {
            System.out.println("La reserva ya está terminada.");
        } else if (reserva.getEstadoReserva() == EstadoReserva.CANCELADA) {
            System.out.println("No se puede hacer CHECK-OUT: la reserva está cancelada.");
        } else {
            reserva.setEstadoReserva(EstadoReserva.TERMINADA);
            System.out.println("CHECK-OUT realizado correctamente para la reserva N° " + reserva.getId());
        }

    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("nombre", getNombre());
        json.put("dni", getDni());
        json.put("origen", getOrigen());
        json.put("direccionOrigen", getDireccionOrigen());
        json.put("eMail", geteMail());
        json.put("contrasenia", getContrasenia());
        json.put("tipo", "Recepcionista");
        return json;
    }

    public static Recepcionista fromJSON(JSONObject json) {
        return new Recepcionista(
                json.getString("nombre"),
                json.getInt("dni"),
                json.getString("origen"),
                json.getString("direccionOrigen"),
                json.getString("eMail"),
                json.getString("contrasenia")
        );
    }

}





