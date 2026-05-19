package co.uniquindio.edu.co.poo.parqueaderojfx.viewController;

import co.uniquindio.edu.co.poo.parqueaderojfx.App;
import co.uniquindio.edu.co.poo.parqueaderojfx.controller.VehiculoController;
import co.uniquindio.edu.co.poo.parqueaderojfx.model.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class VehiculoViewController {

    private App app;
    private VehiculoController vehiculoController;

    private ObservableList<Vehiculo> listVehiculos = FXCollections.observableArrayList();

    private Vehiculo selectedVehiculo;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtPlaca;

    @FXML
    private TextField txtNombreConductor;

    @FXML
    private TextField txtIdentificacionConductor;

    @FXML
    private TextField txtHoraIngreso;

    @FXML
    private TextField txtHoraSalida;

    @FXML
    private ComboBox<TipoVehiculo> cbTipoVehiculo;

    @FXML
    private Button btnAgregarVehiculo;

    @FXML
    private Button btnRegistrarSalida;

    @FXML
    private Button btnLimpiar;

    @FXML
    private TableView<Vehiculo> tblListVehiculo;

    @FXML
    private TableColumn<Vehiculo, String> tbcPlaca;

    @FXML
    private TableColumn<Vehiculo, String> tbcNombreConductor;

    @FXML
    private TableColumn<Vehiculo, String> tbcIdentificacionConductor;

    @FXML
    private TableColumn<Vehiculo, String> tbcHoraIngreso;

    @FXML
    private TableColumn<Vehiculo, String> tbcHoraSalida;

    @FXML
    private TableColumn<Vehiculo, String> tbcTipoVehiculo;

    @FXML
    private TableColumn<Vehiculo, String> tbcEstadoVehiculo;

    @FXML
    void onAgregarVehiculo() {
        registrarEntradaVehiculo();
    }

    @FXML
    void onRegistrarSalida() {
        registrarSalidaVehiculo();
    }

    @FXML
    void onLimpiar() {
        limpiarSeleccion();
    }

    @FXML
    void initialize() {
        cbTipoVehiculo.setItems(
                FXCollections.observableArrayList(TipoVehiculo.values())
        );
    }

    private void initView() {
        initDataBinding();
        obtenerVehiculos();
        tblListVehiculo.setItems(listVehiculos);
        listenerSelection();
    }

    private void initDataBinding() {
        tbcPlaca.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPlaca()));

        tbcNombreConductor.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNombreConductor()));

        tbcIdentificacionConductor.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        String.valueOf(cellData.getValue().getIdentificacionConductor())
                ));

        tbcHoraIngreso.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getHoraIngreso()));

        tbcHoraSalida.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getHoraSalida() == null
                                ? "Sin salida"
                                : cellData.getValue().getHoraSalida()
                ));

        tbcTipoVehiculo.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTipoVehiculo().toString()));

        tbcEstadoVehiculo.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEstadoVehiculo().toString()));
    }

    private void obtenerVehiculos() {
        listVehiculos.clear();
        listVehiculos.addAll(vehiculoController.obtenerListaVehiculos());
    }

    private void listenerSelection() {
        tblListVehiculo.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    selectedVehiculo = newSelection;
                    mostrarInformacionVehiculo(selectedVehiculo);
                }
        );
    }

    private void mostrarInformacionVehiculo(Vehiculo vehiculo) {
        if (vehiculo != null) {
            txtPlaca.setText(vehiculo.getPlaca());
            txtNombreConductor.setText(vehiculo.getNombreConductor());
            txtIdentificacionConductor.setText(
                    String.valueOf(vehiculo.getIdentificacionConductor())
            );
            txtHoraIngreso.setText(vehiculo.getHoraIngreso());
            txtHoraSalida.setText(vehiculo.getHoraSalida());
            cbTipoVehiculo.setValue(vehiculo.getTipoVehiculo());
        }
    }

    private void registrarEntradaVehiculo() {
        try {
            String placa = txtPlaca.getText();
            String nombreConductor = txtNombreConductor.getText();
            int identificacionConductor = Integer.parseInt(txtIdentificacionConductor.getText());
            String horaIngreso = txtHoraIngreso.getText();
            TipoVehiculo tipoVehiculo = cbTipoVehiculo.getValue();

            if (placa.isEmpty() || nombreConductor.isEmpty() || horaIngreso.isEmpty() || tipoVehiculo == null) {
                mostrarMensaje("Debe completar todos los campos para registrar la entrada.");
                return;
            }

            String identificacionUsuario = String.valueOf(identificacionConductor);

            Usuario usuario = vehiculoController.obtenerUsuario(identificacionUsuario);

            if (usuario == null) {
                usuario = new Usuario(
                        nombreConductor,
                        identificacionUsuario,
                        TipoUsuario.VISITANTE
                );

                vehiculoController.registrarUsuario(
                        usuario.getNombre(),
                        usuario.getIdentificacion(),
                        usuario.getTipoUsuario()
                );
            }

            String resultado = vehiculoController.registrarEntradaVehiculo(
                    placa,
                    nombreConductor,
                    identificacionConductor,
                    horaIngreso,
                    tipoVehiculo,
                    usuario
            );

            mostrarMensaje(resultado);
            actualizarTabla();
            limpiarCamposVehiculo();

        } catch (NumberFormatException e) {
            mostrarMensaje("La identificación del conductor debe ser numérica.");
        } catch (Exception e) {
            mostrarMensaje("Error: " + e.getMessage());
        }
    }

    private void registrarSalidaVehiculo() {
        try {
            String placa = txtPlaca.getText();
            String horaSalida = txtHoraSalida.getText();

            if (placa.isEmpty() || horaSalida.isEmpty()) {
                mostrarMensaje("Debe ingresar la placa y la hora de salida.");
                return;
            }

            String factura = vehiculoController.generarFactura(placa, horaSalida);

            String resultado = vehiculoController.registrarSalidaVehiculo(
                    placa,
                    horaSalida
            );

            mostrarMensaje(resultado + "\n\n" + factura);

            actualizarTabla();
            limpiarCamposVehiculo();

        } catch (Exception e) {
            mostrarMensaje("Error: " + e.getMessage());
        }
    }

    private void actualizarTabla() {
        obtenerVehiculos();
        tblListVehiculo.refresh();
    }

    private void limpiarSeleccion() {
        tblListVehiculo.getSelectionModel().clearSelection();
        selectedVehiculo = null;
        limpiarCamposVehiculo();
    }

    private void limpiarCamposVehiculo() {
        txtPlaca.clear();
        txtNombreConductor.clear();
        txtIdentificacionConductor.clear();
        txtHoraIngreso.clear();
        txtHoraSalida.clear();
        cbTipoVehiculo.setValue(null);
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void setApp(App app) {
        this.app = app;
        this.vehiculoController = new VehiculoController(app.universidad);
        initView();
    }
}
