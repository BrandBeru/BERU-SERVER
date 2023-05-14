package org.beru.server.beruserver.view.ui;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import org.beru.server.beruserver.model.db.model.Column;
import org.beru.server.beruserver.model.db.model.DB;
import org.beru.server.beruserver.model.db.model.Table;

public class Context {
    public static ContextMenu dataBase(DB db){
        ContextMenu cm = new ContextMenu();

        MenuItem open = new MenuItem("Open");
        open.setOnAction((actionEvent) -> Actions.openDatabase(db));

        Menu create = new Menu("Create");
        MenuItem cDB = new MenuItem("Create Database");
        MenuItem cTable = new MenuItem("Create Table");
        create.getItems().addAll(cTable, cDB);

        cm.getItems().addAll(open, create);

        return cm;
    }
    public static ContextMenu table(Table table){
        ContextMenu cm = new ContextMenu();

        MenuItem open = new MenuItem("Open");
        open.setOnAction((actionEvent) -> Actions.openTable(table));

        Menu create = new Menu("Create");
        MenuItem cTable = new MenuItem("Create Table");
        MenuItem cColumn = new MenuItem("Create Column");
        MenuItem cCons = new MenuItem("Create Constraint");
        MenuItem cFK = new MenuItem("Create Foreign Key");
        create.getItems().addAll(cTable, cColumn, cCons, cFK);

        cm.getItems().addAll(open, create);

        return cm;
    }
    public static ContextMenu column(Column column){
        ContextMenu cm = new ContextMenu();

        MenuItem open = new MenuItem("Open");
        open.setOnAction((actionEvent) -> Actions.openColumn(column));

        MenuItem create = new MenuItem("Create new Column");

        cm.getItems().addAll(open, create);

        return cm;
    }
}
