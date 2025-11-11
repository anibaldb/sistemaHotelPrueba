package Clases;

import Enums.EstadoReserva;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;

public class Reserva {

    private int id;
    private Cliente cliente;
    private Habitacion habitacion;
    private int dniCliente;
    private int idHabitacion;
    private LocalDate fechaInicio;
    private LocalDate fechaEgreso;
    private long cantNoches;
    private double precioReserva;
    private EstadoReserva estadoReserva;

    public static int nextId=1;

    public Reserva(Hotel hotel, int dniCliente, int idHabitacion, LocalDate fechaInicio, LocalDate fechaEgreso) {
        this.id=nextId++;
        this.dniCliente = dniCliente;
        this.idHabitacion = idHabitacion;
        this.fechaInicio = fechaInicio;
        this.fechaEgreso = fechaEgreso;
        this.cantNoches = ChronoUnit.DAYS.between(fechaInicio,fechaEgreso);
        this.estadoReserva = EstadoReserva.PENDIENTE;

        this.cliente= hotel.getSistemaUsuarios().buscarPorDni(dniCliente);
        this.habitacion= hotel.buscarHabitacionPorId(idHabitacion);
        this.precioReserva = cantNoches * habitacion.getPrecioXNoche();

    }

    public Reserva(){}



    public int getId() {
        return id;
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

    public EstadoReserva getEstadoReserva() {
        return estadoReserva;
    }

    public void setEstadoReserva(EstadoReserva estadoReserva) {
        this.estadoReserva = estadoReserva;
    }

    @Override
    public String toString() {
        return "Reserva - " + " ID: " + id +" - Cliente: " + cliente.getNombre().toString() +" - Habitacion: " + habitacion.getTipo().toString() +" - Ingreso: " + fechaInicio + " - Salida:" + fechaEgreso +" - Precio Reserva: $" + precioReserva+" - Estado: "+getEstadoReserva();
    }

    public static void setNextId(int next) {
        nextId = next;
    }

    public static int getNextId() {
        return nextId;
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("dniCliente", dniCliente);
        json.put("idHabitacion", idHabitacion);
        json.put("fechaInicio", fechaInicio.toString());
        json.put("fechaEgreso", fechaEgreso.toString());
        json.put("cantNoches", cantNoches);
        json.put("precioReserva", precioReserva);
        json.put("estadoReserva", estadoReserva.toString());
        return json;
    }

    public static Reserva fromJSON(JSONObject json, Hotel hotel) {
        Reserva r = new Reserva();
        r.id = json.getInt("id");
        r.dniCliente = json.getInt("dniCliente");
        r.idHabitacion = json.getInt("idHabitacion");
        r.fechaInicio = LocalDate.parse(json.getString("fechaInicio"));
        r.fechaEgreso = LocalDate.parse(json.getString("fechaEgreso"));
        r.cantNoches = json.getLong("cantNoches");
        r.precioReserva = json.getDouble("precioReserva");
        r.estadoReserva = EstadoReserva.valueOf(json.getString("estadoReserva"));


        r.cliente = hotel.getSistemaUsuarios().buscarPorDni(r.dniCliente);
        r.habitacion = hotel.buscarHabitacionPorId(r.idHabitacion);
        return r;
    }
}
