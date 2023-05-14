package org.beru.server.beruserver.model.login;

import org.beru.server.beruserver.model.Json;
import org.beru.server.beruserver.model.SSH;
import org.beru.server.beruserver.model.UserInfo;
import org.beru.server.beruserver.resources.Active;

import java.io.IOException;

public class LoginDataSource {
    public Result<User> login(String username, String password,String host, int port,int type) {

        try {
            // TODO: handle loggedInUser authentication
            User user = new User(1L, username,host,port,type);
            new SSH(username,password,host,port).connect();
            Json json = new Json();
            json.save(user);
            Active.user = user;
            return new Result.Success<>(user);
        } catch (Exception e) {
            return new Result.Error(new IOException(e.getLocalizedMessage(), e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
