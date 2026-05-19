package co.uniquindio.edu.co.poo.parqueaderojfx.viewController;

import co.uniquindio.edu.co.poo.parqueaderojfx.App;
import co.uniquindio.edu.co.poo.parqueaderojfx.controller.TarifaController;
import co.uniquindio.edu.co.poo.parqueaderojfx.model.Tarifa;
import co.uniquindio.edu.co.poo.parqueaderojfx.model.TipoUsuario;
import co.uniquindio.edu.co.poo.parqueaderojfx.model.TipoVehiculo;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class TarifaViewController {

    private App app;
    private TarifaController tarifaController;
    private ObservableList<Tarifa> listTarifas = FXCollections.observableArrayList();
    private Tarifa selectedTarifa;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<TipoVehiculo> cbTipoVehiculo;

    @FXML
    private ComboBox<TipoUsuario> cbTipoUsuario;

    @FXML
    private TextField txtValorHora;

    @FXML
    private TextField txtDescuento;

    @FXML
    private Button btnAgregarTarifa;

    @FXML
    private Button btnActualizarTarifa;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private TableView<Tarifa> tblListTarifas;

    @FXML
    private TableColumn<Tarifa, String> tbcTipoVehiculo;

    @FXML
    private TableColumn<Tarifa, String> tbcValorHora;

    @FXML
    private TableColumn<Tarifa, String> tbcDescuento;

    @FXML
    void onAgregarTarifa() {
        agregarTarifa();
    }

    @FXML
    void onActualizarTarifa() {
        actualizarTarifa();
    }

    @FXML
    void onEliminar() {
        eliminarTarifa();
    }

    @FXML
    void onLimpiar() {
        limpiarSeleccion();
    }

    @FXML
    void initialize() {
        cbTipoVehiculo.setItems(FXCollections.observableArrayList(TipoVehiculo.values()));
        cbTipoUsuario.setItems(FXCollections.observableArrayList(TipoUsuario.values()));
    }

    private void initView() {
        initDataBinding();
        obtenerTarifas();
        tblListTarifas.setItems(listTarifas);
        listenerSelection();
    }

    private void initDataBinding() {
        tbcTipoVehiculo.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTipoVehiculo().toString()));

        tbcValorHora.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getValorPorHora())));

        tbcDescuento.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getDescuento())));
    }

    private void obtenerTarifas() {
        listTarifas.clear();
        listTarifas.addAll(tarifaController.obtenerListaTarifas());
    }

    private void listenerSelection() {
        tblListTarifas.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    selectedTarifa = newSelection;
                    mostrarInformacionTarifa(selectedTarifa);
                }
        );
    }

    private void mostrarInformacionTarifa(Tarifa tarifa) {
        if (tarifa != null) {
            cbTipoVehiculo.setValue(tarifa.getTipoVehiculo());
            txtValorHora.setText(String.valueOf(tarifa.getValorPorHora()));
            txtDescuento.setText(String.valueOf(tarifa.getDescuento()));
        }
    }

    private void agregarTarifa() {
        try {
            TipoVehiculo tipoVehiculo = cbTipoVehiculo.getValue();
            TipoUsuario tipoUsuario = cbTipoUsuario.getValue();

            if (tipoVehiculo == null || tipoUsuario == null) {
                mostrarMensaje("Debe seleccionar el tipo de vehículo y el tipo de usuario.");
                return;
            }

            String result = tarifaController.registrarTarifa(tipoVehiculo, tipoUsuario);

            if (result.contains("registrado correctamente")) {
                actualizarTabla();
                limpiarCamposTarifa();
            }

            mostrarMensaje(result);

        } catch (Exception e) {
            mostrarMensaje("Error: " + e.getMessage());
        }
    }

    private void actualizarTarifa() {
        try {
            if (selectedTarifa == null) {
                mostrarMensaje("Debe seleccionar una tarifa para actualizar.");
                return;
            }

            TipoVehiculo tipoVehiculo = cbTipoVehiculo.getValue();
            double valorHora = Double.parseDouble(txtValorHora.getText());
            double descuento = Double.parseDouble(txtDescuento.getText());

            String result = tarifaController.modificarTarifa(
                    valorHora,
                    descuento,
                    tipoVehiculo
            );

            if (result.contains("modificado correctamente")) {
                actualizarTabla();
                limpiarSeleccion();
            }

            mostrarMensaje(result);

        } catch (NumberFormatException e) {
            mostrarMensaje("El valor por hora y el descuento deben ser numéricos.");
        } catch (Exception e) {
            mostrarMensaje("Error: " + e.getMessage());
        }
    }

    private void eliminarTarifa() {
        if (selectedTarifa != null) {
            String result = tarifaController.eliminarTarifa(
                    selectedTarifa.getTipoVehiculo()
            );

            if (result.contains("eliminado correctamente")) {
                listTarifas.remove(selectedTarifa);
                limpiarSeleccion();
            }

            mostrarMensaje(result);
        } else {
            mostrarMensaje("Debe seleccionar una tarifa para eliminar.");
        }
    }

    private void actualizarTabla() {
        obtenerTarifas();
        tblListTarifas.refresh();
    }

    private void limpiarSeleccion() {
        tblListTarifas.getSelectionModel().clearSelection();
        selectedTarifa = null;
        limpiarCamposTarifa();
    }

    private void limpiarCamposTarifa() {
        cbTipoVehiculo.setValue(null);
        cbTipoUsuario.setValue(null);
        txtValorHora.clear();
        txtDescuento.clear();
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
        this.tarifaController = new TarifaController(app.universidad);
        initView();
    }
}
