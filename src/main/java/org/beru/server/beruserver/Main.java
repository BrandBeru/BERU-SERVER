package org.beru.server.beruserver;

import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.beru.server.beruserver.model.Json;
import org.beru.server.beruserver.resources.Active;
import org.beru.server.beruserver.resources.Files;
import org.beru.server.beruserver.resources.Path;
import org.beru.server.beruserver.resources.R;
import org.beru.server.beruserver.view.ui.Toast;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Active.r = new R();
        initializer();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/DataBaseManager.fxml"));
        Active.activeStage = stage;
        Scene scene = new Scene(fxmlLoader.load());
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
        stage.setTitle(R.string.application_name);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private void initializer(){
        try {
            Path.mkdirs();
            Files.files();
            new Json().load();
        } catch (IOException e) {
            Toast.makeText(Active.activeStage, "Cannot create config files", 2000, 500,300);
        }
    }
}