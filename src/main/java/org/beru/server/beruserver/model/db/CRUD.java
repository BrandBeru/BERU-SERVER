package org.beru.server.beruserver.model.db;

public interface CRUD {
    void create(Object obj);
    Object read(int id, Object type);
    void update();
    void delete();
}
