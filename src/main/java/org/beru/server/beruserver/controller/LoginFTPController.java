package org.beru.server.beruserver.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import org.beru.server.beruserver.model.Encrypt;
import org.beru.server.beruserver.model.LoginLocalData;
import org.beru.server.beruserver.model.LoginRemoteData;
import org.beru.server.beruserver.model.Observable;
import org.beru.server.beruserver.model.login.User;
import org.beru.server.beruserver.model.login.LoginFormState;
import org.beru.server.beruserver.model.login.LoginViewModel;
import org.beru.server.beruserver.resources.Active;
import org.beru.server.beruserver.view.ui.Toast;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginFTPController implements Initializable, Observable {
    public MFXComboBox<String> userText;
    public MFXTextField passwordText;
    public MFXTextField hostText;
    public MFXTextField portText;
    public Line passwordIndicator;
    public Line portIndicator;
    public Line userIndicator;
    public Line hostIndicator;
    public MFXButton loginButton;
    LoginViewModel loginViewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginViewModel = new LoginViewModel();

        userText.textProperty().addListener((obs, oldText, newText) -> notifyObserver());
        passwordText.textProperty().addListener((obs, oldText, newText) -> notifyObserver());
        hostText.textProperty().addListener((obs, oldText, newText) -> notifyObserver());
        portText.textProperty().addListener((obs, oldText, newText) -> notifyObserver());

        loadSavedUsers();
    }

    @Override
    public void notifyObserver() {
        loginViewModel.update(userText.getText(), passwordText.getText(), hostText.getText(), portText.getText());
        LoginFormState loginFormState = loginViewModel.getLoginFormState();

        if(loginFormState == null)
            return;
        loginButton.setDisable(!loginFormState.isDataValid());
        if(loginFormState.getUsernameError()!=null) {
            userIndicator.setStroke(Color.RED);
        }else{
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
            String username = (userText.getFloatingText().equals("Username:") ? userText.getText() : userText.getFloatingText());
            int connectionType = 0;
            Active.user = loginViewModel.login(username, passwordText.getText(), hostText.getText(), Integer.parseInt(portText.getText()), connectionType, "");
            Active.activeStage.close();
        }catch (Exception e) {
            e.printStackTrace();
            Toast.makeError(Active.activeStage, "Cannot connect with specific server", e.getLocalizedMessage(), Toast.ToastDuration.MEDIUM_DURATION, Toast.FadeDelay.SHORT_DURATION, Toast.FadeDelay.SHORT_DURATION);
        }
    }
    private void loadSavedUsers(){
        LoginRemoteData loginRemoteData = null;
        LoginLocalData loginLocalData = new LoginLocalData();
        if(loginRemoteData!=null)
            return;
        else if(loginLocalData.load() != null){
            User user = loginLocalData.load();
            loadVIewData(user);
        }
    }
    public void loadVIewData(User user){
        userText.setText(user.getName());
        hostText.setText(user.getHost());
        portText.setText(user.getPort()+"");
        passwordText.setText(Encrypt.decrypt(user.getPassword()));
    }
    @FXML
    public void selectionExists(ActionEvent actionEvent) {
        User userSelected = null;
        userText.setFloatingText(userSelected.getName());
        hostText.setText(userSelected.getHost());
        portText.setText(String.valueOf(userSelected.getPort()));
        passwordText.setText(Encrypt.decrypt(userSelected.getPassword()));
    }
}
