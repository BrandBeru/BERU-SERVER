package org.beru.server.beruserver.view.ui.control;

import javafx.scene.control.TreeItem;
import org.beru.server.beruserver.model.db.model.Column;
import org.beru.server.beruserver.model.db.model.DB;
import org.beru.server.beruserver.model.db.model.Table;

public class TreeItemDB<T> extends TreeItem<String> {
    private T data;
    public TreeItemDB(DB database){
        this.data = (T) database;
        this.setValue(database.getName());
    }
    public TreeItemDB(Table table){
        this.data = (T) table;
        this.setValue(table.getName());
    }public TreeItemDB(Column column){
        this.data = (T) column;
        this.setValue(column.getName() + " ("+column.getDataType()+")");
    }
    public TreeItemDB(String text){
        this.data = (T) text;
        this.setValue(text);
    }

    public T get() {
        return data;
    }

    public void set(T data) {
        this.data = data;
    }
}
