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
    public double registrarSalidaVehiculo(String placa, String horaSalida) {

        Vehiculo vehiculoEncontrado = null;

        for (Vehiculo v : listVehiculos) {
            if (v.getPlaca().equalsIgnoreCase(placa) &&
                    v.getEstadoVehiculo() == EstadoVehiculo.DENTRO) {
                vehiculoEncontrado = v;
                break;
            }
        }

        if (vehiculoEncontrado == null) {
            System.out.println("Error: el vehículo no está registrado dentro del parqueadero");
            return -1;
        }

        vehiculoEncontrado.setHoraSalida(horaSalida);

        double horas = calcularTiempoPermanencia(
                vehiculoEncontrado.getHoraIngreso(),
                vehiculoEncontrado.getHoraSalida()
        );

        Tarifa tarifa = new Tarifa(
                0,
                0,
                null,
                vehiculoEncontrado.getTipoVehiculo()
        );

        double totalPagar = tarifa.calcularTotal(horas, vehiculoEncontrado.getTheUsuario());

        EspacioParqueadero espacio = vehiculoEncontrado.getTheEspacioParqueadero();
        if (espacio != null) {
            espacio.liberarEspacio();
        }

        vehiculoEncontrado.setEstadoVehiculo(EstadoVehiculo.FUERA);

        System.out.println("Salida registrada correctamente");
        System.out.println("Tiempo total: " + horas + " hora(s)");
        System.out.println("Valor a pagar: $" + totalPagar);

        return totalPagar;
    }

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
     * Método para registrar nuevo espacio
     * @param espacio
     * @return
     */
    public boolean registrarNuevoEspacio(EspacioParqueadero espacio) {
        if (espacio == null) {
            return false;
        }

        for (EspacioParqueadero e : listEspaciosParqueaderos) {
            if (e.getCodigo() == espacio.getCodigo()) {
                System.out.println("Ya existe un espacio con ese código");
                return false;
            }
        }

        listEspaciosParqueaderos.add(espacio);
        System.out.println("Espacio registrado correctamente");
        return true;
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
    public boolean verificarEspacioDisponible() {
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
    public String consultarDisponibilidadEspacios() {
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

        return "Total: " + listEspaciosParqueaderos.size() +
                "\nDisponibles: " + disponibles +
                "\nOcupados: " + ocupados +
                "\nEn mantenimiento: " + mantenimiento;
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

        if (reporte.equals("")) {
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
