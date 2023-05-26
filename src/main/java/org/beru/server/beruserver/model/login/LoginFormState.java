package org.beru.server.beruserver.model.login;

public class LoginFormState {
    private String usernameError;
    private String passwordError;
    private String hostError;
    private String portError;
    private boolean isDataValid;

    LoginFormState( String usernameError, String passwordError, String hostError, String portError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.hostError = hostError;
        this.portError = portError;
        this.isDataValid = false;
    }

    LoginFormState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.hostError = null;
        this.portError = null;
        this.isDataValid = isDataValid;
    }
    public String getUsernameError() {
        return usernameError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public String getHostError() {
        return hostError;
    }

    public String getPortError() {
        return portError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }
}
