package Clases;

import Enums.EstadoHabitacion;
import Enums.EstadoReserva;
import Enums.TipoHabitacion;
import Exceptions.ExceptionCredencialesInvalidas;
import Exceptions.ExceptionHabitacionDuplicada;
import Exceptions.ExceptionUsuarioDuplicado;

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
    public String agregarHabitacion(String id,TipoHabitacion tipo, double precioXNoche ) throws ExceptionHabitacionDuplicada {

        for(Habitacion h: habitaciones.getElementos()){
            if(h.getId().equalsIgnoreCase(id)){
                throw new ExceptionHabitacionDuplicada("La habitacion ya existe...") ;
            }
        }
        Habitacion habitacion=new Habitacion(id,tipo,precioXNoche);
        try {
            habitaciones.agregar(habitacion);
        }catch (Exception e){
            e.getMessage();
        }



        return "Habitacion agregada correctamente";
    }

    //METODO QUE DEVUELVE LAS HABITACIONES DISPONIBLES

    public List<Habitacion> obtenerHabitacionesDisponibles(LocalDate entrada, LocalDate salida) {
        List<Habitacion> disponibles = new ArrayList<>();
        for (Habitacion h : habitaciones.getElementos()) {
            boolean libre = true;
            for (Reserva r : reservas.getElementos()) {
                if (r.getEstadoReserva() != EstadoReserva.PENDIENTE) continue;
                if (r.getHabitacion().equals(h)) {
                    boolean seCruzan = !(salida.isBefore(r.getFechaInicio()) ||
                            entrada.isAfter(r.getFechaEgreso().minusDays(1)));
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


    //METODO QUE BUSCA HABITACION POR ID, DEVUELVE LA HABITACION SINO NULL
    public Habitacion buscarHabitacionPorId(String id){
        for (Habitacion h:habitaciones.getElementos()) {
            if(h.getId().equals(id)){
                return h;
            }
        }
        return null;
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
                    return false; // Ya estaba cancelada
                } else {
                    r.setEstadoReserva(EstadoReserva.CANCELADA);
                    return true;
                }
            }
        }
        return false; // No encontrada
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

    public void setHabitaciones(ClaseGenerica<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    public ClaseGenerica<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(ClaseGenerica<Reserva> reservas) {
        this.reservas = reservas;
    }

    public void setSistemaUsuarios(SistemaUsuarios sistemaUsuarios) {
        this.sistemaUsuarios = sistemaUsuarios;
    }

    public List<Habitacion> obtenerHabitaciones() {
        return new ArrayList<>(habitaciones.getElementos());
    }

    public List<Reserva> listarReservas() {
        return new ArrayList<>(reservas.getElementos());
    }
}
