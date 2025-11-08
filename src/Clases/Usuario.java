package Clases;

public abstract class Usuario {
    private String nombre;
    private int dni;
    private String origen;
    private String direccionOrigen;
    private String eMail;
    private String contrasenia;

    public Usuario(String nombre, int dni, String origen, String direccionOrigen, String eMail, String contrasenia) {
        this.nombre = nombre;
        this.dni = dni;
        this.origen = origen;
        this.direccionOrigen = direccionOrigen;
        this.eMail = eMail;
        this.contrasenia = contrasenia;
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

    public void setDireccionOrigen(String direccionOrigen) {
        this.direccionOrigen = direccionOrigen;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }


    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    @Override
    public String toString() {
        return "nombre='" + nombre + '\'' +
                "dni=" + dni +
                "origen='" + origen + '\'' +
                "direccionOrigen='" + direccionOrigen + '\'' +
                "eMail='" + eMail + '\''+
                "Tipo: "+getTipoUsuario().toString();
    }
    public abstract void  mostrarMenu(Hotel hotel);

    public abstract String getTipoUsuario();



    public boolean validarContrasenia(String contrasenia) {
        if (this.contrasenia.equals(contrasenia)) {
            return true;
        }
        return false;
    }




}
