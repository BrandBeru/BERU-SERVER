package org.beru.server.beruserver.view.files;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import javafx.scene.control.TreeItem;
import org.beru.server.beruserver.model.SSH;

import java.io.File;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

public class RemoteFiles extends FileManagement{
    private TreeItem<String> rootItem;
    private boolean showHiden = true;
    private ChannelSftp sftp = SSH.instance.sftp;

    public RemoteFiles(String path){
        File ff = new File(path);
        try {
            sftp.cd(path);
        } catch (SftpException e) {
            throw new RuntimeException(e);
        }
        rootItem = new TreeItem<>(ff.getName());
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
    public void loadTreeItem(TreeItem<String> selected, String path) {
        File file = new File(path);
        Deque<TreeItem<String>> elements = new LinkedList<>();
        selected.getChildren().clear();
        ChannelSftp.LsEntrySelector selector = new ChannelSftp.LsEntrySelector() {
            @Override
            public int select(ChannelSftp.LsEntry entry) {
                String fileName = entry.getFilename();
                File ff = new File(file.getAbsolutePath()+"/"+fileName);
                if(fileName.equals(".") || fileName.equals(".."))
                    return CONTINUE;
                TreeItem<String> item = new TreeItem<>(fileName);
                if(entry.getAttrs().isDir()) {
                    elements.addFirst(item);
                    System.out.println(entry.getAttrs().getSize());
                    if(entry.getAttrs().getSize()>6)
                        item.getChildren().add(new TreeItem<>(""));
                }else
                    elements.add(item);

                item.expandedProperty().addListener(((observable, oldValue, newValue) -> {
                    loadTreeItem(item, ff.getAbsolutePath());
                }));
                return CONTINUE;
            }
        };
        try {
            sftp.ls(path, selector);
        } catch (SftpException e) {
            throw new RuntimeException(e);
        }
        selected.getChildren().addAll(elements);
    }

    @Override
    public TreeItem<String> getRootItem() {
        return rootItem;
    }
}
