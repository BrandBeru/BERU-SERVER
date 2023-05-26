package org.beru.server.beruserver.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.beru.server.beruserver.model.login.User;
import org.beru.server.beruserver.resources.Files;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class LoginLocalData {
    private ObjectMapper mapper = new ObjectMapper();
    TypeFactory typeFactory = mapper.getTypeFactory();

    public void save(User user){
        File file = new File(Files.CACHE);
        try {
            mapper.writeValue(file, user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public User load(){
        File file = new File(Files.CACHE);
        try {
            return mapper.readValue(file, User.class);
        } catch (IOException e) {
            System.out.println("Is null");
        }
        return null;

    }
}
