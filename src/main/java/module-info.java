module com.example.methodhopfilda {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.methodhopfilda to javafx.fxml;
    exports com.example.methodhopfilda;
}