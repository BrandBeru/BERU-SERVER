package org.beru.server.beruserver.view.ui;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import org.beru.server.beruserver.model.db.model.Column;
import org.beru.server.beruserver.model.db.model.DB;
import org.beru.server.beruserver.model.db.model.Table;
import org.beru.server.beruserver.model.file.FileType;

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
    public static ContextMenu file(FileType<?> fileType){
        ContextMenu cm = new ContextMenu();

        MenuItem newFile = new MenuItem("Create new File");
        MenuItem open = new MenuItem("Open");
        open.setOnAction((event -> Actions.openFileExplorer(fileType, false)));
        MenuItem openNew = new MenuItem("Open in new tab");
        openNew.setOnAction(event -> Actions.openFileExplorer(fileType, true));
        MenuItem rename = new MenuItem("Rename");
        rename.setOnAction((event -> Actions.rename(fileType)));

        Menu download = new Menu("Download in");
        MenuItem downloads = new MenuItem("Downloads");
        MenuItem documents = new MenuItem("Documents");
        MenuItem pictures = new MenuItem("Pictures");
        MenuItem specificPath = new MenuItem("Specific Path");
        download.getItems().addAll(downloads, documents, pictures, specificPath);

        MenuItem delete = new MenuItem("Delete");
        delete.setOnAction((event -> Actions.deleteFile(fileType)));

        if(fileType.isDirectory())
            cm.getItems().addAll(newFile, open, openNew, rename, delete);
        else
            cm.getItems().addAll(open, download, rename, delete);

        return cm;
    }
}
