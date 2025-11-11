package Clases;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Usuario {
    private String nombre;
    private int dni;
    private String origen;
    private String direccionOrigen;
    private String eMail;
    private String contrasenia;
    private String tipo;

    public Usuario(String nombre, int dni, String origen, String direccionOrigen, String eMail, String contrasenia,String tipo) {
        this.nombre = nombre;
        this.dni = dni;
        this.origen = origen;
        this.direccionOrigen = direccionOrigen;
        this.eMail = eMail;
        this.contrasenia = contrasenia;
        this.tipo = tipo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDireccionOrigen() {
        return direccionOrigen;
    }
    

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }




    @Override
    public String toString() {
        return "Nombre: " + nombre +"| DNI: " + dni + "| Origen: " + origen +"| Direccion: " + direccionOrigen + "| eMail: : " + eMail +"| Tipo: "+tipo;
    }
    public abstract void  mostrarMenu(Hotel hotel);





    public boolean validarContrasenia(String contrasenia) {
        if (this.contrasenia.equals(contrasenia)) {
            return true;
        }
        return false;
    }


    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("nombre", nombre);
        json.put("dni", dni);
        json.put("origen", origen);
        json.put("direccionOrigen", direccionOrigen);
        json.put("eMail", eMail);
        json.put("contrasenia", contrasenia);
        json.put("tipo", tipo);


        if (this instanceof Cliente) {
            Cliente c = (Cliente) this;
            JSONArray reservasArray = new JSONArray();
            for (Reserva r: c.getReservasTomadas()) {
                reservasArray.put(r.toJSON());
            }
            json.put("reservasTomadas", reservasArray);
        }

        return json;
    }


    public static Usuario fromJSON(JSONObject json) {
        String tipo = json.getString("tipo");
        switch (tipo) {
            case "Cliente":
                return Cliente.fromJSON(json);
            case "Recepcionista":
                return Recepcionista.fromJSON(json);
            case "Administrador":
                return Administrador.fromJSON(json);
            default:
                throw new IllegalArgumentException("Tipo de usuario desconocido: " + tipo);

        }
    }










}





