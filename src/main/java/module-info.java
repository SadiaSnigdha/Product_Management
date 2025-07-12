module com.example.product_management {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires jbcrypt;
    requires java.desktop;
    opens com.example.product_management.controller to javafx.fxml;
    exports com.example.product_management.controller;
    opens com.example.product_management to javafx.fxml;
    exports com.example.product_management;
}