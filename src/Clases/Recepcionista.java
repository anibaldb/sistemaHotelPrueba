package Clases;

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







    public String getTipoUsuario() {
        return "Recepcionista";
    }



    @Override
    public void mostrarMenu( Hotel hotel) {

        String str="";

        str+="Menu:\n";
        str+="1- Agregar Cliente\n";
        str+="2- Crear Reserva\n";
        str+="1- Ver habitaciones disponibles\n";
        str+="1- Ver estado habitaciones\n";
        str+="1- Realizar CHECK-IN\n";
        str+="1- Realizar CHECK-OUT\n";
        str+="1- Agregar Cliente\n";

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


        System.out.print("Ingrese fecha de entrada (AAAA-MM-DD): ");
        LocalDate entrada = LocalDate.parse(teclado.nextLine());

        System.out.print("Ingrese fecha de salida (AAAA-MM-DD): ");
        LocalDate salida = LocalDate.parse(teclado.nextLine());

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
}
