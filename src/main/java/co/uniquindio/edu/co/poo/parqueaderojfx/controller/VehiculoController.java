package co.uniquindio.edu.co.poo.parqueaderojfx.controller;

import co.uniquindio.edu.co.poo.parqueaderojfx.model.*;

import java.util.Collection;

public class VehiculoController {

    private Universidad universidad;

    public VehiculoController(Universidad universidad) {
        this.universidad = universidad;
    }

    // REGISTRAR ENTRADA
    public String registrarEntradaVehiculo(
            String placa,
            String nombreConductor,
            int identificacionConductor,
            String horaIngreso,
            TipoVehiculo tipoVehiculo,
            Usuario usuario
    ) {

        return universidad.registrarEntradaVehiculo(
                placa,
                nombreConductor,
                identificacionConductor,
                horaIngreso,
                tipoVehiculo,
                usuario
        );
    }

    // REGISTRAR SALIDA
    public String registrarSalidaVehiculo(
            String placa,
            String horaSalida
    ) {

        return universidad.registrarSalidaVehiculo(
                placa,
                horaSalida
        );
    }

    // GENERAR FACTURA
    public String generarFactura(
            String placa,
            String horaSalida
    ) {

        return universidad.generarFactura(
                placa,
                horaSalida
        );
    }

    // OBTENER VEHICULO
    public Vehiculo obtenerVehiculo(String placa) {

        return universidad.obtenerVehiculo(placa);
    }

    // LISTAR VEHICULOS
    public Collection<Vehiculo> obtenerListaVehiculos() {

        return universidad.getListVehiculos();
    }

    // OBTENER USUARIO
    public Usuario obtenerUsuario(String identificacion) {

        return universidad.obtenerUsuario(
                identificacion
        );
    }

    // REGISTRAR USUARIO SI NO EXISTE
    public String registrarUsuario(
            String nombre,
            String identificacion,
            TipoUsuario tipoUsuario
    ) {

        return universidad.registrarUsuario(
                nombre,
                identificacion,
                tipoUsuario
        );
    }
}
