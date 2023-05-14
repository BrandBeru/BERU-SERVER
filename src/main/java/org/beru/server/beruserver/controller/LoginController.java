package org.beru.server.beruserver.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import org.beru.server.beruserver.Sftp;
import org.beru.server.beruserver.model.Observable;
import org.beru.server.beruserver.model.login.Result;
import org.beru.server.beruserver.model.login.User;
import org.beru.server.beruserver.model.login.LoginFormState;
import org.beru.server.beruserver.model.login.LoginViewModel;
import org.beru.server.beruserver.resources.Active;
import org.beru.server.beruserver.resources.C;
import org.beru.server.beruserver.resources.R;
import org.beru.server.beruserver.view.ui.Toast;
import org.controlsfx.control.textfield.AutoCompletionBinding;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

public class LoginController implements Initializable, Observable {
    public MFXComboBox userText;
    public MFXTextField passwordText;
    public MFXTextField hostText;
    public MFXTextField portText;
    public Line passwordIndicator;
    public Line portIndicator;
    public Line userIndicator;
    public Line hostIndicator;
    public MFXButton loginButton;
    public MFXComboBox connectionTypeCB;
    LoginViewModel loginViewModel;

    public static LoginController instance;
    AutoCompletionBinding<String> autoCompletionBinding;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        loginViewModel = new LoginViewModel();

        connectionTypeCB.setItems(FXCollections.observableArrayList(R.array.connection_type));
        ArrayList<String> datas = new ArrayList<>();
        System.out.println(C.users.size());
        for(Map.Entry<String, User> entry:C.users.entrySet()){
            datas.add(entry.getKey());
            System.out.println(entry.getValue().toString());
        }
        userText.setItems(FXCollections.observableArrayList(datas));

        userText.textProperty().addListener((obs, oldText, newText) -> notifyObserver());
        passwordText.textProperty().addListener((obs, oldText, newText) -> notifyObserver());
        hostText.textProperty().addListener((obs, oldText, newText) -> notifyObserver());
        portText.textProperty().addListener((obs, oldText, newText) -> notifyObserver());
    }

    @Override
    public void notifyObserver() {
        loginViewModel.update(userText.getText(), passwordText.getText(), hostText.getText(), portText.getText());
        LoginFormState loginFormState = loginViewModel.getLoginFormState();

        if(loginFormState == null)
            return;
        loginButton.setDisable(!loginFormState.isDataValid());
        if(loginFormState.getUsernameError()!=null) {
            userText.setFloatingText(loginFormState.getUsernameError());
            userIndicator.setStroke(Color.RED);
        }else{
            userText.setFloatingText("Username");
            userIndicator.setStroke(Color.BLACK);
        }
        if(loginFormState.getPasswordError()!=null) {
            passwordText.setFloatingText(loginFormState.getPasswordError());
            passwordIndicator.setStroke(Color.RED);
        }else{
            passwordText.setFloatingText("Password");
            passwordIndicator.setStroke(Color.BLACK);
        }
        if(loginFormState.getHostError()!=null) {
            hostText.setFloatingText(loginFormState.getHostError());
            hostIndicator.setStroke(Color.RED);
        }else{
            hostText.setFloatingText("Host");
            hostIndicator.setStroke(Color.BLACK);
        }
        if(loginFormState.getPortError()!=null) {
            portText.setFloatingText(loginFormState.getPortError());
            portIndicator.setStroke(Color.RED);
        }else{
            portText.setFloatingText("Port");
            portIndicator.setStroke(Color.BLACK);
        }
    }

    public void login(ActionEvent actionEvent) throws Exception {
        try{
            User user = loginViewModel.login(userText.getText(), passwordText.getText(), hostText.getText(), Integer.parseInt(portText.getText()), connectionTypeCB.getSelectedIndex());
            if(user!=null) {
                Active.activeStage.close();
                new Sftp().start();
            }
        }catch (Exception e) {
            Toast.makeError(null, "Cannot connect with specific server", e.getLocalizedMessage(), Toast.ToastDuration.MEDIUM_DURATION, Toast.FadeDelay.SHORT_DURATION, Toast.FadeDelay.SHORT_DURATION);
        }
    }

    public void selectionExists(ActionEvent actionEvent) {
        User userSelected = C.users.get(userText.getSelectedItem().toString());
        hostText.setText(userSelected.getHost());
        portText.setText(String.valueOf(userSelected.getPort()));
        connectionTypeCB.getSelectionModel().selectIndex(userSelected.getType());
    }
}
