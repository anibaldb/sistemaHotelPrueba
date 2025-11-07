package Clases;

import Exceptions.ExceptionCredencialesInvalidas;
import Exceptions.ExceptionUsuarioDuplicado;

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

        return "Admin registrado con exito...";
    }


    public String  registrarCliente(String nombre, int dni, String origen, String direccionOrigen, String eMail, String contrasenia) throws ExceptionUsuarioDuplicado {


        if (verificarDuplicado(dni)) {
            throw new ExceptionUsuarioDuplicado("El usuario con ese DNI ya existe...");
        }
        Usuario usuario = new Cliente(nombre, dni, origen, direccionOrigen, eMail, contrasenia);
        usuarios.add(usuario);

        return "Cliente registrado con exito...";
    }

    public String  registrarRecepcionista(String nombre, int dni, String origen, String direccionOrigen, String eMail, String contrasenia) throws ExceptionUsuarioDuplicado {


        if (verificarDuplicado(dni)) {
            throw new ExceptionUsuarioDuplicado("El usuario con ese DNI ya existe...");
        }
        Usuario usuario = new Recepcionista(nombre, dni, origen, direccionOrigen, eMail, contrasenia);
        usuarios.add(usuario);

        return "Recepcionista registrado con exito...";
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

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public Cliente buscarPorDni(int dni) {
        for (Usuario u : usuarios) {
            if (u.getDni() == dni && u instanceof Cliente)
                return (Cliente) u;
        }
        return null;
    }


}



