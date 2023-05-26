package org.beru.server.beruserver.model.db.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Table {
    private int id;
    private String name;
    private String engine;
    private int ai;
    private String length;
    private String charset;
    private String collation;
    private String description;
    private Date createTime;

    private List<Column> columns = new ArrayList<>();

    public Table(int id, String name, String engine, int ai, String length, String charset, String collation, String description, Date createTime, List<Column> columns) {
        this.id = id;
        this.name = name;
        this.engine = engine;
        this.ai = ai;
        this.length = length;
        this.charset = charset;
        this.collation = collation;
        this.description = description;
        this.createTime = createTime;
        this.columns = columns;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public int getAi() {
        return ai;
    }

    public void setAi(int ai) {
        this.ai = ai;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Table{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", engine='" + engine + '\'' +
                ", ai=" + ai +
                ", charset='" + charset + '\'' +
                ", collation='" + collation + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
