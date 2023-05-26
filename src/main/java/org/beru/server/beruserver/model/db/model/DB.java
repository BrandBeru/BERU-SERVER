package org.beru.server.beruserver.model.db.model;

import java.util.ArrayList;
import java.util.List;

public class DB {
    private String name;
    private String charset;
    private String collation;
    private String sqlPath;
    private String size;
    private List<Table> tables = new ArrayList<>();

    public DB(String name, String charset, String collation, String sqlPath, String size, List<Table> tables) {
        this.name = name;
        this.charset = charset;
        this.collation = collation;
        this.sqlPath = sqlPath;
        this.size = size;
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

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getCollation() {
        return collation;
    }

    public void setCollation(String collation) {
        this.collation = collation;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSqlPath() {
        return sqlPath;
    }

    public void setSqlPath(String sqlPath) {
        this.sqlPath = sqlPath;
    }

    @Override
    public String toString() {
        return "DB{" +
                "name='" + name + '\'' +
                ", charset='" + charset + '\'' +
                ", collation='" + collation + '\'' +
                ", sqlPath='" + sqlPath + '\'' +
                ", size='" + size + '\'' +
                ", tables=" + tables +
                '}';
    }

    public void addTable(Table table){
        tables.add(table);
    }
    public void removeTable(Table table){
        tables.remove(table);
    }
}
