package org.beru.server.beruserver.model.login;

import org.beru.server.beruserver.model.UserInfo;

public class User {
    private Long id;
    private String name;
    private String host;
    private int port;
    private int type;

    public User(Long id, String name, String host, int port,int type) {
        this.id = id;
        this.name = name;
        this.host = host;
        this.port = port;
        this.type = type;
    }
    public User() {
    }

    public Long getId() {
        return id;
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
    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "User[" +
                "id:" + id +
                ", name:'" + name + '\'' +
                ", host:'" + host + '\'' +
                ", port:" + port +
                ", type:" + type +
                ']';
    }
}
