package co.uniquindio.edu.co.poo.parqueaderojfx.model;

public class Tarifa {
    private double valorPorHora;
    private double descuento;
    private TipoVehiculo tipoVehiculo;

    /**
     * Método constructor de la clase tarifa
     * @param tipoVehiculo para calcular la tarifa
     */
    public Tarifa(TipoVehiculo tipoVehiculo) {
        this.valorPorHora = asignarTarifa();
        this.tipoVehiculo = tipoVehiculo;
    }

    /**
     * Método para asignar el valor a cada tarifa por tipo de vehículo
     * @return el valor por hora correspondiente
     */
    public double asignarTarifa() {
        switch (this.tipoVehiculo) {
            case CARRO:
                return 3000;
            case BICICLETA:
                return 1500;
            case MOTOCICLETA:
                return 2000;
            default:
                throw new IllegalArgumentException("Tipo de vehículo no válido");
        }
    }

    /**
     * Método para calcular el descuento de la tarifa
     * @param tipoUsuario para calcular el descuento de la tarifa
     * @return descuento aplicable
     */
    public double calcularDescuento(TipoUsuario tipoUsuario) {
        switch (tipoUsuario) {
            case DOCENTE:
                return 0.15;
            case ADMINISTRATIVO:
                return 0.20;
            case ESTUDIANTE:
                return 0.10;
            case VISITANTE:
                return 0.0;
            default:
                throw new IllegalArgumentException("Tipo de usuario no válido");
        }
    }

    /**
     * Método para calcular el total a pagar por el tiempo de permanencia
     * @param tiempo de permanencia de un vehículo dentro del parqueadero
     * @param tipoUsuario propietario del vehículo dentro del parqueadero
     * @return total neto a pagar con el descuento aplicado
     */
    public double calcularTotal(double tiempo, TipoUsuario tipoUsuario){
        double tarifa= this.valorPorHora;
        double valorTotal=tiempo*tarifa;
        double descuento= calcularDescuento(tipoUsuario);
        double totalConDescuento= valorTotal-(valorTotal*descuento);
        return totalConDescuento;
    }
    //Getters y Setters de la clase Tarifa
    public double getValorPorHora() {
        return valorPorHora;
    }

    public void setValorPorHora(double valorPorHora) {
        this.valorPorHora = valorPorHora;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }
}
