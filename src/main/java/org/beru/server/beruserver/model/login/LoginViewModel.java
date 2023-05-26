package org.beru.server.beruserver.model.login;

import org.beru.server.beruserver.model.Observer;
import org.beru.server.beruserver.resources.R;

public class LoginViewModel implements Observer {
    LoginRepository loginRepository;
    LoginFormState loginFormState;
    public LoginViewModel(){
        LoginDataSource dataSource = new LoginDataSource();
        loginRepository = new LoginRepository(dataSource);
        loginFormState = new LoginFormState(R.string.invalid_username,R.string.invalid_password,R.string.invalid_host,R.string.invalid_port);
    }
    public boolean isUsernameValid(String username){
        if(username==null){
            return false;
        }
        if(username.contains("@")){
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        }else{
            return !username.trim().isEmpty();
        }
    }
    public boolean isHostValid(String host){
            if(host==null)
                return false;
            if(host.contains("."))
                return Patterns.IP_ADDRESS.matcher(host).matches();
            else
                return false;
    }
    public boolean isPortValid(String port){
        try{
            int p = Integer.parseInt(port);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }
    public boolean isPasswordValid(String password){
        return password != null && password.trim().length() > 3;
    }

    public User login(String username, String password,String host, int port,int type, String db){
        Result<User> result = loginRepository.login(username, password,host,port,type, db);

        if(result instanceof Result.Success<?>){
            return ((Result.Success<User>)result).getData();
        }
        throw new RuntimeException();
    }

    @Override
    public void update(String username, String password, String host, String port) {
        if(!isUsernameValid(username))
            loginFormState = new LoginFormState(R.string.invalid_username, null,null,null);
        else if(!isPasswordValid(password))
            loginFormState = new LoginFormState(null, R.string.invalid_password,null,null);
        else if (!isHostValid(host))
            loginFormState = new LoginFormState(null, null,R.string.invalid_host,null);
        else if(!isPortValid(port)){
            loginFormState = new LoginFormState(null, null,null,R.string.invalid_port);
        }
        else {
            loginFormState = new LoginFormState(true);
        }
    }

    public LoginRepository getLoginRepository() {
        return loginRepository;
    }

    public LoginFormState getLoginFormState() {
        return loginFormState;
    }
}
