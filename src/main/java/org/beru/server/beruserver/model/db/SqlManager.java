package org.beru.server.beruserver.model.db;

import javafx.scene.control.Tab;
import org.beru.server.beruserver.model.db.info.Id;
import org.beru.server.beruserver.model.db.info.Table;
import org.beru.server.beruserver.model.db.info.Value;
import org.beru.server.beruserver.model.db.model.Column;
import org.beru.server.beruserver.model.db.model.DB;
import org.beru.server.beruserver.resources.C;
import org.beru.server.beruserver.resources.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlManager implements CRUD{
    Connection conn;
    private String table;
    private Map<String, Object> datas = new HashMap<>();
    private Object info;

    public SqlManager(){
        initialization();
    }
    @Override
    public void create(Object obj) {
        info = obj;
        annotation();
        System.out.println("Using: " + table);
        System.out.println("creating: ");
        datas.forEach((name, value) -> {
            System.out.println("Key: " + name + " | Value: "+value);});
    }

    @Override
    public Object read(int id, Object type) {
        info = type;
        annotation();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + table + " WHERE id=?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                for (Field field : info.getClass().getDeclaredFields()){
                    System.out.println(field.getName());
                    System.out.println("->" + rs.getObject("name"));
                    field.setAccessible(true);
                    if(field.isAnnotationPresent(Value.class)){
                        Value value = (Value) field.getAnnotation(Value.class);
                        field.set(info, rs.getObject(value.name()));
                    }else if(field.isAnnotationPresent(Id.class)){
                        field.set(info, rs.getInt(field.getName()));
                    }else{
                        field.set(info, rs.getObject(field.getName()));
                    }
                }
            }
            return info;
        } catch (SQLException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }

    public void annotation() {
        datas.clear();
        if(info.getClass().isAnnotationPresent(Table.class)){
            Table tbInfo = (Table) info.getClass().getAnnotation(Table.class);
            table = tbInfo.name();

            for(Field field : info.getClass().getDeclaredFields()){
                field.setAccessible(true);
                if(field.isAnnotationPresent(Id.class)){
                    try {
                        datas.put(field.getName(), field.get(info));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }else if(field.isAnnotationPresent(Value.class)){
                    try{
                        Value value = (Value) field.getAnnotation(Value.class);
                        datas.put(value.name(), field.get(info));
                    }catch (IllegalAccessException e){
                        e.printStackTrace();
                    }
                }else {
                    try {
                        datas.put(field.getName(), field.get(info));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        }
    }

    public List<DB> getDatabases(){
        List<DB> dbs = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SHOW DATABASES");
            while (rs.next()){
                String name = rs.getString("Database");
                DB database = new DB(name, getTables(name));
                dbs.add(database);
            }
            return dbs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<org.beru.server.beruserver.model.db.model.Table> getTables(String db){
        List<org.beru.server.beruserver.model.db.model.Table> tables = new ArrayList<>();
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA=?");
            stmt.setString(1, db);
            ResultSet rs = stmt.executeQuery();
            int id = 0;
            while (rs.next()){
                String tableName = rs.getString("TABLE_NAME");
                org.beru.server.beruserver.model.db.model.Table table = new org.beru.server.beruserver.model.db.model.Table(
                        id,
                        tableName,
                        rs.getString("ENGINE"),
                        rs.getInt("AUTO_INCREMENT"),
                        rs.getString("CHECKSUM"),
                        rs.getString("TABLE_COLLATION"),
                        rs.getString("TABLE_COMMENT"),
                        getColumns(tableName)
                );
                tables.add(table);
                id++;
            }
            return tables;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public List<Column> getColumns(String table){
        List<Column> columns = new ArrayList<>();
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME =?");
            stmt.setString(1, table);
            ResultSet rs = stmt.executeQuery();
            int id = 0;
            while (rs.next()){
                Column column = new Column(
                        id,
                        rs.getString("COLUMN_NAME"),
                        rs.getString("DATA_TYPE"),
                        rs.getString("IS_NULLABLE"),
                        rs.getString("EXTRA"),
                        rs.getString("COLUMN_KEY"),
                        rs.getString("COLUMN_DEFAULT")
                );
                columns.add(column);
                id++;
            }
            return columns;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void initialization(){
        try {
            Class.forName(R.properties.active_driver);

            conn = DriverManager.getConnection(R.properties.url, R.properties.username, R.properties.password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
