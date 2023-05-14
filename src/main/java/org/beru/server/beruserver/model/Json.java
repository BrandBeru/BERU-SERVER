package org.beru.server.beruserver.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.beru.server.beruserver.model.login.User;
import org.beru.server.beruserver.resources.C;
import org.beru.server.beruserver.resources.Files;
import org.beru.server.beruserver.resources.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Json {
    private ObjectMapper mapper = new ObjectMapper();
    TypeFactory typeFactory = mapper.getTypeFactory();

    public void save(User user){
        File file = new File(Files.CACHE);
        try {
            List<User> users = mapper.readValue(file, typeFactory.constructCollectionType(List.class, User.class));
            users.forEach((u) -> {
                if(!u.toString().equals(user.toString())) {
                    users.add(user);
                    try {
                        mapper.writeValue(file, users);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            List<User> userList = new ArrayList<>();
            userList.add(user);
            mapper.writeValue(file, userList);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    public void load(){
        File file = new File(Files.CACHE);
        try {
            List<User> users = mapper.readValue(file, typeFactory.constructCollectionType(List.class, User.class));
            users.forEach((user -> {
                System.out.println("init");
                C.users.put(user.getName(), user);
            }));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
