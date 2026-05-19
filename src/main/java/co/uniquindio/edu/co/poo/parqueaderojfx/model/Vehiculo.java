package co.uniquindio.edu.co.poo.parqueaderojfx.model;

public class Vehiculo {
    private String placa;
    private String nombreConductor;
    private int identificacionConductor;
    private String horaIngreso;
    private String horaSalida;
    private EspacioParqueadero theEspacioParqueadero;
    private TipoVehiculo tipoVehiculo;
    private Usuario theUsuario;
    private EstadoVehiculo estadoVehiculo;

    public Vehiculo(String placa, String nombreConductor, int identificacionConductor, String horaIngreso, String horaSalida, EspacioParqueadero theEspacioParqueadero, TipoVehiculo tipoVehiculo, Usuario theUsuario, EstadoVehiculo estadoVehiculo) {
        this.placa = placa;
        this.nombreConductor = nombreConductor;
        this.identificacionConductor = identificacionConductor;
        this.horaIngreso = horaIngreso;
        this.horaSalida = horaSalida;
        this.theEspacioParqueadero = theEspacioParqueadero;
        this.tipoVehiculo = tipoVehiculo;
        this.theUsuario = theUsuario;
        this.estadoVehiculo = estadoVehiculo;
    }

    public String getPlaca() {
        return this.placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getNombreConductor() {
        return this.nombreConductor;
    }

    public void setNombreConductor(String nombreConductor) {
        this.nombreConductor = nombreConductor;
    }

    public String getIdentificacionConductor() {
        return this.identificacionConductor;
    }

    public void setIdentificacionConductor(int identificacionConductor) {
        this.identificacionConductor = identificacionConductor;
    }

    public String getHoraIngreso() {
        return this.horaIngreso;
    }

    public void setHoraIngreso(String horaIngreso) {
        this.horaIngreso = horaIngreso;
    }

    public String getHoraSalida() {
        return this.horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public EspacioParqueadero getTheEspacioParqueadero() {
        return this.theEspacioParqueadero;
    }

    public void setTheEspacioParqueadero(EspacioParqueadero theEspacioParqueadero) {
        this.theEspacioParqueadero = theEspacioParqueadero;
    }

    public TipoVehiculo getTipoVehiculo() {
        return this.tipoVehiculo;
    }

    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public Usuario getTheUsuario() {
        return this.theUsuario;
    }

    public void setTheUsuario(Usuario theUsuario) {
        this.theUsuario = theUsuario;
    }

    public EstadoVehiculo getEstadoVehiculo() {
        return this.estadoVehiculo;
    }

    public void setEstadoVehiculo(EstadoVehiculo estadoVehiculo) {
        this.estadoVehiculo = estadoVehiculo;
    }
}
