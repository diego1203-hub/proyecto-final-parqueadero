package co.uniquindio.edu.co.poo.parqueaderojfx.viewController;

import co.uniquindio.edu.co.poo.parqueaderojfx.App;
import co.uniquindio.edu.co.poo.parqueaderojfx.controller.OpcionesParqueaderoController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class OpcionesParqueaderoViewController {

    private App app;

    private OpcionesParqueaderoController opcionesParqueaderoController;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label welcomeText;

    @FXML
    private Button btnInterfazVehiculo;

    @FXML
    private Button btnInterfazUsuario;

    @FXML
    private Button btnInterfazTarifa;

    @FXML
    private Button btnInterfazEspacioParqueadero;

    @FXML
    private Button btnInterfazUniversidad;

    @FXML
    private Button btnInterfazPrimary;

    @FXML
    void openInterfazVehiculo() {

        opcionesParqueaderoController.abrirVehiculo();
    }

    @FXML
    void openInterfazUsuario() {

        opcionesParqueaderoController.abrirUsuario();
    }

    @FXML
    void openInterfazTarifa() {

        opcionesParqueaderoController.abrirTarifa();
    }

    @FXML
    void openInterfazEspacioParqueadero() {

        opcionesParqueaderoController.abrirEspacioParqueadero();
    }

    @FXML
    void openInterfazuniversidad() {

        opcionesParqueaderoController.abrirReportes();
    }

    @FXML
    void openInterfazPrimary() {

        opcionesParqueaderoController.salir();
    }

    @FXML
    void initialize() {

    }

    public void setApp(App app) {

        this.app = app;

        opcionesParqueaderoController =
                new OpcionesParqueaderoController(app);
    }
}
