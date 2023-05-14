module org.beru.server.beruserver {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.jfxtras.styles.jmetro;
    requires MaterialFX;
    requires jsch;
    requires mysql.connector.j;
    requires java.sql;
    requires com.fasterxml.jackson.databind;

    opens org.beru.server.beruserver to javafx.fxml;

    exports org.beru.server.beruserver;
    exports org.beru.server.beruserver.controller;
    exports org.beru.server.beruserver.model;
    exports org.beru.server.beruserver.model.login;
    exports org.beru.server.beruserver.view.ui;
    exports org.beru.server.beruserver.view.files;

    opens org.beru.server.beruserver.model;
    exports org.beru.server.beruserver.model.db;
    opens org.beru.server.beruserver.model.db;
    exports org.beru.server.beruserver.model.db.info;
    exports org.beru.server.beruserver.model.db.model;
    opens org.beru.server.beruserver.model.db.info;
    exports org.beru.server.beruserver.controller.db;
}
