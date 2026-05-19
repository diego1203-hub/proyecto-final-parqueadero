package co.edu.uniquindio.edu.co.poo.parqueaderojfx.viewController;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.edu.co.poo.parqueaderojfx.App;
import co.edu.uniquindio.edu.co.poo.parqueaderojfx.controller.VehiculoController;
import co.edu.uniquindio.edu.co.poo.parqueaderojfx.model.Vehiculo;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class VehiculoViewController {

    VehiculoController vehiculoController;

    ObservableList<Vehiculo> listVehiculos =
            FXCollections.observableArrayList();

    Vehiculo selectedVehiculo;

    private App app;

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
    private ComboBox<String> cbTipoVehiculo;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnActualizarVehiculo;

    @FXML
    private Button btnAgregarVehiculo;

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
    void onAgregarVehiculo() {
        agregarVehiculo();
    }

    @FXML
    void onActualizarVehiculo() {
        actualizarVehiculo();
    }

    @FXML
    void onLimpiar() {
        limpiarSeleccion();
    }

    @FXML
    void onEliminar() {
        eliminarVehiculo();
    }

    @FXML
    void initialize() {

        cbTipoVehiculo.setItems(
                FXCollections.observableArrayList(
                        "CARRO",
                        "MOTOCICLETA",
                        "BICICLETA"
                )
        );
    }

    private void initView() {

        initDataBinding();

        obtenerVehiculos();

        tblListVehiculo.getItems().clear();

        tblListVehiculo.setItems(listVehiculos);

        listenerSelection();
    }

    private void initDataBinding() {

        tbcPlaca.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getPlaca()
                ));

        tbcNombreConductor.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getNombreConductor()
                ));

        tbcIdentificacionConductor.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        String.valueOf(
                                cellData.getValue().getIdentificacionConductor()
                        )
                ));

        tbcHoraIngreso.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getHoraIngreso()
                ));

        tbcHoraSalida.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getHoraSalida()
                ));

        tbcTipoVehiculo.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getTipoVehiculo().toString()
                ));
    }

    private void obtenerVehiculos() {
        listVehiculos.addAll(
                vehiculoController.obtenerListaVehiculos()
        );
    }

    private void listenerSelection() {

        tblListVehiculo
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldSelection, newSelection) -> {

                    selectedVehiculo = newSelection;

                    mostrarInformacionVehiculo(selectedVehiculo);
                });
    }

    private void mostrarInformacionVehiculo(Vehiculo vehiculo) {

        if (vehiculo != null) {

            txtPlaca.setText(
                    vehiculo.getPlaca()
            );

            txtNombreConductor.setText(
                    vehiculo.getNombreConductor()
            );

            txtIdentificacionConductor.setText(
                    String.valueOf(
                            vehiculo.getIdentificacionConductor()
                    )
            );

            txtHoraIngreso.setText(
                    vehiculo.getHoraIngreso()
            );

            txtHoraSalida.setText(
                    vehiculo.getHoraSalida()
            );

            cbTipoVehiculo.setValue(
                    vehiculo.getTipoVehiculo().toString()
            );
        }
    }

    private void agregarVehiculo() {

        Vehiculo vehiculo = buildVehiculo();

        if (vehiculoController.crearVehiculo(vehiculo)) {

            listVehiculos.add(vehiculo);

            limpiarCamposVehiculo();
        }
    }

    private Vehiculo buildVehiculo() {

        Vehiculo vehiculo =
                new Vehiculo(
                        txtPlaca.getText(),
                        txtNombreConductor.getText(),
                        Integer.parseInt(
                                txtIdentificacionConductor.getText()
                        ),
                        txtHoraIngreso.getText(),
                        txtHoraSalida.getText(),
                        cbTipoVehiculo.getValue()
                );

        return vehiculo;
    }

    private void eliminarVehiculo() {

        if (vehiculoController.eliminarVehiculo(
                txtPlaca.getText()
        )) {

            listVehiculos.remove(selectedVehiculo);

            limpiarCamposVehiculo();

            limpiarSeleccion();
        }
    }

    private void actualizarVehiculo() {

        if (selectedVehiculo != null &&
                vehiculoController.actualizarVehiculo(
                        selectedVehiculo.getPlaca(),
                        buildVehiculo()
                )) {

            int index =
                    listVehiculos.indexOf(selectedVehiculo);

            if (index >= 0) {
                listVehiculos.set(index, buildVehiculo());
            }

            tblListVehiculo.refresh();

            limpiarSeleccion();

            limpiarCamposVehiculo();
        }
    }

    private void limpiarSeleccion() {

        tblListVehiculo
                .getSelectionModel()
                .clearSelection();

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

    public void setApp(App app) {

        this.app = app;

        vehiculoController =
                new VehiculoController(app.universidad);

        initView();
    }
}