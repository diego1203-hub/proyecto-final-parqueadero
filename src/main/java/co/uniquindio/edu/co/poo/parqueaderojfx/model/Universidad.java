package co.uniquindio.edu.co.poo.parqueaderojfx.model;

import java.time.LocalDate;
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
     *
     * @param nombre
     * @param nit
     * @param direccion
     */
    public Universidad(String nombre, int nit, String direccion) {
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
     * Método para registrar un vehículo
     *
     * @param vehiculo a registrar
     * @return
     */
    public boolean registrarVehiculo(Vehiculo vehiculo){
        listVehiculos.add(vehiculo);
        return true;
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
        EspacioParqueadero espacioDisponible = buscarEspacioDisponible(tipoVehiculo);

        for(Vehiculo v:listVehiculos){
            if(v.getPlaca().equalsIgnoreCase(placa) && v.getEstadoVehiculo()==EstadoVehiculo.DENTRO){
                throw new IllegalArgumentException("La placa " + placa + " ya está registrada dentro del parqueadero.");
            }
        }
        if (espacioDisponible == null) {
            throw new IllegalStateException("No hay espacios disponibles para el tipo de vehículo: " + tipoVehiculo);
        } else {
            Vehiculo vehiculo = new Vehiculo(
                    placa,
                    nombreConductor,
                    identificacionConductor,
                    horaIngreso,
                    null,
                    espacioDisponible,
                    tipoVehiculo,
                    theUsuario,
                    EstadoVehiculo.DENTRO
            );
            espacioDisponible.asignarEspacio(vehiculo);
            vehiculo.setTheEspacioParqueadero(espacioDisponible);
            registrarVehiculo(vehiculo);

            respuesta = "Se ha añadido el vehículo exitosamente";
        }

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
            return  ("El vehículo no se encuentra registrado en el parqueadero");
        }
        if (vehiculoEncontrado.getEstadoVehiculo() == EstadoVehiculo.FUERA) {
            throw new IllegalStateException("El vehículo con placa " + placa + " ya ha salido.");
        }else{
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
     * Método para buscar un espacio disponible para cierto tipo de vehículo
     * @param tipoVehiculo para validar qué tipo de espacio disponible
     * @return el espacio parqueadero disponible para un tipo de vehículo específico
     */
    public EspacioParqueadero buscarEspacioDisponible(TipoVehiculo tipoVehiculo) {
        for (EspacioParqueadero e : listEspaciosParqueaderos) {
            if (e.getEstadoEspacio() == EstadoEspacio.DISPONIBLE &&
                    e.getTipoVehiculo() == tipoVehiculo) {
                return e;
            }
        }
        return null;
    }
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
     * @return
     */
    public String registrarNuevoEspacio(int codigo, TipoVehiculo tipoVehiculo) {
        String respuesta="";
        EspacioParqueadero espacio= obtenerEspacio(codigo);
        if (espacio != null) {
            respuesta= "El espacio ya existe en el registro del parqueadero";
        } else {
            EspacioParqueadero espacioNuevo= new EspacioParqueadero(codigo, tipoVehiculo);
            listEspaciosParqueaderos.add(espacioNuevo);
            respuesta= "Espacio registrado correctamente";
        }
        return respuesta;
    }

    /**
     * Método para modificar los atributos de un espacio parqueadero
     * @param espacio a modificar
     * @return respuesta de modificacion exitosa o no exitosa
     */
    public String modificarEspacio(EspacioParqueadero espacio) {
        String respuesta= "";
        EspacioParqueadero e= obtenerEspacio(espacio.getCodigo());
        if (e == null) {
            return "El espacio no existe en los registros del parqueadero";
        }else{
            e.setTipoVehiculo(espacio.getTipoVehiculo());
            e.setEstadoEspacio(espacio.getEstadoEspacio());
            respuesta="Espacio modificado correctamente";
        }
        return respuesta;
    }


    /**
     * Método para deshabilitar espacio
     * @param espacio
     * @return
     */
    public String deshabilitarEspacio(EspacioParqueadero espacio) {
        String respuesta="";
        EspacioParqueadero e= obtenerEspacio(espacio.getCodigo());
        if (e == null) {
            return "El espacio no existe en el parqueadero";
        }
        if (e.getEstadoEspacio() == EstadoEspacio.DISPONIBLE) {
            e.setEstadoEspacio(EstadoEspacio.MANTENIMIENTO);
            respuesta= "Espacio deshabilitado correctamente";
        } else {
            respuesta ="No se puede deshabilitar un espacio ocupado";
        }
        return respuesta;
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
     * @return El reporte con la lista de vehículos estacionados o un mensaje si no hay vehículos estacionados.
     */
    public String consultarVehiculosEstacionados() {
        StringBuilder reporte = new StringBuilder("=== VEHICULOS ACTUALMENTE ESTACIONADOS ===\n");
        for (Vehiculo v : listVehiculos) {
            if (v.getEstadoVehiculo() == EstadoVehiculo.DENTRO) {
                reporte.append("Placa: ").append(v.getPlaca())
                        .append(" | Tipo: ").append(v.getTipoVehiculo())
                        .append(" | Hora ingreso: ").append(v.getHoraIngreso());

                if (v.getTheEspacioParqueadero() != null) {
                    reporte.append(" | Espacio: ").append(v.getTheEspacioParqueadero().getCodigo());
                }
                reporte.append("\n");
            }
        }

        if (reporte.isEmpty()) {
            return "No hay vehículos estacionados";
        }

        return reporte.toString();
    }

    // ------------------CRUD TARIFA ------------------
    /**
     * Método para buscar y obtener una tarifa por tipo de vehículo
     * @param tipoVehiculo del vehículo al salir
     * @return la tarifa para el tipo de vehículo
     */
    public Tarifa obtenerTarifaPorTipoVehiculo(TipoVehiculo tipoVehiculo) {
        Tarifa tarifaEncontrada = null;
        for (Tarifa t : listTarifas) {
            if (t != null && t.getTipoVehiculo() == tipoVehiculo) {
                tarifaEncontrada = t;
                break;
            }
        }
        return tarifaEncontrada;
    }

    /**
     * Método para registrar una tarifa en la universidad
     * @param tipoVehiculo El tipo de vehículo para el cual se asignará la tarifa
     * @param tipoUsuario El tipo usuario para definir el descuento a aplicar
     * @return
     */
    public String registrarTarifa(TipoVehiculo tipoVehiculo, TipoUsuario tipoUsuario) {
        String respuesta= "";
        for(Tarifa tarifa:listTarifas) {
            if (tarifa.getTipoVehiculo() == tipoVehiculo) {
                return "La tarifa para este vehículo ya se encuentra creada";
            }
        }
        Tarifa tarifaNueva= new Tarifa(tipoVehiculo);
        listTarifas.add(tarifaNueva);
        tarifaNueva.setDescuento(tarifaNueva.calcularDescuento(tipoUsuario));
        respuesta=  " La tarifa para el vehículo tipo "+tipoVehiculo+" ha sido registrado correctamente";
        return respuesta;
    }

    /**
     * Método para eliminar una tarifa
     * @param tipoVehiculo para encontrar el tipo de tarifa a eliminar
     * @return mensaje con el resultado de la operación
     */
    public String eliminarTarifa(TipoVehiculo tipoVehiculo){
        String respuesta="";
        Tarifa tarifa = obtenerTarifaPorTipoVehiculo(tipoVehiculo);
        if(tarifa == null){
            respuesta = "Tarifa no encontrada";
        }else{
            listTarifas.remove(tarifa);
            respuesta = "Tarifa eliminado correctamente";
        }
        return respuesta;
    }
    /**
     * Método para modificar los atributos de una tarifa
     * @param valorHora de la tarifa a modificar
     * @param descuento de la tarifa a modificar
     * @param tipoVehiculo de la tarifa a modificar
     * @return respuesta de modificación exitosa o no exitosa
     */
    public String modificarTarifa(double valorHora, double descuento, TipoVehiculo tipoVehiculo) {
        String respuesta= "";
        Tarifa tarifa = obtenerTarifaPorTipoVehiculo(tipoVehiculo);
        if (tarifa == null) {
            return "La tarifa no está creada";
        }else{
            tarifa.setTipoVehiculo(tipoVehiculo);
            tarifa.setDescuento(descuento);
            tarifa.setValorPorHora(valorHora);
            respuesta="La tarifa ha sido modificado correctamente";
        }
        return respuesta;
    }

    /**
     * Método para generar la factura del vehículo
     * @param placa del vehículo
     * @param horaSalida del vehículo
     * @return mensaje con el resumen de la factura
     */
    public String generarFactura(String placa, String horaSalida) {
        String respuesta="";
        Vehiculo vehiculo = obtenerVehiculo(placa);

        if (vehiculo == null) {
            respuesta= "El vehículo no se encuentra registrado en el parqueadero";
        }

        double horas = calcularTiempoPermanencia(vehiculo.getHoraIngreso(), horaSalida);

        Tarifa tarifa = new Tarifa(vehiculo.getTipoVehiculo());
        double totalPagar = tarifa.calcularTotal(horas, vehiculo.getTheUsuario().getTipoUsuario());
        respuesta=  "Factura de salida del vehículo\n"+ "Placa: " + vehiculo.getPlaca() + "\n"+ "Tipo de vehículo: " + vehiculo.getTipoVehiculo() + "\n"+
                "Tiempo de permanencia: " + horas + " hora(s)\n"+
                "Valor por hora: $" + tarifa.asignarTarifa() + "\n"+
                "Descuento aplicado: " + tarifa.calcularDescuento(vehiculo.getTheUsuario().getTipoUsuario()) * 100 + "%\n"+
                "Total a pagar: $" + totalPagar + "\n";

        return respuesta;
    }

    // ------------------ CRUD USUARIO ------------------

    /**
     * Método para buscar y obtener un usuario de la lista de usuarios
     * @param identificacion del usuario
     * @return el usuario encontrado
     */
    public Usuario obtenerUsuario(String identificacion){
        Usuario uEncontrado= null;
        for(Usuario us:listUsuarios){
            if(us.getIdentificacion().equalsIgnoreCase(identificacion)){
                uEncontrado= us;
                break;
            }
        }
        return uEncontrado;
    }
    /**
     * Método para registrar un nuevo usuario en el parqueadero
     * @param nombre del usuario a registrar
     * @param identificacion del usuario a registrar
     * @param tipoUsuario del usuario a registrar
     * @return la respuesta de registro éxitoso o no éxitoso
     */
    public String registrarUsuario(String nombre, String identificacion, TipoUsuario tipoUsuario){
        String respuesta= "";
        Usuario usuario= obtenerUsuario(identificacion);
        if(usuario != null){
            return "El usuario ya se encuentra registrado";
        } else{
            Usuario nUsuario= new Usuario(nombre, identificacion, tipoUsuario);
            listUsuarios.add(nUsuario);
            respuesta= " El usuario " + nUsuario.getNombre()+ "ha sido registrado en el sistema éxitosamente";
        }
        return respuesta;
    }

    /**
     * Método para modificar los atributos de un usuario
     * @param nombre del usuario a mosificar
     * @param identificacion del usuario a mosificar
     * @param tipoUsuario del usuario a mosificar
     * @return
     */
    public String modificarUsuario(String nombre, String identificacion, TipoUsuario tipoUsuario){
        String respuesta="";
        Usuario usuarioAModificar= obtenerUsuario(identificacion);
        if(usuarioAModificar==null){
            return "El usuario no existe en el registro de usuarios";
        }else{
            usuarioAModificar.setNombre(nombre);
            usuarioAModificar.setTipoUsuario(tipoUsuario);
            respuesta= "El usuario "+usuarioAModificar.getNombre()+" ha sido modificado correctamente";
        }
        return respuesta;
    }
    /**
     * Método para Eliminar un usuario según la cédula.
     * @param identificacion Cédula del usuario a eliminar.
     * @return Mensaje indicando el resultado de la operación.
     */
    public String eliminarUsuario(String identificacion){
        String respuesta;
        Usuario usuario = obtenerUsuario(identificacion);
        if(usuario == null){
            respuesta = "Usuario no encontrado";
        }else{
            listUsuarios.remove(usuario);
            respuesta = "Usuario eliminado correctamente";
        }
        return respuesta;
    }
    /**
     * Método para listar todos los usuarios registrados en el parqueadero.
     * @return Cadena con la lista de usuarios.
     */
    public String listarUsuarios(){
        StringBuilder listaUsuarios = new StringBuilder("=== LISTA DE USUARIOS ===\n");
        if (listaUsuarios.isEmpty()) {
            listaUsuarios.append("No hay usuarios registrados en el parqueadero.\n");
        } else {
            for (Usuario u : listUsuarios) {
                listaUsuarios.append(u.toString()).append("\n");
            }
        }
        return listaUsuarios.toString();
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
            return "Invitado";
        }
    }
    /**
     * Método para iniciar sesión con la identificación del usuario
     * @param identificacion La identificación del usuario para verificar en la lista de usuarios
     * @return mensaje que indica si la sesión fue iniciada correctamente o no
     */
    public String iniciarSesion(String identificacion) {
        String respuesta= "";
        Usuario usuario= obtenerUsuario(identificacion);
        if(usuario!=null){
            respuesta= "Sesión iniciada correctamente para " + usuario.getNombre();
        }else {
            respuesta= "Usuario no encontrado";
        }
        return respuesta;
    }
    /**
     * Método para validar si el usuario tiene permiso de administrador
     * @param usuario El usuario que se quiere verificar
     * @return mensaje confirmando si el usuario es administrador o no
     */
    public String validarPermisoAdministrador(Usuario usuario) {
        String respuesta= "";
        if (usuario.getTipoUsuario() == TipoUsuario.ADMINISTRATIVO) {
            respuesta= "Permiso de administrador concedido.";
        } else {
            respuesta= "El usuario no tiene permiso de administrador.";
        }
        return respuesta;
    }
    /**
     * Método para validar si el usuario tiene permiso de operador
     * @param usuario El usuario que se quiere verificar
     * @return mensaje confirmando si el usuario es operador o no
     */
    public String validarPermisoOperador(Usuario usuario) {
        if (usuario.getTipoUsuario() == TipoUsuario.ESTUDIANTE || usuario.getTipoUsuario() == TipoUsuario.DOCENTE) {
            return "Permiso de operador concedido.";
        } else {
            return "El usuario no tiene permiso de operador.";
        }
    }
    // ------------------ REPORTES DEL PARQUEADERO ------------------
    /**
     * Método para generar el reporte de las actividades del parqueadero
     * @return
     */
    public String generarReporte() {
        StringBuilder reporte = new StringBuilder("=== REPORTE GENERAL DEL PARQUEADERO ===\n");
        int dentro = 0;
        int fuera = 0;
        int disponibles = 0;
        for (Vehiculo v : listVehiculos) {
            if (v.getEstadoVehiculo() == EstadoVehiculo.DENTRO) {
                dentro++;
            } else if (v.getEstadoVehiculo() == EstadoVehiculo.FUERA) {
                fuera++;
            }
        }

        for (EspacioParqueadero e : listEspaciosParqueaderos) {
            if (e.getEstadoEspacio() == EstadoEspacio.DISPONIBLE) {
                disponibles++;
            }
        }
        //Datos generales
        reporte.append("Total vehículos registrados: ").append(listVehiculos.size()).append("\n")
                .append("Vehículos dentro: ").append(dentro).append("\n")
                .append("Vehículos fuera: ").append(fuera).append("\n")
                .append("Espacios totales: ").append(listEspaciosParqueaderos.size()).append("\n")
                .append("Espacios disponibles: ").append(disponibles).append("\n");
        //Ingresos Totales
        double ingresos = ingresosGeneradosDia();
        reporte.append("Ingresos generados hoy: $").append(ingresos).append("\n");

        // Tiempo promedio de permanencia
        double tiempoPromedio = tiempoPromedioPermanencia();
        reporte.append("Tiempo promedio de permanencia hoy: ").append(tiempoPromedio).append(" horas\n");

        // Vehículos que permanecieron más de 4 horas
        reporte.append("Vehículos que permanecieron más de 4 horas: \n");
        reporte.append(vehiculosMasDeDeterminadasHoras(4));

        return reporte.toString();
    }

    /**
     * Método para calcular los ingresos generados hoy.
     * @return El total de ingresos generados hoy.
     */
    public double ingresosGeneradosDia() {
        double totalIngresos = 0;
        LocalDate hoy = LocalDate.now();  // Obtenemos la fecha de hoy

        for (Vehiculo v : listVehiculos) {
            LocalDate ingresoFecha = LocalDate.parse(v.getHoraIngreso().split(" ")[0]);
            if (ingresoFecha.equals(hoy)) {
                double tiempo = calcularTiempoPermanencia(v.getHoraIngreso(), v.getHoraSalida());
                Tarifa tarifa = obtenerTarifaPorTipoVehiculo(v.getTipoVehiculo());
                double ingresos = tarifa.calcularTotal(tiempo, v.getTheUsuario().getTipoUsuario());
                totalIngresos += ingresos;
            }
        }
        return totalIngresos;
    }
    /**
     * Método para calcular el tiempo promedio de permanencia de los vehículos en el parqueadero.
     * @return El tiempo promedio de permanencia en horas.
     */
    public double tiempoPromedioPermanencia() {
        double totalTiempo = 0;
        int totalVehiculos = 0;
        double promedio=0;

        for (Vehiculo v : listVehiculos) {
            if (v.getHoraSalida() != null) {
                double tiempo = calcularTiempoPermanencia(v.getHoraIngreso(), v.getHoraSalida());
                totalTiempo += tiempo;
                totalVehiculos++;
            }

        }
        if (totalVehiculos > 0){
            promedio= totalTiempo / totalVehiculos;
        }
        return promedio;
    }

    /**
     * Método para obtener los vehículos que han permanecido más de X horas en el parqueadero.
     * @param horas El número de horas de permanencia que debe superar el vehículo para ser incluido.
     * @return Un reporte de vehículos que han permanecido más de X horas.
     */
    public String vehiculosMasDeDeterminadasHoras(double horas) {
        StringBuilder reporte = new StringBuilder();

        for (Vehiculo v : listVehiculos) {
            double tiempo = calcularTiempoPermanencia(v.getHoraIngreso(), v.getHoraSalida());
            if (tiempo > horas) {
                reporte.append("Placa: ").append(v.getPlaca())
                        .append(" | Tipo: ").append(v.getTipoVehiculo())
                        .append(" | Tiempo de permanencia: ").append(tiempo)
                        .append(" horas\n");
            }
        }
        if(reporte.isEmpty()){
            reporte.append("No hay vehículos que hayan permanecido más de ").append(horas).append(" horas.");
        }
        return reporte.toString();
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
