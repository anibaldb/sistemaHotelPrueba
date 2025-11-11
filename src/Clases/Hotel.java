package Clases;

import Enums.EstadoHabitacion;
import Enums.EstadoReserva;
import Enums.TipoHabitacion;
import Exceptions.ExceptionCredencialesInvalidas;
import Exceptions.ExceptionHabitacionDuplicada;
import Exceptions.ExceptionUsuarioDuplicado;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Hotel {

    private String nombre;
    private ClaseGenerica<Habitacion> habitaciones;
    private ClaseGenerica<Reserva> reservas;
    private SistemaUsuarios sistemaUsuarios;


    public Hotel(String nombre) {
        this.nombre=nombre;
        this.habitaciones = new ClaseGenerica<>();
        this.reservas = new ClaseGenerica<>();
        this.sistemaUsuarios=new SistemaUsuarios();
    }

    public SistemaUsuarios getSistemaUsuarios(){
        return sistemaUsuarios;
    }

    public void setSistemaUsuarios(SistemaUsuarios sistemaUsuarios) {
        this.sistemaUsuarios = sistemaUsuarios;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ClaseGenerica<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    public List<Habitacion>obtenerHabitaciones(){
        return new ArrayList<>(habitaciones.getElementos());
    }

    public ClaseGenerica<Reserva> getReservas() {
        return reservas;
    }

    public void mostrarHabitaciones(){
        habitaciones.recorrer();
    }

    public void mostrarUsuarios(){
        sistemaUsuarios.listarUsuarios();
    }

    //METODOS PARA USUARIOS
    public String RegistrarCliente(String nombre, int dni, String origen, String direccionOrigen, String eMail, String contrasenia) throws ExceptionUsuarioDuplicado, ExceptionUsuarioDuplicado {
        return sistemaUsuarios.registrarCliente(nombre, dni, origen, direccionOrigen, eMail, contrasenia);
    }

    public String RegistrarAdministrador(String nombre, int dni, String origen, String direccionOrigen, String eMail, String contrasenia) throws ExceptionUsuarioDuplicado, ExceptionUsuarioDuplicado {
        return sistemaUsuarios.registrarAdministrador(nombre, dni, origen, direccionOrigen, eMail, contrasenia);
    }

    public String registrarRecepcionista(String nombre, int dni, String origen, String direccionOrigen, String eMail, String contrasenia) throws ExceptionUsuarioDuplicado, ExceptionUsuarioDuplicado {
        return sistemaUsuarios.registrarRecepcionista(nombre, dni, origen, direccionOrigen, eMail, contrasenia);
    }

    public Usuario login(String eMail, String contrasenia) throws ExceptionCredencialesInvalidas{
        return sistemaUsuarios.login(eMail, contrasenia);
    }
    //METODO RESERVA
    public void agregarReserva(Reserva r) throws Exception{
        reservas.agregar(r);
    }

    public Cliente buscarClientePorDni(int dni){
        return sistemaUsuarios.buscarPorDni(dni);
    }

    //AGREGAR HABITACION
    public String agregarHabitacion(TipoHabitacion tipo, double precioXNoche ) throws ExceptionHabitacionDuplicada {

        Habitacion habitacion=new Habitacion(tipo,precioXNoche);
        try {
            habitaciones.agregar(habitacion);

        }catch (Exception e){
            e.getMessage();
        }

        return "Habitacion agregada correctamente";
    }

    public List<Habitacion> obtenerHabitacionesDisponibles(LocalDate entrada, LocalDate salida) {

        List<Habitacion> disponibles = new ArrayList<>();

        for (Habitacion h : habitaciones.getElementos()) {
            boolean libre = true;
            for (Reserva r : reservas.getElementos()) {
                if (r.getEstadoReserva() != EstadoReserva.PENDIENTE)
                    continue;

                if (r.getHabitacion().equals(h)) {

                    boolean seCruzan = !(salida.isBefore(r.getFechaInicio()) || entrada.isAfter(r.getFechaEgreso().minusDays(1)));

                    if (seCruzan) {
                        libre = false;
                        break;
                    }
                }
            }

            if (libre) disponibles.add(h);
        }
        return disponibles;
    }

    //METODO QUE BUSCA RESERVA POR ID
    public Reserva buscarReservaPorId(int id) {
        for (Reserva r : this.reservas.getElementos()) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }
    //METODO PARA BUSCAR UNA RESERVA POR DNI

    public List<Reserva> buscarReservasPorDni(int dni) {
        List<Reserva> resultado = new ArrayList<>();
        for (Reserva r : this.reservas.getElementos()) {
            if (r.getCliente().getDni() == dni) {
                resultado.add(r);
            }
        }
        return resultado;
    }

    //CANCELAR RESERVA PASANDO SU ID
    public boolean cancelarReserva(int idReserva) {

        for (Reserva r : this.reservas.getElementos()) {
            if (r.getId() == idReserva) {
                if (r.getEstadoReserva() == EstadoReserva.CANCELADA) {
                    return false;
                } else {
                    r.setEstadoReserva(EstadoReserva.CANCELADA);
                    return true;
                }
            }
        }
        return false;
    }

    //METODO QUE BUSCA HABITACION POR ID, DEVUELVE LA HABITACION SINO NULL
    public Habitacion buscarHabitacionPorId(int id) {
        for (Habitacion h : habitaciones.getElementos()) {
            if (h.getId() == id) {
                return h;
            }
        }
        return null;
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("nombre", nombre);

        JSONArray habitacionesArray = new JSONArray();
        for (Habitacion h : habitaciones.getElementos()) {
            habitacionesArray.put(h.toJSON());
        }
        json.put("habitaciones", habitacionesArray);
        json.put("nextIdHabitacion", Habitacion.getNextId());

        JSONArray reservasArray = new JSONArray();
        for (Reserva r : reservas.getElementos()) {
            reservasArray.put(r.toJSON());
        }
        json.put("reservas", reservasArray);
        json.put("nextIdReserva", Reserva.getNextId());

        return json;
    }

    public static Hotel fromJSON(JSONObject json) {
        Hotel hotel = new Hotel(json.getString("nombre"));

        JSONArray habitacionesArray = json.getJSONArray("habitaciones");
        for (int i = 0; i < habitacionesArray.length(); i++) {
            JSONObject habJson = habitacionesArray.getJSONObject(i);
            Habitacion habitacion = Habitacion.fromJSON(habJson);
            try {
                hotel.getHabitaciones().agregar(habitacion);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Habitacion.setNextId(json.getInt("nextIdHabitacion"));

        JSONArray reservasArray = json.getJSONArray("reservas");
        for (int i = 0; i < reservasArray.length(); i++) {
            JSONObject resJson = reservasArray.getJSONObject(i);
            Reserva reserva = Reserva.fromJSON(resJson, hotel);
            try {
                hotel.getReservas().agregar(reserva);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Reserva.setNextId(json.getInt("nextIdReserva"));

        return hotel;
    }

    public void guardarEnJSON() {
        JSONObject jsonHotel = this.toJSON();
        JSONUtiles.uploadJSON(jsonHotel, "hotelDatos");
    }

}
