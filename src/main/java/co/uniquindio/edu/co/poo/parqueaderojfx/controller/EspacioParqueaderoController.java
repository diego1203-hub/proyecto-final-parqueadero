package co.uniquindio.edu.co.poo.parqueaderojfx.controller;

import co.uniquindio.edu.co.poo.parqueaderojfx.model.EspacioParqueadero;
import co.uniquindio.edu.co.poo.parqueaderojfx.model.TipoVehiculo;
import co.uniquindio.edu.co.poo.parqueaderojfx.model.Universidad;

import java.util.Collection;

public class EspacioParqueaderoController {

    private Universidad universidad;

    public EspacioParqueaderoController(Universidad universidad) {
        this.universidad = universidad;
    }

    public String registrarNuevoEspacio(int codigo, TipoVehiculo tipoVehiculo) {
        return universidad.registrarNuevoEspacio(codigo, tipoVehiculo);
    }

    public String modificarEspacio(EspacioParqueadero espacio) {
        return universidad.modificarEspacio(espacio);
    }

    public String deshabilitarEspacio(EspacioParqueadero espacio) {
        return universidad.deshabilitarEspacio(espacio);
    }

    public EspacioParqueadero obtenerEspacio(int codigo) {
        return universidad.obtenerEspacio(codigo);
    }

    public Collection<EspacioParqueadero> obtenerListaEspacios() {
        return universidad.getListEspaciosParqueaderos();
    }

    public String consultarEstadoEspacios() {
        return universidad.consultarEstadoEspacios();
    }
}