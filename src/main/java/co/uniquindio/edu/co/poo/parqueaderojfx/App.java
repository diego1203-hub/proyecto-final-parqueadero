package co.uniquindio.edu.co.poo.parqueaderojfx;

import co.uniquindio.edu.co.poo.parqueaderojfx.viewController.VehiculoViewController;
import co.uniquindio.edu.co.poo.parqueaderojfx.viewController.UsuarioViewController;
import co.uniquindio.edu.co.poo.parqueaderojfx.viewController.PrimaryViewController;
import co.uniquindio.edu.co.poo.parqueaderojfx.model.Universidad;
import co.uniquindio.edu.co.poo.parqueaderojfx.model.TipoUsuario;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private Stage primaryStage;
    public static Universidad universidad = new Universidad("UQ", 123456, "Calle 1");

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Gestión de Parqueadero");
        inicializarData();
        openViewPrincipal();
    }

    private void openViewPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("primary.fxml"));
            VBox rootLayout = (VBox) loader.load();
            PrimaryViewController primaryController = loader.getController();
            primaryController.setApp(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openOpcionesParqueadero() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("OpcionesParqueadero.fxml"));
            AnchorPane rootLayout = (AnchorPane) loader.load();
            OpcionesParqueaderoViewController opcionesViewController = loader.getController();
            opcionesViewController.setApp(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openVehiculoView() {

        try {

            FXMLLoader loader = new FXMLLoader(
                    App.class.getResource("Vehiculo.fxml")
            );

            Scene scene = new Scene(loader.load());

            VehiculoViewController controller =
                    loader.getController();

            controller.setApp(this);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Gestión Vehículos");
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openUsuarioView() {

        try {

            FXMLLoader loader = new FXMLLoader(
                    App.class.getResource("Usuario.fxml")
            );

            Scene scene = new Scene(loader.load());

            UsuarioViewController controller =
                    loader.getController();

            controller.setApp(this);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Gestión Usuarios");
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openTarifaView() {

        try {

            FXMLLoader loader = new FXMLLoader(
                    App.class.getResource("Tarifa.fxml")
            );

            Scene scene = new Scene(loader.load());

            TarifaViewController controller =
                    loader.getController();

            controller.setApp(this);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Gestión Tarifas");
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openEspacioParqueaderoView() {

        try {

            FXMLLoader loader = new FXMLLoader(
                    App.class.getResource("EspacioParqueadero.fxml")
            );

            Scene scene = new Scene(loader.load());

            EspacioParqueaderoViewController controller =
                    loader.getController();

            controller.setApp(this);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Espacios Parqueadero");
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openUniversidadView() {

        try {

            FXMLLoader loader = new FXMLLoader(
                    App.class.getResource("primary.fxml")
            );

            Scene scene = new Scene(loader.load());

            PrimaryViewController controller =
                    loader.getController();

            controller.setApp(this);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Reportes");
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openPrimaryView() {

        primaryStage.close();
    }

    private void inicializarData() {
        universidad.registrarUsuario("Juan", "12233", TipoUsuario.ESTUDIANTE);
    }

    public static void main(String[] args) {
        launch();
    }
}