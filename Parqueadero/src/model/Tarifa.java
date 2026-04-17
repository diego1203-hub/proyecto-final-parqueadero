package model;

import java.util.List;

public class Tarifa {
    private double valorPorHora;
    private double descuento;
    private List<Vehiculo> listVehiculos;
    private TipoVehiculo tipoVehiculo;

    public Tarifa(double valorPorHora, double descuento, List<Vehiculo> listVehiculos, TipoVehiculo tipoVehiculo) {
        this.valorPorHora = valorPorHora;
        this.descuento = descuento;
        this.listVehiculos = listVehiculos;
        this.tipoVehiculo = tipoVehiculo;
    }
    public double asignarTarifa(){
        if(this.tipoVehiculo==TipoVehiculo.CARRO){
         this.valorPorHora=3000;
        }else if(this.tipoVehiculo==TipoVehiculo.BICICLETA){
            this.valorPorHora=1500;
        }else if(this.tipoVehiculo==TipoVehiculo.MOTOCICLETA){
            this.valorPorHora=2000;
        }
        return valorPorHora;
    }
    public double calcularDescuento(Usuario usuario){
        if(usuario.getTipoUsuario()==TipoUsuario.DOCENTE){
            this.descuento=0.15;
        }else if(usuario.getTipoUsuario()==TipoUsuario.ADMINISTRATIVO){
            this.descuento=0.20;
        }else if(usuario.getTipoUsuario()==TipoUsuario.ESTUDIANTE){
            this.descuento=0.10;
        }else if(usuario.getTipoUsuario()==TipoUsuario.VISITANTE){
            this.descuento=0;
    }
    return this.descuento;
    }
    public double calcularTotal(double tiempo, Usuario usuario){
        double tarifa= asignarTarifa();
        double valorTotal=tiempo*tarifa;
        double descuento= calcularDescuento(usuario);
        double totalConDescuento= valorTotal-(valorTotal*descuento);
        return totalConDescuento;
    }

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
}
