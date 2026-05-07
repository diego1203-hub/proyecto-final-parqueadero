package model;

import java.util.ArrayList;
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
        this.listVehiculos = new ArrayList<>();
        this.listEspaciosParqueaderos = new ArrayList<>();
        this.listUsuarios = new ArrayList<>();
        this.listTarifas = new ArrayList<>();
    }

    // ------------------ CRUD VEHICULO ------------------

    /**
     * Método para buscar un vehiculo por placa
     * @param placa
     * @return
     */
    public Vehiculo obtenerVehiculo(String placa){
        Vehiculo encontrado = null;
        for(Vehiculo v : listVehiculos){
            if(v.getPlaca().equalsIgnoreCase(placa)){
                encontrado = v;
                break;
            }
        }
        return encontrado;
    }

    /**
     * Método para registrar la entrada de un vehículo y registrar el vehículo
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
            if(v.getPlaca().equalsIgnoreCase(placa) && v.getEstadoVehiculo()==EstadoVehiculo.DENTRO){
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
     * Método para calcular el tiempo de permanencia de un vehiculo
     * @param horaIngreso
     * @param horaSalida
     * @return
     */
    public double calcularTiempoPermanencia(String horaIngreso, String horaSalida) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        LocalTime ingreso = LocalTime.parse(horaIngreso, formatter);
        LocalTime salida = LocalTime.parse(horaSalida, formatter);

        long minutos = Duration.between(ingreso, salida).toMinutes();

        if (minutos < 0) {
            throw new IllegalArgumentException("La hora de salida no puede ser menor que la hora de ingreso");
        }

        return Math.ceil(minutos / 60.0);
    }

    /**
     * Método para registrar la Salida de un Vehiculo
     * @param placa
     * @param horaSalida
     * @return
     */
    public String registrarSalidaVehiculo(String placa, String horaSalida) {
        String respuesta="";
        Vehiculo vehiculoEncontrado = obtenerVehiculo(placa);

        if (vehiculoEncontrado == null) {
            respuesta= ("El vehículo no se encuentra registrado en el parqueadero");
        }
            if (vehiculoEncontrado!=null &&
                    vehiculoEncontrado.getEstadoVehiculo() == EstadoVehiculo.DENTRO) {
                vehiculoEncontrado.setEstadoVehiculo(EstadoVehiculo.FUERA);
                respuesta= "La salida del vehiculo "+ vehiculoEncontrado.getPlaca()+" se ha completado correctamente";
                vehiculoEncontrado.setHoraSalida(horaSalida);
                EspacioParqueadero espacio = vehiculoEncontrado.getTheEspacioParqueadero();
                if (espacio != null) {
                    espacio.liberarEspacio();
                }
        }
            return respuesta;
    }


    // ------------------ CRUD ESPACIO PARQUEADERO ------------------

    /**
     * Método para buscar un espacio en el parqueadero por código
     * @param codigo
     * @return
     */
    public EspacioParqueadero obtenerEspacio(int codigo){
        EspacioParqueadero encontrado = null;
        for(EspacioParqueadero ep : listEspaciosParqueaderos){
            if(ep.getCodigo()==(codigo)){
                encontrado = ep;
                break;
            }
        }
        return encontrado;
    }
    /**
     * Método para registrar nuevo espacio en el parqueadero
     * @param codigo
     * @param tipoVehiculo
     * @param estadoEspacio
     * @return
     */
    public String registrarNuevoEspacio(int codigo, TipoVehiculo tipoVehiculo,EstadoEspacio estadoEspacio) {
        String respuesta="";
        EspacioParqueadero espacio= obtenerEspacio(codigo);
        if (espacio == null) {
            respuesta= "El espacio no existe en el registro del parqueadero";
        } else {
            EspacioParqueadero espacioNuevo= new EspacioParqueadero(codigo, null);
            listEspaciosParqueaderos.add(espacio);
            respuesta= "Espacio registrado correctamente";
        }
        return respuesta;
    }

    /**
     * Método para modificar espacio
     * @param espacio
     * @return
     */
    public boolean modificarEspacio(EspacioParqueadero espacio) {
        if (espacio == null) {
            return false;
        }

        for (EspacioParqueadero e : listEspaciosParqueaderos) {
            if (e.getCodigo() == espacio.getCodigo()) {
                e.setTipoVehiculo(espacio.getTipoVehiculo());
                e.setEstadoEspacio(espacio.getEstadoEspacio());
                System.out.println("Espacio modificado correctamente");
                return true;
            }
        }

        System.out.println("No se encontró el espacio");
        return false;
    }

    /**
     * Método para deshabilitar espacio
     * @param espacio
     * @return
     */
    public boolean deshabilitarEspacio(EspacioParqueadero espacio) {
        if (espacio == null) {
            return false;
        }

        for (EspacioParqueadero e : listEspaciosParqueaderos) {
            if (e.getCodigo() == espacio.getCodigo()) {
                if (e.getEstadoEspacio() == EstadoEspacio.DISPONIBLE) {
                    e.setEstadoEspacio(EstadoEspacio.MANTENIMIENTO);
                    System.out.println("Espacio deshabilitado correctamente");
                    return true;
                } else {
                    System.out.println("No se puede deshabilitar un espacio ocupado");
                    return false;
                }
            }
        }

        System.out.println("Espacio no encontrado");
        return false;
    }

    /**
     * Método para verificar espacios disponibles
     * @return
     */
    public boolean existeEspacioDisponible() {
        for (EspacioParqueadero e : listEspaciosParqueaderos) {
            if (e.getEstadoEspacio() == EstadoEspacio.DISPONIBLE) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método para consultar la disponibilidad de espacios en la universidad
     * @return
     */
    public String consultarEstadoEspacios() {
        String espacios="";
        int disponibles = 0;
        int ocupados = 0;
        int mantenimiento = 0;

        for (EspacioParqueadero e : listEspaciosParqueaderos) {
            if (e.getEstadoEspacio() == EstadoEspacio.DISPONIBLE) {
                disponibles++;
            } else if (e.getEstadoEspacio() == EstadoEspacio.OCUPADO) {
                ocupados++;
            } else if (e.getEstadoEspacio() == EstadoEspacio.MANTENIMIENTO) {
                mantenimiento++;
            }
        }
        espacios="Total: " + listEspaciosParqueaderos.size() +
                "\nDisponibles: " + disponibles +
                "\nOcupados: " + ocupados +
                "\nEn mantenimiento: " + mantenimiento;

        return espacios;
    }

    /**
     * Método para consultar los vehiculos que ya están estacionados
     * @return
     */
    public String consultarVehiculosEstacionados() {
        String reporte = "";

        for (Vehiculo v : listVehiculos) {
            if (v.getEstadoVehiculo() == EstadoVehiculo.DENTRO) {
                reporte += "Placa: " + v.getPlaca() +
                        " | Tipo: " + v.getTipoVehiculo() +
                        " | Hora ingreso: " + v.getHoraIngreso();

                if (v.getTheEspacioParqueadero() != null) {
                    reporte += " | Espacio: " + v.getTheEspacioParqueadero().getCodigo();
                }

                reporte += "\n";
            }
        }

        if (reporte.isEmpty()) {
            return "No hay vehículos estacionados";
        }

        return reporte;
    }

    /**
     * Método para generar el reporte de las actividades del parqueadero
     * @return
     */
    public String generarReportes() {
        int dentro = 0;
        int fuera = 0;

        for (Vehiculo v : listVehiculos) {
            if (v.getEstadoVehiculo() == EstadoVehiculo.DENTRO) {
                dentro++;
            } else if (v.getEstadoVehiculo() == EstadoVehiculo.FUERA) {
                fuera++;
            }
        }

        int disponibles = 0;
        for (EspacioParqueadero e : listEspaciosParqueaderos) {
            if (e.getEstadoEspacio() == EstadoEspacio.DISPONIBLE) {
                disponibles++;
            }
        }

        return "===== REPORTE GENERAL =====" +
                "\nTotal vehículos registrados: " + listVehiculos.size() +
                "\nVehículos dentro: " + dentro +
                "\nVehículos fuera: " + fuera +
                "\nEspacios totales: " + listEspaciosParqueaderos.size() +
                "\nEspacios disponibles: " + disponibles;
    }

    // ------------------CRUD TARIFA ------------------

    public String generarFactura(String placa, String horaSalida) {
        String respuesta="";
        Vehiculo vehiculo = obtenerVehiculo(placa);

        if (vehiculo == null) {
            respuesta= "El vehículo no se encuentra registrado en el parqueadero";
        }

        double horas = calcularTiempoPermanencia(vehiculo.getHoraIngreso(), horaSalida);

        Tarifa tarifa = new Tarifa(0, 0, vehiculo.getTipoVehiculo());
        double totalPagar = tarifa.calcularTotal(horas, vehiculo.getTheUsuario());
        respuesta=  "Factura de salida del vehículo\n"+ "Placa: " + vehiculo.getPlaca() + "\n"+ "Tipo de vehículo: " + vehiculo.getTipoVehiculo() + "\n"+
        "Tiempo de permanencia: " + horas + " hora(s)\n"+
       "Valor por hora: $" + tarifa.asignarTarifa() + "\n"+
                "Descuento aplicado: " + tarifa.calcularDescuento(vehiculo.getTheUsuario()) * 100 + "%\n"+
       "Total a pagar: $" + totalPagar + "\n";

        return respuesta;
    }


    // ------------------ ROLES DE USUARIO ------------------
    /**
     * Método para controlar roles de usuario
     * @param usuario
     * @return
     */
    public String controlarRol(Usuario usuario) {
        if (usuario == null) {
            return "Usuario no válido";
        }

        if (usuario.getTipoUsuario() == TipoUsuario.ADMINISTRATIVO) {
            return "Administrador";
        } else {
            return "Operador";
        }
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
