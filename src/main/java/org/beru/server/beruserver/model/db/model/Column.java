package org.beru.server.beruserver.model.db.model;

public class Column {
    private int id;
    private String name;
    private String dataType;
    private String nullable;
    private String extra;
    private String key;
    private String colDefault;

    public Column(int id, String name, String dataType, String nullable, String extra, String key, String colDefault) {
        this.id = id;
        this.name = name;
        this.dataType = dataType;
        this.nullable = nullable;
        this.extra = extra;
        this.key = key;
        this.colDefault = colDefault;
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

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getNullable() {
        return nullable;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getColDefault() {
        return colDefault;
    }

    public void setColDefault(String colDefault) {
        this.colDefault = colDefault;
    }

    @Override
    public String toString() {
        return "Column{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dataType='" + dataType + '\'' +
                ", nullable='" + nullable + '\'' +
                ", extra='" + extra + '\'' +
                ", key='" + key + '\'' +
                ", colDefault=" + colDefault +
                '}';
    }
}
