package org.beru.server.beruserver.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.text.Text;
import org.beru.server.beruserver.model.db.SqlManager;
import org.beru.server.beruserver.model.db.Test2;
import org.beru.server.beruserver.model.db.model.Column;
import org.beru.server.beruserver.model.db.model.DB;
import org.beru.server.beruserver.model.db.model.Table;
import org.beru.server.beruserver.resources.Active;
import org.beru.server.beruserver.view.ui.Actions;
import org.beru.server.beruserver.view.ui.Context;
import org.beru.server.beruserver.view.ui.Toast;
import org.beru.server.beruserver.view.ui.control.TreeItemDB;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.ResourceBundle;

public class DataBaseController implements Initializable{
    public TreeView<String> navigatorDB;
    public TabPane tabPane;
    public void initialize() {
        try{
//            Test2 test = new Test2();
//            this.manager = manager;
//            manager.read(1, test);
//            manager.create(test);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeError(Active.activeStage, "¡Error!", e.getLocalizedMessage(), Toast.ToastDuration.LONG_DURATION, Toast.FadeDelay.MEDIUM_DURATION, Toast.FadeDelay.SHORT_DURATION);
        }
    }
    public void loadNavigator(SqlManager manager){
        TreeItemDB<String> mysql = new TreeItemDB<>("Mysql");
        manager.getDatabases().forEach(db -> {
            FontIcon dbIcon = new FontIcon("mdi2d-database:22:BLUE");
            TreeItemDB<DB> dbItem = new TreeItemDB<>(db);
            dbItem.setGraphic(dbIcon);
            db.getTables().forEach(table -> {
                FontIcon tableIcon = new FontIcon("mdi2t-table:22:BLUE");
                TreeItemDB<Table> tableItem = new TreeItemDB<>(table);
                tableItem.setGraphic(tableIcon);
                table.getColumns().forEach(column -> {
                    TreeItemDB<Column> columnItem = new TreeItemDB<>(column);
                    FontIcon columnIcon = new FontIcon("mdi2t-table-column:22:BLUE");
                    columnItem.setGraphic(columnIcon);
                    tableItem.getChildren().add(columnItem);
                });
                dbItem.getChildren().add(tableItem);
            });
            mysql.getChildren().add(dbItem);
        });
        root.getChildren().add(mysql);
        navigatorDB.setOnMouseClicked((event -> {
            try{
                Node node = event.getPickResult().getIntersectedNode();
                if(node instanceof Text || (node instanceof TreeCell<?> && ((TreeCell<?>) node).getText() != null)) {
                    TreeItemDB<?> item = (TreeItemDB<?>) navigatorDB.getSelectionModel().getSelectedItem();
                    if(item.get() instanceof DB db){
                        navigatorDB.setContextMenu(Context.dataBase(db));
                    }else if(item.get() instanceof Table table){
                        navigatorDB.setContextMenu(Context.table(table));
                    }else if(item.get() instanceof Column column){
                        navigatorDB.setContextMenu(Context.column(column));
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }));
    }

    public void newConnection(ActionEvent actionEvent) {
        Actions.openDBLogin();
    }
    private TreeItem<String> root;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Active.dataBaseController = this;
        Active.tabPane = tabPane;

        root = new TreeItem<String>("DataBases");
        navigatorDB.setRoot(root);
    }
}
