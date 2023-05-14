package org.beru.server.beruserver.view.files;

import javafx.scene.control.TreeItem;

import java.util.ArrayList;

public abstract class FileManagement {
    public abstract void mkdir(String folder);
    public abstract void mkdirs(String path);
    public abstract void rm(String name);
    public abstract void send(String name);
    public abstract ArrayList<FileFormat> ls(String path);

    public abstract void loadTreeItem(TreeItem<String> selected, String path);
    public abstract TreeItem<String> getRootItem();

}
