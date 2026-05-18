import javax.swing.JOptionPane;
import model.*;

public class Main {
    public static void main(String[] args) {
        // Crear la universidad
        Universidad universidad = new Universidad("Universidad del Quindío", 123456789, "Calle 20 #30-40");


        // Menu para el Main
        while (true) {
            String[] opciones = {
                    "Registrar Vehículo",
                    "Registrar Entrada de Vehículo",
                    "Generar Factura",
                    "Consultar Vehículos Estacionados",
                    "Generar Reportes",
                    "Registrar Salida de Vehículo",
                    "Salir"
            };

            int opcion = JOptionPane.showOptionDialog(null, "Selecciona una opción", "Menú Principal",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

            switch (opcion) {
                case 0:  // Registrar Vehículo
                    registrarVehiculo(universidad);
                    break;
                case 1:  // Registrar Entrada de Vehículo
                    registrarEntradaVehiculo(universidad);
                    break;
                case 2:  // Generar Factura
                    generarFactura(universidad);
                    break;
                case 3:  // Consultar Vehículos Estacionados
                    consultarVehiculosEstacionados(universidad);
                    break;
                case 4:  // Generar Reportes
                    generarReportes(universidad);
                case 5:  // Registrar Salida de Vehículo
                    registrarSalidaVehiculo(universidad);
                    break;
                case 6:  // Salir
                    JOptionPane.showMessageDialog(null, "¡Saliendo del programa!");
                    return;
                default:
                    JOptionPane.showMessageDialog(null, "Ingrese un número válido");
                    break;
            }
        }
    }

    // Método para registrar una tarifa
    public static String registrarTarifa(Universidad universidad) {
        String[] tiposVehiculos = {"CARRO", "MOTOCICLETA", "BICICLETA"};
        String tipo = (String) JOptionPane.showInputDialog(null, "Selecciona el tipo de vehículo", "Registrar Tarifa",
                JOptionPane.QUESTION_MESSAGE, null, tiposVehiculos, tiposVehiculos[0]);

        if (tipo == null) return "Operación cancelada";

        TipoVehiculo tipoVehiculo = TipoVehiculo.valueOf(tipo);

        String descuento = JOptionPane.showInputDialog("Introduce el descuento para el vehículo " + tipo + " (como porcentaje, ej. 0.10 para 10%)");
        if (descuento == null) return "Operación cancelada";

        double descuentoValue = Double.parseDouble(descuento);

        return universidad.registrarTarifa(tipoVehiculo, TipoUsuario.ADMINISTRATIVO); // Llamamos al método en la universidad
    }

    /**
     * Método para registrar un vehículo con/o sin su conductor
     * @param universidad
     */
    public static void registrarVehiculo(Universidad universidad) {
        String placa = JOptionPane.showInputDialog("Introduce la placa del vehículo:");
        String nombreConductor = JOptionPane.showInputDialog("Introduce el nombre del conductor:");
        String identificacionConductor = JOptionPane.showInputDialog("Introduce la identificación del conductor:");

        String horaIngreso = JOptionPane.showInputDialog("Introduce la hora de ingreso (formato HH:mm):");

        // Solicitar el tipo de vehículo
        TipoVehiculo tipoVehiculo = (TipoVehiculo) JOptionPane.showInputDialog(null,
                "Selecciona el tipo de vehículo", "Registrar Vehículo",
                JOptionPane.QUESTION_MESSAGE, null, TipoVehiculo.values(), null);

        Usuario theUsuario = universidad.obtenerUsuario(identificacionConductor);
        // Si el usuario no está registrado, pedir los datos y crear el usuario
        if (theUsuario == null) {
            JOptionPane.showMessageDialog(null, "El usuario no está registrado. Se procederá a crear el usuario.");

            // Solicitar los datos para crear el usuario
            TipoUsuario tipoUsuario= (TipoUsuario) JOptionPane.showInputDialog(null,"Selecciona el tipo de usuario", "Registrar Usuario", JOptionPane.QUESTION_MESSAGE, null, TipoUsuario.values(), TipoUsuario.ESTUDIANTE);

            // Crear el usuario
            theUsuario = new Usuario(nombreConductor, identificacionConductor, tipoUsuario);
            universidad.registrarUsuario(theUsuario.getNombre(), theUsuario.getIdentificacion(), theUsuario.getTipoUsuario());
            JOptionPane.showMessageDialog(null, "Usuario creado exitosamente: " + theUsuario.getNombre());
        }

        // Ahora que tenemos el usuario, proceder con el registro del vehículo
        try {
            universidad.registrarEntradaVehiculo(placa, nombreConductor, Integer.parseInt(identificacionConductor), horaIngreso, tipoVehiculo, theUsuario);
            JOptionPane.showMessageDialog(null, "Vehículo registrado exitosamente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al registrar el vehículo: " + e.getMessage());
        }
    }

    // Método para registrar la entrada de un vehículo
    public static void registrarEntradaVehiculo(Universidad universidad) {
        // Solicitar la placa del vehículo
        String placa = JOptionPane.showInputDialog("Introduce la placa del vehículo:");

        // Buscar el vehículo en el sistema
        Vehiculo vehiculoExistente = universidad.obtenerVehiculo(placa);

        // Verificar si el vehículo ya está registrado
        if (vehiculoExistente == null) {
            // Si el vehículo no está registrado, ofrecer la opción de registrarlo
            int opcion = JOptionPane.showConfirmDialog(null, "El vehículo no está registrado. ¿Quieres registrarlo?", "Registro de Vehículo",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (opcion == JOptionPane.YES_OPTION) {
                // Llamamos al método para registrar el vehículo
                registrarVehiculo(universidad);
            } else {
                JOptionPane.showMessageDialog(null, "El vehículo no ha sido registrado. Proceso cancelado.");
                return;
            }
        }
        // Si el vehículo ya está registrado, se obtiene la información del vehículo
        String nombreConductor = vehiculoExistente.getNombreConductor();
        int identificacionConductor = vehiculoExistente.getIdentificacionConductor();
        String horaIngreso = JOptionPane.showInputDialog("Introduce la hora de ingreso (formato HH:mm):");
        TipoVehiculo tipoVehiculo = vehiculoExistente.getTipoVehiculo();
        Usuario theUsuario = vehiculoExistente.getTheUsuario();

        try {
            // Registrar la entrada del vehículo en el sistema
            String resultado = universidad.registrarEntradaVehiculo(placa, nombreConductor, identificacionConductor, horaIngreso, tipoVehiculo, theUsuario);
            JOptionPane.showMessageDialog(null, resultado);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al registrar la entrada: " + e.getMessage());
        }
    }
    // Método para registrar la salida de un vehículo
    public static void registrarSalidaVehiculo(Universidad universidad) {
        String placa = JOptionPane.showInputDialog("Introduce la placa del vehículo:");
        String horaSalida = JOptionPane.showInputDialog("Introduce la hora de salida (formato HH:mm):");
        Vehiculo vehiculoExistente = universidad.obtenerVehiculo(placa);
        if (vehiculoExistente == null) {
            JOptionPane.showMessageDialog(null, "El vehículo con la placa " + placa + " no está registrado.");
            return;
        }
        try {
            String resultado = universidad.registrarSalidaVehiculo(placa, horaSalida);
            if (resultado.contains("completado")) {
                // Generar factura
                String factura = universidad.generarFactura(placa, horaSalida);

                // Mostrar la factura con el total a pagar
                JOptionPane.showMessageDialog(null, factura);
            } else {
                // Si la salida no se registró correctamente, mostrar el mensaje
                JOptionPane.showMessageDialog(null, resultado);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al registrar la salida: " + e.getMessage());
        }
    }

    // Método para generar la factura de un vehículo
    public static void generarFactura(Universidad universidad) {
        String placa = JOptionPane.showInputDialog("Introduce la placa del vehículo para generar la factura:");
        String horaSalida = JOptionPane.showInputDialog("Introduce la hora de salida (formato HH:mm):");

        String factura = universidad.generarFactura(placa, horaSalida);
        JOptionPane.showMessageDialog(null, factura);
    }

    // Método para consultar los vehículos estacionados
    public static void consultarVehiculosEstacionados(Universidad universidad) {
        String estadoVehiculos = universidad.consultarVehiculosEstacionados();
        JOptionPane.showMessageDialog(null, estadoVehiculos);
    }

    // Método para generar reportes
    public static void generarReportes(Universidad universidad) {
        String reportes = universidad.generarReporte();
        JOptionPane.showMessageDialog(null, reportes);
    }
}