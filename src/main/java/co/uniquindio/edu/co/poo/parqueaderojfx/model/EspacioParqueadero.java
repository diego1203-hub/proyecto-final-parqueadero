package co.uniquindio.edu.co.poo.parqueaderojfx.model;

public class EspacioParqueadero {
    private int codigo;
    private EstadoEspacio estadoEspacio;
    private TipoVehiculo tipoVehiculo;
    private Vehiculo theVehiculo;

    /**
     * Metodo constructor para la clase EspacioParqueadero
     * @param codigo del espacio en el parqueadero
     * @param tipoVehiculo asignado al espacio en el parqueadero
     */
    public EspacioParqueadero(int codigo, TipoVehiculo tipoVehiculo) {
        this.codigo = codigo;
        this.tipoVehiculo = tipoVehiculo;;
        this.estadoEspacio=EstadoEspacio.DISPONIBLE;
    }

    /**
     * Método para asignar un vehiculo a un espacio creado
     *
     * @param vehiculo
     * @return
     */
    public void asignarEspacio (Vehiculo vehiculo) {
        if (this.estadoEspacio == EstadoEspacio.DISPONIBLE) {
            this.theVehiculo = vehiculo;
            this.estadoEspacio = EstadoEspacio.OCUPADO;
            System.out.println("Vehículo asignado");
        } else {
            System.out.println("El espacio no está disponible");
        }
    }
    /**
     * Metodo para liberar espacio parqueadero
     */
    public void liberarEspacio() {
        if (this.estadoEspacio == EstadoEspacio.OCUPADO) {
            this.theVehiculo = null;
            this.estadoEspacio = EstadoEspacio.DISPONIBLE;
            System.out.println("Espacio liberado");
        } else {
            System.out.println("No hay vehículo para retirar");
        }
    }
    /**
     * Metodo para cambiar estado a mantenimiento
     */
    public void cambiarEstadoMantenimiento(){
        if(this.estadoEspacio == EstadoEspacio.DISPONIBLE){
            this.estadoEspacio = EstadoEspacio.MANTENIMIENTO;
            System.out.println("Espacio en mantenimiento");
        } else {
            System.out.println("No se puede poner en mantenimiento");
        }

    }
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public EstadoEspacio getEstadoEspacio() {
        return estadoEspacio;
    }

    public void setEstadoEspacio(EstadoEspacio estadoEspacio) {
        this.estadoEspacio = estadoEspacio;
    }

    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

}