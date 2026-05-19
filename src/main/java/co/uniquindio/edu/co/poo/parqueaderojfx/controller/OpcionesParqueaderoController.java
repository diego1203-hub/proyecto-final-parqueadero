package co.uniquindio.edu.co.poo.parqueaderojfx.controller;

import co.uniquindio.edu.co.poo.parqueaderojfx.App;

public class OpcionesParqueaderoController {

    private App app;

    public OpcionesParqueaderoController(App app) {
        this.app = app;
    }

    public void abrirVehiculo() {
        app.openVehiculoView();
    }

    public void abrirUsuario() {
        app.openUsuarioView();
    }

    public void abrirTarifa() {
        app.openTarifaView();
    }

    public void abrirEspacioParqueadero() {
        app.openEspacioParqueaderoView();
    }

    public void abrirReportes() {
        app.openUniversidadView();
    }

    public void salir() {
        app.openPrimaryView();
    }
}