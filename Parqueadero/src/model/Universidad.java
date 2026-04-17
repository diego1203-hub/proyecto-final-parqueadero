package model;

import java.util.List;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Universidad {
    private String nombre;
    private int nit;
    private String direccion;

    private List<Usuario> listUsuarios;
    private List<Vehiculo> listVehiculos;
    private List<EspacioParqueadero> listEspaciosParqueaderos;
    private List<Tarifa> listTarifas;

    /**
     * Constructor de la clase Universidad
     * @param nombre
     * @param nit
     * @param direccion
     * @param listUsuarios
     * @param listVehiculos
     * @param listEspaciosParqueaderos
     * @param listTarifas
     */
    public Universidad(String nombre, int nit, String direccion, List<Usuario> listUsuarios, List<Vehiculo> listVehiculos, List<EspacioParqueadero> listEspaciosParqueaderos, List<Tarifa> listTarifas) {
        this.nombre = nombre;
        this.nit = nit;
        this.direccion = direccion;
        this.listUsuarios = listUsuarios;
        this.listVehiculos = listVehiculos;
        this.listEspaciosParqueaderos = listEspaciosParqueaderos;
        this.listTarifas = listTarifas;
    }

    /**
     * Método para registrar la entrada de un Vehículo
     * @param placa
     * @param nombreConductor
     * @param identificacionConductor
     * @param horaIngreso
     * @param tipoVehiculo
     * @param theUsuario
     * @return
     */
    public String registrarEntradaVehiculo(String placa, String nombreConductor, int identificacionConductor, String horaIngreso, TipoVehiculo tipoVehiculo, Usuario theUsuario){
        String respuesta="";
        for(Vehiculo v:listVehiculos){
            if(v.getPlaca().equals(placa) && v.getEstadoVehiculo()==EstadoVehiculo.DENTRO){
                respuesta= "El vehículo ya se encuentra adentro de la universidad";
            }
        }
        EspacioParqueadero espacioDisponible = null;

        for (EspacioParqueadero e : listEspaciosParqueaderos) {
            if (e.getEstadoEspacio() == EstadoEspacio.DISPONIBLE &&
                    e.getTipoVehiculo() == tipoVehiculo) {

                espacioDisponible = e;
                break;
            }
        }

        if (espacioDisponible == null) {
            respuesta= "No hay espacios disponibles";
        }
        Vehiculo vehiculo = new Vehiculo(
                placa,
                nombre,
                identificacionConductor,
                horaIngreso,
                null,
                null,
                espacioDisponible,
                tipoVehiculo,
                theUsuario,
                EstadoVehiculo.DENTRO
        );
        espacioDisponible.asignarEspacio(vehiculo);
        vehiculo.setTheEspacioParqueadero(espacioDisponible);
        listVehiculos.add(vehiculo);
        respuesta="Se ha añadido el vehículo existosamente";
        return respuesta;
    }

    /**
     * Método para registrar la Salida de un Vehiculo
     * @param placa
     * @param horaSalida
     * @return
     */
    public double registrarSalidaVehiculo(String placa, String horaSalida) {

        // 1. Buscar el vehículo dentro del parqueadero
        Vehiculo vehiculoEncontrado = null;

        for (Vehiculo v : listVehiculos) {
            if (v.getPlaca().equalsIgnoreCase(placa) &&
                    v.getEstadoVehiculo() == EstadoVehiculo.DENTRO) {
                vehiculoEncontrado = v;
                break;
            }
        }

        // 2. Validar si no existe
        if (vehiculoEncontrado == null) {
            System.out.println("Error: el vehículo no está registrado dentro del parqueadero");
            return -1;
        }

        // 3. Registrar hora de salida
        vehiculoEncontrado.setHoraSalida(horaSalida);

        // 4. Calcular tiempo de permanencia
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        LocalTime ingreso = LocalTime.parse(vehiculoEncontrado.getHoraIngreso(), formatter);
        LocalTime salida = LocalTime.parse(horaSalida, formatter);

        long minutos = Duration.between(ingreso, salida).toMinutes();

        if (minutos < 0) {
            System.out.println("Error: la hora de salida no puede ser menor que la hora de ingreso");
            return -1;
        }

        // Redondear a horas cobrables
        double horas = Math.ceil(minutos / 60.0);

        // 5. Crear tarifa según el tipo de vehículo
        Tarifa tarifa = new Tarifa(
                0,
                0,
                null,
                vehiculoEncontrado.getTipoVehiculo()
        );

        double totalPagar = tarifa.calcularTotal(horas, vehiculoEncontrado.getTheUsuario());

        // 6. Liberar espacio
        EspacioParqueadero espacio = vehiculoEncontrado.getTheEspacioParqueadero();
        if (espacio != null) {
            espacio.liberarEspacio();
        }

        // 7. Cambiar estado del vehículo
        vehiculoEncontrado.setEstadoVehiculo(EstadoVehiculo.FUERA);

        System.out.println("Salida registrada correctamente");
        System.out.println("Tiempo total: " + horas + " hora(s)");
        System.out.println("Valor a pagar: $" + totalPagar);

        return totalPagar;
    }






    /**
     * Getters y Setters de la clase Universidad
     */
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNit() {
        return nit;
    }

    public void setNit(int nit) {
        this.nit = nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Usuario> getListUsuarios() {
        return listUsuarios;
    }

    public void setListUsuarios(List<Usuario> listUsuarios) {
        this.listUsuarios = listUsuarios;
    }

    public List<Vehiculo> getListVehiculos() {
        return listVehiculos;
    }

    public void setListVehiculos(List<Vehiculo> listVehiculos) {
        this.listVehiculos = listVehiculos;
    }

    public List<EspacioParqueadero> getListEspaciosParqueaderos() {
        return listEspaciosParqueaderos;
    }

    public void setListEspaciosParqueaderos(List<EspacioParqueadero> listEspaciosParqueaderos) {
        this.listEspaciosParqueaderos = listEspaciosParqueaderos;
    }

    public List<Tarifa> getListTarifas() {
        return listTarifas;
    }

    public void setListTarifas(List<Tarifa> listTarifas) {
        this.listTarifas = listTarifas;
    }
}
