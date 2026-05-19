package co.uniquindio.edu.co.poo.parqueaderojfx.controller;

import co.uniquindio.edu.co.poo.parqueaderojfx.model.Universidad;
import co.uniquindio.edu.co.poo.parqueaderojfx.model.Usuario;

import java.util.Collection;

public class UsuarioController {

    Universidad universidad;

    public UsuarioController(Universidad universidad) {
        this.universidad = universidad;
    }

    public String registrarUsuario(Usuario usuario) {
        return universidad.registrarUsuario(
                usuario.getNombre(),
                usuario.getIdentificacion(),
                usuario.getTipoUsuario()
        );
    }

    public Collection<Usuario> obtenerListaUsuarios() {
        return universidad.getListUsuarios();
    }

    public String eliminarUsuario(String identificacion) {
        return universidad.eliminarUsuario(identificacion);
    }

    public String actualizarUsuario(Usuario usuario) {
        return universidad.modificarUsuario(
                usuario.getNombre(),
                usuario.getIdentificacion(),
                usuario.getTipoUsuario()
        );
    }
}