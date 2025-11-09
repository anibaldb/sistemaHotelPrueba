package Clases;

import Enums.EstadoHabitacion;
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
}
