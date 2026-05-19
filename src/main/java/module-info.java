module co.uniquindio.edu.co.poo.parqueaderojfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens co.uniquindio.edu.co.poo.parqueaderojfx  to javafx.fxml;
    exports co.uniquindio.edu.co.poo.parqueaderojfx ;
    exports co.uniquindio.edu.co.poo.parqueaderojfx.viewController;
    opens co.uniquindio.edu.co.poo.parqueaderojfx .viewController to javafx.fxml;
}