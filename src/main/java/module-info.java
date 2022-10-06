module com.example.hotelone {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
//    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.hotelone to javafx.fxml;
    exports com.hotelone;
    exports com.hotelone.controllers;
    exports com.hotelone.entities;
    exports com.hotelone.enums;
    opens com.hotelone.controllers to javafx.fxml;

    requires java.sql;
}