package co.uniquindio.edu.co.poo.parqueaderojfx.controller;

import co.uniquindio.edu.co.poo.parqueaderojfx.model.Tarifa;
import co.uniquindio.edu.co.poo.parqueaderojfx.model.TipoUsuario;
import co.uniquindio.edu.co.poo.parqueaderojfx.model.TipoVehiculo;
import co.uniquindio.edu.co.poo.parqueaderojfx.model.Universidad;

import java.util.Collection;

public class TarifaController {

    private Universidad universidad;

    public TarifaController(Universidad universidad) {
        this.universidad = universidad;
    }

    public String registrarTarifa(TipoVehiculo tipoVehiculo, TipoUsuario tipoUsuario) {
        return universidad.registrarTarifa(tipoVehiculo, tipoUsuario);
    }

    public String eliminarTarifa(TipoVehiculo tipoVehiculo) {
        return universidad.eliminarTarifa(tipoVehiculo);
    }

    public String modificarTarifa(double valorHora, double descuento, TipoVehiculo tipoVehiculo) {
        return universidad.modificarTarifa(valorHora, descuento, tipoVehiculo);
    }

    public Tarifa obtenerTarifaPorTipoVehiculo(TipoVehiculo tipoVehiculo) {
        return universidad.obtenerTarifaPorTipoVehiculo(tipoVehiculo);
    }

    public Collection<Tarifa> obtenerListaTarifas() {
        return universidad.getListTarifas();
    }
}
