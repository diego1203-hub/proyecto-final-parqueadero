package model;

import java.util.List;

public class Usuario {
    private String nombre;
    private String identificacion;
    private List<Vehiculo> listVehiculos;
    private TipoUsuario tipoUsuario;

    /**
     * Metodo constructor de la clase Usuario
     * @param nombre
     * @param identificacion
     * @param listVehiculos
     * @param tipoUsuario
     */

    public Usuario(String nombre, String identificacion, List<Vehiculo> listVehiculos, TipoUsuario tipoUsuario) {
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.listVehiculos = listVehiculos;
        this.tipoUsuario = tipoUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public List<Vehiculo> getListVehiculos() {
        return listVehiculos;
    }

    public void setListVehiculos(List<Vehiculo> listVehiculos) {
        this.listVehiculos = listVehiculos;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}

