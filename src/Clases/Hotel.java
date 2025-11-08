package Clases;

import Enums.EstadoHabitacion;
import Enums.TipoHabitacion;
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

    public List<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(List<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public void setSistemaUsuarios(SistemaUsuarios sistemaUsuarios) {
        this.sistemaUsuarios = sistemaUsuarios;
    }
}
