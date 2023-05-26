package org.beru.server.beruserver.view.files;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import javafx.scene.control.TreeItem;
import org.beru.server.beruserver.model.file.RemoteFile;
import org.beru.server.beruserver.resources.Active;
import org.beru.server.beruserver.view.ui.control.TreeItemFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

public class RemoteFiles extends FileManagement{
    private TreeItemFile<RemoteFile> rootItem;
    private boolean showHiden = true;
    private ChannelSftp sftp = Active.ssh.sftp;

    public RemoteFiles(String path){
        File ff = new File(path);
        try {
            sftp.cd(path);
        } catch (SftpException e) {
            throw new RuntimeException(e);
        }
        RemoteFile rootFile = new RemoteFile(Active.ssh.getHome(), Active.ssh.session.getUserName(),"",0,true);
        rootItem = new TreeItemFile<>(rootFile);
        rootItem.setExpanded(true);
    }
    @Override
    public void mkdir(String folder) {

    }

    @Override
    public void mkdirs(String path) {

    }

    @Override
    public void rm(String name) {

    }

    @Override
    public void send(String name) {

    }

    @Override
    public ArrayList<FileFormat> ls(String path) {
        return null;
    }

    @Override
    public void loadTreeItem(TreeItemFile<?> selected, String path) {
        Deque<TreeItemFile<RemoteFile>> elements = new LinkedList<>();
        selected.getChildren().clear();
        ChannelSftp.LsEntrySelector selector = new ChannelSftp.LsEntrySelector() {
            @Override
            public int select(ChannelSftp.LsEntry entry) {
                String ext = entry.getAttrs().isDir() ? "":entry.getFilename().split("\\.")[1];
                RemoteFile remoteFile = new RemoteFile(Active.ssh.pwd()+"/"+entry.getFilename(),entry.getFilename(),ext,entry.getAttrs().getSize(),entry.getAttrs().isDir());
                String name = remoteFile.getName();
                if(name.equals(".") || name.equals(".."))
                    return CONTINUE;
                TreeItemFile<RemoteFile> item = new TreeItemFile<>(remoteFile);
                if(remoteFile.isDirectory()) {
                    elements.addFirst(item);
                    if(entry.getAttrs().getSize()>6)
                        item.getChildren().add(new TreeItem<>(""));
                }else
                    elements.add(item);

                item.expandedProperty().addListener(((observable, oldValue, newValue) -> {
                    System.out.println(remoteFile.getPath());
                    try {
                        sftp.cd(remoteFile.getPath());
                    } catch (SftpException e) {
                        throw new RuntimeException(e);
                    }
                    loadTreeItem(item, remoteFile.getPath());
                }));
                return CONTINUE;
            }
        };
        try {
            sftp.ls(sftp.pwd(), selector);
        } catch (SftpException e) {
            throw new RuntimeException(e);
        }
        selected.getChildren().addAll(elements);
    }

    @Override
    public TreeItemFile<RemoteFile> getRootItem() {
        return rootItem;
    }
}
