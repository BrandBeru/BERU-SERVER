package org.beru.server.beruserver.model.login;

import org.beru.server.beruserver.model.Encrypt;
import org.beru.server.beruserver.model.LoginLocalData;
import org.beru.server.beruserver.model.SSH;
import org.beru.server.beruserver.model.db.SqlManager;
import org.beru.server.beruserver.resources.Active;
import org.beru.server.beruserver.view.ui.Actions;

import java.io.IOException;

public class LoginDataSource {
    public Result<User> login(String username, String password,String host, int port,int type, String db) {
        try {
            // TODO: handle loggedInUser authentication
            User user = new User(username, Encrypt.encrypt(password),host,port,type,db);
            connector(type, user);
            LoginLocalData json = new LoginLocalData();
            json.save(user);
            return new Result.Success<>(user);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result.Error(new IOException(e.getLocalizedMessage(), e));
        }
    }
    public void connector(int type, User user){
        switch (type){
            case 0:
                Active.ssh = new SSH(user.getName(), Encrypt.decrypt(user.getPassword()), user.getHost(), user.getPort());
                Active.sftpController.login();
                break;
            case 1:
                Active.dataBaseController.loadNavigator(new SqlManager(user, "mysql"));
                break;
            default:
                break;
        }
        System.out.println("Connected");
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
