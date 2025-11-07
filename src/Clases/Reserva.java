package Clases;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;

public class Reserva {

    private int id;
    private Cliente cliente;
    private Habitacion habitacion;
    private LocalDate fechaInicio;
    private LocalDate fechaEgreso;
    private long cantNoches;
    private double precioReserva;
    private boolean estadoReserva;

    public static int nextId=1;

    public Reserva(Cliente cliente, Habitacion habitacion, LocalDate fechaInicio, LocalDate fechaEgreso) {
        this.id=nextId;
        nextId++;
        this.cliente = cliente;
        this.habitacion = habitacion;
        this.fechaInicio = fechaInicio;
        this.fechaEgreso = fechaEgreso;
        this.cantNoches = ChronoUnit.DAYS.between(fechaInicio,fechaEgreso);
        this.precioReserva = cantNoches*habitacion.getPrecioXNoche();
        this.estadoReserva = false;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaEgreso() {
        return fechaEgreso;
    }

    public void setFechaEgreso(LocalDate fechaEgreso) {
        this.fechaEgreso = fechaEgreso;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public long getCantNoches() {
        return cantNoches;
    }

    public void setCantNoches(long cantNoches) {
        this.cantNoches = cantNoches;
    }

    public double getPrecioReserva() {
        return precioReserva;
    }

    public void setPrecioReserva(double precioReserva) {
        this.precioReserva = precioReserva;
    }

    public boolean isEstadoReserva() {
        return estadoReserva;
    }

    public void setEstadoReserva(boolean estadoReserva) {
        this.estadoReserva = estadoReserva;
    }


    @Override
    public String toString() {
        return "Reserva - " + " ID: " + id +" - Cliente: " + cliente.getNombre().toString() +" - Habitacion: " + habitacion.getTipo().toString() +" - Ingreso: " + fechaInicio + " - Salida:" + fechaEgreso +" - Precio Reserva: $" + precioReserva;
    }
}
