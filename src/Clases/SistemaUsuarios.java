package Clases;

import Exceptions.ExceptionCredencialesInvalidas;
import Exceptions.ExceptionUsuarioDuplicado;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SistemaUsuarios {
    private List<Usuario> usuarios;
    private Usuario usuarioLogueado;


    public SistemaUsuarios() {

        this.usuarios = new ArrayList<>();
        this.usuarioLogueado = null;
    }


    public Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }

    public void logout() {
        this.usuarioLogueado = null;
    }

    public Usuario login(String eMail, String contrasenia) throws ExceptionCredencialesInvalidas {
        for (Usuario u : usuarios) {
            if (u.geteMail().equalsIgnoreCase(eMail) && u.validarContrasenia(contrasenia)) {
                usuarioLogueado = u;
                return u;
            }
        }
        throw new ExceptionCredencialesInvalidas("Email o contraseña invalida");
    }

    public String  registrarAdministrador(String nombre, int dni, String origen, String direccionOrigen, String eMail, String contrasenia) throws ExceptionUsuarioDuplicado {

        if (verificarDuplicado(dni)) {
            throw new ExceptionUsuarioDuplicado("El usuario con ese DNI ya existe...");
        }
        Usuario usuario = new Administrador(nombre, dni, origen, direccionOrigen, eMail, contrasenia);
        usuarios.add(usuario);
        guardarEnJSON();

        return "Administrador registrado con éxito...";
    }

    public String  registrarCliente(String nombre, int dni, String origen, String direccionOrigen, String eMail, String contrasenia) throws ExceptionUsuarioDuplicado {

        if (verificarDuplicado(dni)) {
            throw new ExceptionUsuarioDuplicado("El usuario con ese DNI ya existe...");
        }
        Usuario usuario = new Cliente(nombre, dni, origen, direccionOrigen, eMail, contrasenia);
        usuarios.add(usuario);

        guardarEnJSON();

        return "Cliente registrado con éxito...";
    }

    public String  registrarRecepcionista(String nombre, int dni, String origen, String direccionOrigen, String eMail, String contrasenia) throws ExceptionUsuarioDuplicado {

        if (verificarDuplicado(dni)) {
            throw new ExceptionUsuarioDuplicado("El usuario con ese DNI ya existe...");
        }
        Usuario usuario = new Recepcionista(nombre, dni, origen, direccionOrigen, eMail, contrasenia);
        usuarios.add(usuario);
        guardarEnJSON();

        return "Recepcionista registrado con éxito...";
    }

    public boolean verificarDuplicado(int dni) {
        for (Usuario u : usuarios) {
            if (u.getDni() == dni) {
                return true;
            }
        }
        return false;
    }

    public void listarUsuarios(){
        for (Usuario u : usuarios) {
            System.out.println(u.toString());
        }
    }

    public Cliente buscarPorDni(int dni) {
        for (Usuario u : usuarios) {
            if (u.getDni() == dni && u instanceof Cliente)
                return (Cliente) u;
        }
        return null;
    }

    public void agregarUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        JSONArray usuariosArray = new JSONArray();

        for (Usuario u : usuarios) {
            usuariosArray.put(u.toJSON());
        }

        json.put("usuarios", usuariosArray);
        return json;
    }

    public static SistemaUsuarios fromJSON(JSONObject json) {
        SistemaUsuarios sistema = new SistemaUsuarios();
        JSONArray usuariosArray = json.optJSONArray("usuarios");
        if (usuariosArray != null) {
            for (int i = 0; i < usuariosArray.length(); i++) {
                JSONObject usuarioJson = usuariosArray.getJSONObject(i);
                Usuario u = Usuario.fromJSON(usuarioJson);
                sistema.agregarUsuario(u);
            }
        }
        return sistema;
    }

    public void guardarEnJSON() {
        JSONObject jsonSistema = this.toJSON();
        JSONUtiles.uploadJSON(jsonSistema, "usuarios");
    }
}



