package org.beru.server.beruserver.model;

@FunctionalInterface
public interface Observer {
    void update(String username, String password,String host, String port);
}
