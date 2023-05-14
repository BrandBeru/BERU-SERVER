package org.beru.server.beruserver.model;

import com.jcraft.jsch.*;
import org.beru.server.beruserver.resources.Path;

public class SSH {
    public Session session;
    public ChannelSftp sftp;
    public static SSH instance;
    private ProgressMonitor progressMonitor;

    public SSH(String user, String password, String host, int port){
        try {
            session = new JSch().getSession(user, host,port);
            session.setPassword(password);
            session.setConfig("PreferredAuthentications","password");
            session.setConfig("StrictHostKeyChecking", "no");

            instance = this;
        } catch (JSchException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean connect(){
        try {
            session.connect();
            sftp = (ChannelSftp) session.openChannel("sftp");
            sftp.connect();
            Path.REMOTE.append(sftp.getHome());
        } catch (JSchException | SftpException e) {
            throw new RuntimeException(e);
        }
        return session.isConnected();
    }
    public void reconnect(){
        try {
            sftp.connect();
        } catch (JSchException e) {
            throw new RuntimeException(e);
        }
    }
}
