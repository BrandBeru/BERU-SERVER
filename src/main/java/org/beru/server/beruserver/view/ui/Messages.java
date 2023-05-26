package org.beru.server.beruserver.view.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

public class Messages {
    public boolean confirm(String context){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, context, ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        return alert.getResult() == ButtonType.YES;
    }
    public String input(String context, String header){
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setContentText(context);
        inputDialog.setHeaderText(header);

        inputDialog.showAndWait();

        return inputDialog.getResult();
    }
}
