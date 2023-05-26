package org.beru.server.beruserver;

import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import org.beru.server.beruserver.resources.Active;
import org.beru.server.beruserver.resources.R;

public class StartView {
    public void start(String view) throws Exception {
        Stage stage = new Stage();
        Active.activeStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/"+view+".fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        new JMetro(scene.getRoot(), Style.DARK);
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
        stage.setTitle(R.string.application_name);
        stage.setScene(scene);
        stage.show();
    }
}
