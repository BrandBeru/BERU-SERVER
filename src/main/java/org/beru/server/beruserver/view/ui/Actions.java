package org.beru.server.beruserver.view.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import org.beru.server.beruserver.Main;
import org.beru.server.beruserver.model.db.model.Column;
import org.beru.server.beruserver.model.db.model.DB;
import org.beru.server.beruserver.model.db.model.Table;
import org.beru.server.beruserver.resources.Active;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;

public class Actions {
    public static void openDatabase(DB database){
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/db/DBInfo.fxml"));
        FontIcon icon = new FontIcon("mdi2d-database:20");
        Tab tab = new Tab(database.getName(), icon);
        loader(tab, loader);
    }
    public static void openTable(Table table){
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/db/TableInfo.fxml"));
        FontIcon icon = new FontIcon("mdi2t-table:20");
        Tab tab = new Tab(table.getName(), icon);
        loader(tab, loader);
    }
    public static void openColumn(Column column){
        System.out.println("Open: " + column);
    }
    public static void loader(Tab tab, FXMLLoader loader){
        TabPane tabPane = Active.dataBaseController.tabPane;
        try {
            AnchorPane anchorPane = loader.load();
            anchorPane.setPrefSize(tabPane.getWidth(), tabPane.getHeight());
            tab.setContent(anchorPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        tabPane.getTabs().add(tab);
    }
}
