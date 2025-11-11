package Clases;

import Enums.EstadoReserva;
import Interfaces.MetodosUsuarios;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Cliente extends Usuario implements MetodosUsuarios{
    List<Reserva> reservasTomadas;

    public Cliente(String nombre, int dni, String origen, String direccionOrigen, String eMail, String contrasenia ) {
        super(nombre, dni, origen, direccionOrigen, eMail, contrasenia, "Cliente");
        this.reservasTomadas = new ArrayList<>();
    }




    public void agregarReservaTomada(Reserva reserva) {

        reservasTomadas.add(reserva);


    }





    public String mostrarReservasTomadas(){
        String rta="";
        for(Reserva r: reservasTomadas){
            rta+=r.toString()+"\n";
        }
        return rta;
    }

    @Override
    public void mostrarMenu(Hotel hotel) {
        Scanner teclado=new Scanner(System.in);
        int opcion;

        do{
            System.out.println("Menu:\n");
            System.out.println("1- Crear Reserva\n");
            System.out.println("2- Ver Historial reservas\n");
            System.out.println("0- Salir\n");
            System.out.println("Ingrese opcion: ");
            opcion= teclado.nextInt();
            teclado.nextLine();

            switch (opcion){
                case 1->{

                    crearReserva(hotel);

                }
                case 2->{

                    System.out.println(mostrarReservasTomadas());

                }
                case 0->{
                    System.out.println("Cerrando sesion...");
                }
            }

        }while (opcion!=0);



    }


    public void crearReserva(Hotel hotel) {
        Scanner teclado = new Scanner(System.in);

        System.out.println("Creando reserva para el cliente logueado: " + getNombre());


        LocalDate entrada = leerFecha("Ingrese fecha de entrada (AAAA-MM-DD): ");
        LocalDate salida = leerFecha("Ingrese fecha de salida (AAAA-MM-DD): ");


        if (!salida.isAfter(entrada)) {
            System.out.println("La fecha de salida debe ser posterior a la fecha de entrada.");
            return;
        }


        List<Habitacion> disponibles = hotel.obtenerHabitacionesDisponibles(entrada, salida);

        if(disponibles.isEmpty()){
            System.out.println("No hay habitaciones disponibles para esas fechas");
            return;
        }
        System.out.println("\nHabitaciones disponibles entre " + entrada + " y " + salida + ":\n");




        System.out.print("\nIngrese el ID de la habitación que desea reservar: ");
        int idSeleccionado = teclado.nextInt();

        Habitacion seleccionada = hotel.buscarHabitacionPorId(idSeleccionado);


        if (seleccionada == null) {
            System.out.println("El ID ingresado no corresponde a ninguna habitación disponible.");
            return;
        }


        Reserva nueva = new Reserva(hotel,this.getDni(), idSeleccionado, entrada, salida);
        try{
            hotel.agregarReserva(nueva);
        }catch (Exception e){
            e.getMessage();
        }


        this.agregarReservaTomada(nueva);

        System.out.println("\nReserva creada exitosamente!");
        System.out.println("Desde " + entrada + " hasta " + salida);
        System.out.println("Habitación: " + seleccionada.getId() + " - " + seleccionada.getTipo());
        System.out.println("Cliente: " + getNombre());
        System.out.println("Cantidad noches: "+nueva.getCantNoches());
        System.out.println("Monto: $"+nueva.getPrecioReserva());
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






    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("nombre", getNombre());
        json.put("dni", getDni());
        json.put("origen", getOrigen());
        json.put("direccionOrigen", getDireccionOrigen());
        json.put("eMail", geteMail());
        json.put("contrasenia", getContrasenia());
        json.put("tipo", getTipo());


        JSONArray reservasArray = new JSONArray();
        for (Reserva r : reservasTomadas) {
            reservasArray.put(r.toJSON());
        }
        json.put("reservasTomadas", reservasArray);

        return json;
    }

    public List<Reserva> getReservasTomadas() {
        return reservasTomadas;
    }

    public static Cliente fromJSON(JSONObject json) {
        Cliente c = new Cliente(
                json.getString("nombre"),
                json.getInt("dni"),
                json.getString("origen"),
                json.getString("direccionOrigen"),
                json.getString("eMail"),
                json.getString("contrasenia")
        );


        JSONArray reservasArray = json.optJSONArray("reservasTomadas");
        if (reservasArray != null) {
            for (int i = 0; i < reservasArray.length(); i++) {
                JSONObject reservaJson = reservasArray.getJSONObject(i);
                Reserva reserva = Reserva.fromJSON(reservaJson, null);
                c.agregarReservaTomada(reserva);
            }
        }

        return c;
    }


}






