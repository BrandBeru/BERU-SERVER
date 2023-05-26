package org.beru.server.beruserver.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import org.beru.server.beruserver.resources.Active;
import org.beru.server.beruserver.view.ui.Actions;

import java.net.URL;
import java.util.ResourceBundle;

public class ChooserController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void sshAction(ActionEvent actionEvent) {
        Actions.loginSSH();
    }

    public void ftpAction(ActionEvent actionEvent) {
    }

    public void explorerAction(ActionEvent actionEvent) {
    }

    public void webAction(ActionEvent actionEvent) {
    }

    public void dbAction(ActionEvent actionEvent) {
        Actions.loginDataBase();
    }
}
