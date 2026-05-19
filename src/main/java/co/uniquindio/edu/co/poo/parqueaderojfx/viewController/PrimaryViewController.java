package co.uniquindio.edu.co.poo.parqueaderojfx.viewController;

import java.net.URL;
import java.util.ResourceBundle;

import co.uniquindio.edu.co.poo.parqueaderojfx.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PrimaryViewController {
    @FXML
    private ResourceBundle resources;

    private App app;
    @FXML
    private URL location;

    @FXML
    private Button btnOpciones;

    @FXML
    private Button btnUsuarios;

    @FXML
    private Button btnVehiculos;

    @FXML
    void onOpenOpcionesParqueadero() {
        app.openOpcionesParqueadero();
    }

    @FXML
    void onOpenUsuarios() {
        app.openCrudUsuario();
    }

    @FXML
    void onOpenVehiculos() {
        app.openCrudVehiculo();
    }

    public void setApp(App app) {
        this.app = app;
    }

    @FXML
    void initialize() {

    }
}