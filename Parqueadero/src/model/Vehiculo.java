package model;

import java.util.List;

public class Vehiculo {
    private String placa;
    private String nombreConductor;
    private int identificacionConductor;
    private String horaIngreso;
    private String horaSalida;

    private List<Tarifa> listTarifas;
    private EspacioParqueadero theEspacioParqueadero;
    private TipoVehiculo tipoVehiculo;
    private Usuario theUsuario;
    private EstadoVehiculo estadoVehiculo;


    /**
     * Constructor de la clase Vehiculo
     * @param placa
     * @param nombreConductor
     * @param identificacionConductor
     * @param horaIngreso
     * @param horaSalida
     * @param listTarifas
     * @param theEspacioParqueadero
     * @param tipoVehiculo
     * @param theUsuario
     * @param estadoVehiculo
     */
    public Vehiculo(String placa, String nombreConductor, int identificacionConductor, String horaIngreso, String horaSalida, List<Tarifa> listTarifas, EspacioParqueadero theEspacioParqueadero, TipoVehiculo tipoVehiculo, Usuario theUsuario, EstadoVehiculo estadoVehiculo) {
        this.placa = placa;
        this.nombreConductor = nombreConductor;
        this.identificacionConductor = identificacionConductor;
        this.horaIngreso = horaIngreso;
        this.horaSalida = horaSalida;
        this.listTarifas = listTarifas;
        this.theEspacioParqueadero = theEspacioParqueadero;
        this.tipoVehiculo = tipoVehiculo;
        this.theUsuario = theUsuario;
        this.estadoVehiculo = estadoVehiculo;
    }



    /**
     * Getter y Setter de la clase Vehiculo
     */
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getNombreConductor() {
        return nombreConductor;
    }

    public void setNombreConductor(String nombreConductor) {
        this.nombreConductor = nombreConductor;
    }

    public int getIdentificacionConductor() {
        return identificacionConductor;
    }

    public void setIdentificacionConductor(int identificacionConductor) {
        this.identificacionConductor = identificacionConductor;
    }

    public String getHoraIngreso() {
        return horaIngreso;
    }

    public void setHoraIngreso(String horaIngreso) {
        this.horaIngreso = horaIngreso;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public List<Tarifa> getListTarifas() {
        return listTarifas;
    }

    public void setListTarifas(List<Tarifa> listTarifas) {
        this.listTarifas = listTarifas;
    }

    public EspacioParqueadero getTheEspacioParqueadero() {
        return theEspacioParqueadero;
    }

    public void setTheEspacioParqueadero(EspacioParqueadero theEspacioParqueadero) {
        this.theEspacioParqueadero = theEspacioParqueadero;
    }

    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public Usuario getTheUsuario() {
        return theUsuario;
    }

    public void setTheUsuario(Usuario theUsuario) {
        this.theUsuario = theUsuario;
    }

    public EstadoVehiculo getEstadoVehiculo() {
        return estadoVehiculo;
    }

    public void setEstadoVehiculo(EstadoVehiculo estadoVehiculo) {
        this.estadoVehiculo = estadoVehiculo;
    }
}
