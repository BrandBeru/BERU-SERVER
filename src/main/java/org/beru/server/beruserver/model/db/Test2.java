package org.beru.server.beruserver.model.db;

import org.beru.server.beruserver.model.db.info.Id;
import org.beru.server.beruserver.model.db.info.Table;

@Table(name = "beru_test")
public class Test2 {
    @Id
    private int id;
    private String name;
    private String cedula;
    private String direccion;
}
