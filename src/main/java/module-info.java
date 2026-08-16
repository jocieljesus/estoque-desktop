module com.jociel.estoque {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.materialdesign2;
    requires java.sql;


    opens com.jociel.estoque to javafx.fxml;
    opens com.jociel.estoque.controller to javafx.fxml;
    opens com.jociel.estoque.model to javafx.base;

    exports com.jociel.estoque;
}