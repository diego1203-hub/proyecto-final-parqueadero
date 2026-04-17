module com.example.interfazjavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.interfazjavafx to javafx.fxml;
    exports com.example.interfazjavafx;
}