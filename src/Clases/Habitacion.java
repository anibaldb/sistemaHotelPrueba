package Clases;

import Enums.EstadoHabitacion;
import Enums.TipoHabitacion;

public class Habitacion {
    private String id;
    private TipoHabitacion tipo;
    private EstadoHabitacion estado;
    private double precioXNoche;
    private String motivoFueraServicio;

    public Habitacion(String id, TipoHabitacion tipo, double precioXNoche) {
        this.id = id;
        this.tipo = tipo;
        this.estado = EstadoHabitacion.DISPONIBLE;
        this.precioXNoche = precioXNoche;
    }

    public void setearMotivoFueraServicio(String motivo){
        this.motivoFueraServicio=motivo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TipoHabitacion getTipo() {
        return tipo;
    }

    public void setTipo(TipoHabitacion tipo) {
        this.tipo = tipo;
    }

    public EstadoHabitacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoHabitacion estado) {
        this.estado = estado;
    }

    public double getPrecioXNoche() {
        return precioXNoche;
    }

    public void setPrecioXNoche(double precioXNoche) {
        this.precioXNoche = precioXNoche;
    }

    public String getMotivoFueraServicio() {
        return motivoFueraServicio;
    }

    public void setMotivoFueraServicio(String motivoFueraServicio) {
        this.motivoFueraServicio = motivoFueraServicio;
    }

    @Override
    public String toString() {
        return "Habitacion - " +"Nro: " + id + " - Tipo: " + tipo +" - Precio Noche: $" + precioXNoche;
    }
}
