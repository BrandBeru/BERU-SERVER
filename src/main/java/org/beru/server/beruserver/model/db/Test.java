package org.beru.server.beruserver.model.db;

import org.beru.server.beruserver.model.db.info.*;

@Table(name = "beru")
public class Test {
    @Id
    private int id;
    @Value(name = "name")
    private String nombre;
    @Value(name = "last_name")
    private String apellido;
    @Value(name = "phone")
    private String numero;

    public Test(int id, String name, String lastName, String phoneNumber) {
        this.id = id;
        this.nombre = name;
        this.apellido = lastName;
        this.numero = phoneNumber;
    }

    public Test() {
    }

}
