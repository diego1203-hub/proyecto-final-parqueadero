package co.uniquindio.edu.co.poo.parqueaderojfx.viewController;

import co.uniquindio.edu.co.poo.parqueaderojfx.App;
import co.uniquindio.edu.co.poo.parqueaderojfx.controller.EspacioParqueaderoController;
import co.uniquindio.edu.co.poo.parqueaderojfx.model.EspacioParqueadero;
import co.uniquindio.edu.co.poo.parqueaderojfx.model.EstadoEspacio;
import co.uniquindio.edu.co.poo.parqueaderojfx.model.TipoVehiculo;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class EspacioParqueaderoViewController {

    private App app;
    private EspacioParqueaderoController espacioParqueaderoController;
    private ObservableList<EspacioParqueadero> listEspacios = FXCollections.observableArrayList();
    private EspacioParqueadero selectedEspacio;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtCodigo;

    @FXML
    private ComboBox<TipoVehiculo> cbTipoVehiculo;

    @FXML
    private ComboBox<EstadoEspacio> cbEstadoEspacio;

    @FXML
    private Button btnAgregarEspacio;

    @FXML
    private Button btnActualizarEspacio;

    @FXML
    private Button btnDeshabilitarEspacio;

    @FXML
    private Button btnLimpiar;

    @FXML
    private TableView<EspacioParqueadero> tblListEspacios;

    @FXML
    private TableColumn<EspacioParqueadero, String> tbcCodigo;

    @FXML
    private TableColumn<EspacioParqueadero, String> tbcTipoVehiculo;

    @FXML
    private TableColumn<EspacioParqueadero, String> tbcEstadoEspacio;

    @FXML
    private TableColumn<EspacioParqueadero, String> tbcVehiculoAsignado;

    @FXML
    void onAgregarEspacio() {
        agregarEspacio();
    }

    @FXML
    void onActualizarEspacio() {
        actualizarEspacio();
    }

    @FXML
    void onDeshabilitarEspacio() {
        deshabilitarEspacio();
    }

    @FXML
    void onLimpiar() {
        limpiarSeleccion();
    }

    @FXML
    void initialize() {
        cbTipoVehiculo.setItems(FXCollections.observableArrayList(TipoVehiculo.values()));
        cbEstadoEspacio.setItems(FXCollections.observableArrayList(EstadoEspacio.values()));
    }

    private void initView() {
        initDataBinding();
        obtenerEspacios();
        tblListEspacios.setItems(listEspacios);
        listenerSelection();
    }

    private void initDataBinding() {
        tbcCodigo.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getCodigo())));

        tbcTipoVehiculo.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTipoVehiculo().toString()));

        tbcEstadoEspacio.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEstadoEspacio().toString()));

        tbcVehiculoAsignado.setCellValueFactory(cellData -> {
            if (cellData.getValue().getTheVehiculo() != null) {
                return new SimpleStringProperty(cellData.getValue().getTheVehiculo().getPlaca());
            }
            return new SimpleStringProperty("Sin vehículo");
        });
    }

    private void obtenerEspacios() {
        listEspacios.clear();
        listEspacios.addAll(espacioParqueaderoController.obtenerListaEspacios());
    }

    private void listenerSelection() {
        tblListEspacios.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    selectedEspacio = newSelection;
                    mostrarInformacionEspacio(selectedEspacio);
                }
        );
    }

    private void mostrarInformacionEspacio(EspacioParqueadero espacio) {
        if (espacio != null) {
            txtCodigo.setText(String.valueOf(espacio.getCodigo()));
            cbTipoVehiculo.setValue(espacio.getTipoVehiculo());
            cbEstadoEspacio.setValue(espacio.getEstadoEspacio());
        }
    }

    private void agregarEspacio() {
        try {
            int codigo = Integer.parseInt(txtCodigo.getText());
            TipoVehiculo tipoVehiculo = cbTipoVehiculo.getValue();

            if (tipoVehiculo == null) {
                mostrarMensaje("Debe seleccionar el tipo de vehículo.");
                return;
            }

            String result = espacioParqueaderoController.registrarNuevoEspacio(codigo, tipoVehiculo);

            if (result.contains("correctamente")) {
                actualizarTabla();
                limpiarCamposEspacio();
            }

            mostrarMensaje(result);

        } catch (NumberFormatException e) {
            mostrarMensaje("El código debe ser numérico.");
        } catch (Exception e) {
            mostrarMensaje("Error: " + e.getMessage());
        }
    }

    private void actualizarEspacio() {
        try {
            if (selectedEspacio == null) {
                mostrarMensaje("Debe seleccionar un espacio para actualizar.");
                return;
            }

            int codigo = Integer.parseInt(txtCodigo.getText());
            TipoVehiculo tipoVehiculo = cbTipoVehiculo.getValue();
            EstadoEspacio estadoEspacio = cbEstadoEspacio.getValue();

            if (tipoVehiculo == null || estadoEspacio == null) {
                mostrarMensaje("Debe seleccionar tipo de vehículo y estado.");
                return;
            }

            EspacioParqueadero espacio = new EspacioParqueadero(codigo, tipoVehiculo);
            espacio.setEstadoEspacio(estadoEspacio);

            String result = espacioParqueaderoController.modificarEspacio(espacio);

            if (result.contains("correctamente")) {
                actualizarTabla();
                limpiarSeleccion();
            }

            mostrarMensaje(result);

        } catch (NumberFormatException e) {
            mostrarMensaje("El código debe ser numérico.");
        } catch (Exception e) {
            mostrarMensaje("Error: " + e.getMessage());
        }
    }

    private void deshabilitarEspacio() {
        try {
            if (selectedEspacio == null) {
                mostrarMensaje("Debe seleccionar un espacio para deshabilitar.");
                return;
            }

            String result = espacioParqueaderoController.deshabilitarEspacio(selectedEspacio);

            if (result.contains("correctamente")) {
                actualizarTabla();
                limpiarSeleccion();
            }

            mostrarMensaje(result);

        } catch (Exception e) {
            mostrarMensaje("Error: " + e.getMessage());
        }
    }

    private void actualizarTabla() {
        obtenerEspacios();
        tblListEspacios.refresh();
    }

    private void limpiarSeleccion() {
        tblListEspacios.getSelectionModel().clearSelection();
        selectedEspacio = null;
        limpiarCamposEspacio();
    }

    private void limpiarCamposEspacio() {
        txtCodigo.clear();
        cbTipoVehiculo.setValue(null);
        cbEstadoEspacio.setValue(null);
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
        this.espacioParqueaderoController = new EspacioParqueaderoController(app.universidad);
        initView();
    }
}
