package org.beru.server.beruserver.model;

import com.jcraft.jsch.*;
import io.github.palexdev.materialfx.controls.MFXProgressBar;
import javafx.scene.control.Label;
import org.beru.server.beruserver.model.file.RemoteFile;
import org.beru.server.beruserver.resources.Active;
import org.beru.server.beruserver.resources.Path;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class SSH {
    public Session session;
    public ChannelSftp sftp;
    private MFXProgressBar progressBar;
    private Label label;

    public SSH(String user, String password, String host, int port){
        try {
            session = new JSch().getSession(user, host,port);
            session.setPassword(password);
            System.out.println(password);
            session.setConfig("PreferredAuthentications","password");
            session.setConfig("StrictHostKeyChecking", "no");
        } catch (JSchException e) {
            throw new RuntimeException(e);
        }
        connect();
    }
    public boolean connect(){
        try {
            session.connect();
            sftp = (ChannelSftp) session.openChannel("sftp");
            sftp.connect();
            Path.REMOTE.append(sftp.getHome());
            sftp.cd(sftp.getHome());
        } catch (JSchException | SftpException e) {
            throw new RuntimeException(e);
        }
        return session.isConnected();
    }
    public List<RemoteFile> list(){
        List<RemoteFile> files = new ArrayList<>();
        try{
            ChannelSftp.LsEntrySelector selector = new ChannelSftp.LsEntrySelector() {
                @Override
                public int select(ChannelSftp.LsEntry entry) {
                    if(entry.getFilename().equals(".") || entry.getFilename().equals(".."))
                        return CONTINUE;
                    else {
                        RemoteFile file = null;
                        try {
                            file = new RemoteFile(sftp.pwd()+"/"+entry.getFilename(), entry.getFilename(),entry.getFilename(), entry.getAttrs().getSize(), entry.getAttrs().isDir());
                        } catch (SftpException e) {
                            throw new RuntimeException(e);
                        }
                        files.add(file);
                    }
                    return CONTINUE;
                }
            };
            sftp.ls(sftp.pwd(), selector);
        }catch (SftpException e) {
            throw new RuntimeException(e);
        }
        return files;
    }
    public String pwd(){
        try {
            return sftp.pwd();
        } catch (SftpException e) {
            throw new RuntimeException(e);
        }
    }
    public String getHome(){
        try {
            return sftp.getHome();
        } catch (SftpException e) {
            throw new RuntimeException(e);
        }
    }
    public void download(String rPath, String lPath){
        new Thread(() ->{
            File f = new File(rPath);
            System.out.println("Download File");
            try {
                sftp.get(f.getAbsolutePath(), lPath+"/"+f.getName(), new ProgressMonitor("Downloading",progressBar,label));
            } catch (SftpException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
    public void downloadFolder(String rPath, String lPath){
        new Thread(() ->{
            try {
                System.out.println("Download Folder");
                new File(lPath).mkdirs();
                sftp.lcd(lPath);
                lsFolderCopy(rPath, lPath);
            } catch (SftpException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
    private void lsFolderCopy(String sourcePath, String destPath) throws SftpException { // List source (remote, sftp) directory and create a local copy of it - method for every single directory.
        Vector<ChannelSftp.LsEntry> list = sftp.ls(sourcePath); // List source directory structure.
        for (ChannelSftp.LsEntry oListItem : list) { // Iterate objects in the list to get file/folder names.
            if (!oListItem.getAttrs().isDir()) { // If it is a file (not a directory).
                if (!(new File(destPath + "/" + oListItem.getFilename())).exists() || (oListItem.getAttrs().getMTime() > Long.valueOf(new File(destPath + "/" + oListItem.getFilename()).lastModified() / (long) 1000).intValue())) { // Download only if changed later.
                    new File(destPath + "/" + oListItem.getFilename());
                    sftp.get(sourcePath + "/" + oListItem.getFilename(), destPath + "/" + oListItem.getFilename(),new ProgressMonitor("Downloading",progressBar,label)); // Grab file from source ([source filename], [destination filename]).
                }
            } else if (!(".".equals(oListItem.getFilename()) || "..".equals(oListItem.getFilename()))) {
                new File(destPath + "/" + oListItem.getFilename()).mkdirs(); // Empty folder copy.
                lsFolderCopy(sourcePath + "/" + oListItem.getFilename(), destPath + "/" + oListItem.getFilename()); // Enter found folder on server to read its contents and create locally.
            }
        }
    }
    public void send(String lPath, String rPath){
        new Thread(() ->{
            File f = new File(lPath);
            try {
                sftp.put(f.getAbsolutePath(), f.getName(), new ProgressMonitor("Uploading",progressBar,label));
            } catch (SftpException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
    public void sendFolder(String lPath, String rPath){
        new Thread(() ->{
            File f = new File(lPath);
            try {
                sftp.put(lPath, rPath+"/"+f.getName(), new ProgressMonitor("Uploading",progressBar,label));
            } catch (SftpException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
    public boolean delete(String filePath){
        try {
            sftp.rm(filePath);
            return true;
        } catch (SftpException e) {
            throw new RuntimeException(e);
        }
    }
    public void rename(String file, String newFile){
        try {
            sftp.rename(file, newFile);
        } catch (SftpException e) {
            throw new RuntimeException(e);
        }
    }
    public void reconnect(){
        try {
            sftp.connect();
        } catch (JSchException e) {
            throw new RuntimeException(e);
        }
    }
    public void setProgressBar(MFXProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public void setLabel(Label label) {
        this.label = label;
    }
}
