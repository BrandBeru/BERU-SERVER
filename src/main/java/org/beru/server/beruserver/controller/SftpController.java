package org.beru.server.beruserver.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import jfxtras.styles.jmetro.JMetroStyleClass;
import org.beru.server.beruserver.resources.Path;
import org.beru.server.beruserver.view.files.LocalFiles;
import org.beru.server.beruserver.view.files.RemoteFiles;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class SftpController implements Initializable {
    public TreeView treeView;
    public TreeView remoteFilesTreeView;
    public HBox bottomPanel;

    LocalFiles localFiles;
    RemoteFiles remoteFiles;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File file = new File(Path.LOCAL.toString());
        String remotePath = Path.REMOTE.toString();
        localFiles = new LocalFiles(file);
        remoteFiles = new RemoteFiles(remotePath);
        treeView.setRoot(localFiles.getRootItem());
        localFiles.loadTreeItem(treeView.getRoot(), file.getPath());
        remoteFilesTreeView.setRoot(remoteFiles.getRootItem());
        remoteFiles.loadTreeItem(remoteFilesTreeView.getRoot(), remotePath);
        style();
    }
    private void style(){
        bottomPanel.getStyleClass().add(JMetroStyleClass.BACKGROUND);
    }
}
