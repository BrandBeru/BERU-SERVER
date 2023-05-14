package org.beru.server.beruserver.model.db.model;

import java.util.ArrayList;
import java.util.List;

public class DB {
    private String name;
    private List<Table> tables = new ArrayList<>();

    public DB(String name, List<Table> tables) {
        this.name = name;
        this.tables = tables;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    @Override
    public String toString() {
        return "DB[" +
                "name:'" + name + '\'' +
                ", tables:" + tables +
                ']';
    }

    public void addTable(Table table){
        tables.add(table);
    }
    public void removeTable(Table table){
        tables.remove(table);
    }
}
