package co.uniquindio.edu.co.poo.parqueaderojfx.viewController;

import java.net.URL;
import java.util.ResourceBundle;

import co.uniquindio.edu.co.poo.parqueaderojfx.App;
import co.uniquindio.edu.co.poo.parqueaderojfx.controller.UsuarioController;
import co.uniquindio.edu.co.poo.parqueaderojfx.model.TipoUsuario;
import co.uniquindio.edu.co.poo.parqueaderojfx.model.Usuario;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class UsuarioViewController {

    private App app;
    private UsuarioController usuarioController;
    private ObservableList<Usuario> listUsuarios = FXCollections.observableArrayList();
    private Usuario selectedUsuario;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtIdentificacion;

    @FXML
    private ComboBox<TipoUsuario> cbTipoUsuario;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnActualizarUsuario;

    @FXML
    private Button btnAgregarUsuario;

    @FXML
    private TableView<Usuario> tblListUsuarios;

    @FXML
    private TableColumn<Usuario, String> tbcNombre;

    @FXML
    private TableColumn<Usuario, String> tbcIdentificacion;

    @FXML
    private TableColumn<Usuario, String> tbcTipoUsuario;

    @FXML
    void onAgregarUsuario() {
        agregarUsuario();
    }

    @FXML
    void onActualizarUsuario() {
        actualizarUsuario();
    }

    @FXML
    void onLimpiar() {
        limpiarSeleccion();
    }

    @FXML
    void onEliminar() {
        eliminarUsuario();
    }

    @FXML
    void initialize() {
        cbTipoUsuario.setItems(FXCollections.observableArrayList(TipoUsuario.values()));
    }

    private void initView() {
        initDataBinding();
        obtenerUsuarios();
        tblListUsuarios.setItems(listUsuarios);
        listenerSelection();
    }

    private void initDataBinding() {
        tbcIdentificacion.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getIdentificacion()));

        tbcNombre.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNombre()));

        tbcTipoUsuario.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTipoUsuario().toString()));
    }

    private void obtenerUsuarios() {
        listUsuarios.clear();
        listUsuarios.addAll(usuarioController.obtenerListaUsuarios());
    }

    private void listenerSelection() {
        tblListUsuarios.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    selectedUsuario = newSelection;
                    mostrarInformacionUsuario(selectedUsuario);
                }
        );
    }

    private void mostrarInformacionUsuario(Usuario usuario) {
        if (usuario != null) {
            txtIdentificacion.setText(usuario.getIdentificacion());
            txtNombre.setText(usuario.getNombre());
            cbTipoUsuario.setValue(usuario.getTipoUsuario());
        }
    }

    private void agregarUsuario() {
        Usuario usuario = buildUsuario();

        String result = usuarioController.registrarUsuario(usuario);

        if (result.contains("éxitosamente")) {
            listUsuarios.add(usuario);
            limpiarCamposUsuario();
        }

        mostrarMensaje(result);
    }

    private Usuario buildUsuario() {
        return new Usuario(
                txtNombre.getText(),
                txtIdentificacion.getText(),
                cbTipoUsuario.getValue()
        );
    }

    private void eliminarUsuario() {
        if (selectedUsuario != null) {
            String result = usuarioController.eliminarUsuario(selectedUsuario.getIdentificacion());

            if (result.equals("Usuario eliminado correctamente")) {
                listUsuarios.remove(selectedUsuario);
                limpiarCamposUsuario();
                limpiarSeleccion();
            }

            mostrarMensaje(result);
        } else {
            mostrarMensaje("Debe seleccionar un usuario para eliminar.");
        }
    }

    private void actualizarUsuario() {
        if (selectedUsuario != null) {
            Usuario usuarioActualizado = buildUsuario();

            String result = usuarioController.actualizarUsuario(usuarioActualizado);

            if (result.contains("correctamente")) {
                int index = listUsuarios.indexOf(selectedUsuario);

                if (index >= 0) {
                    listUsuarios.set(index, usuarioActualizado);
                }

                tblListUsuarios.refresh();
                limpiarSeleccion();
                limpiarCamposUsuario();
            }

            mostrarMensaje(result);
        } else {
            mostrarMensaje("Debe seleccionar un usuario para actualizar.");
        }
    }

    private void limpiarSeleccion() {
        tblListUsuarios.getSelectionModel().clearSelection();
        selectedUsuario = null;
        limpiarCamposUsuario();
    }

    private void limpiarCamposUsuario() {
        txtIdentificacion.clear();
        txtNombre.clear();
        cbTipoUsuario.setValue(null);
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
        this.usuarioController = new UsuarioController(app.universidad);
        initView();
    }
}
