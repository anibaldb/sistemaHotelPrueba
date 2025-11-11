package Clases;

import Enums.EstadoHabitacion;
import Enums.TipoHabitacion;
import org.json.JSONObject;

public class Habitacion {
    private int id;
    private TipoHabitacion tipo;
    private EstadoHabitacion estado;
    private double precioXNoche;
    private String motivoFueraServicio;

    public static int nextId=1;

    public Habitacion(TipoHabitacion tipo, double precioXNoche) {
        this.id = nextId++;
        this.tipo = tipo;
        this.estado = EstadoHabitacion.DISPONIBLE;
        this.precioXNoche = precioXNoche;
    }

    public Habitacion() {}


    public void setearMotivoFueraServicio(String motivo){
        this.motivoFueraServicio=motivo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public static void setNextId(int next) {
        nextId = next;
    }

    public static int getNextId() {
        return nextId;
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("tipo", tipo.toString());
        json.put("estado", estado.toString());
        json.put("precioXNoche", precioXNoche);
        json.put("motivoFueraServicio", motivoFueraServicio != null ? motivoFueraServicio : JSONObject.NULL);
        return json;
    }

    public static Habitacion fromJSON(JSONObject json) {
        Habitacion h = new Habitacion();
        h.id = json.getInt("id");
        h.tipo = TipoHabitacion.valueOf(json.getString("tipo"));
        h.estado = EstadoHabitacion.valueOf(json.getString("estado"));
        h.precioXNoche = json.getDouble("precioXNoche");
        if (!json.isNull("motivoFueraServicio"))
            h.motivoFueraServicio = json.getString("motivoFueraServicio");
        return h;
    }





}
