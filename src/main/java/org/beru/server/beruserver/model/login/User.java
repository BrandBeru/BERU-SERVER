package org.beru.server.beruserver.model.login;

import org.beru.server.beruserver.model.UserInfo;

public class User {
    private String name;
    private String password;
    private String host;
    private int port;
    private String db;
    private int type;

    public User(String name, String password, String host, int port,int type, String db) {
        this.name = name;
        this.password = password;
        this.host = host;
        this.port = port;
        this.type = type;
        this.db = db;
    }
    public User() {
    }

    public String getName() {
        return name;
    }
    public String getHost() {
        return host;
    }
    public int getPort() {
        return port;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public void setPort(int port) {
        this.port = port;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", db='" + db + '\'' +
                ", type=" + type +
                '}';
    }
}
