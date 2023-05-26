package org.beru.server.beruserver.controller;

import com.jcraft.jsch.SftpException;
import io.github.palexdev.materialfx.controls.MFXProgressBar;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import jfxtras.styles.jmetro.JMetroStyleClass;
import org.beru.server.beruserver.model.file.LocalFile;
import org.beru.server.beruserver.model.file.RemoteFile;
import org.beru.server.beruserver.model.login.User;
import org.beru.server.beruserver.resources.Active;
import org.beru.server.beruserver.resources.Path;
import org.beru.server.beruserver.view.files.LocalFiles;
import org.beru.server.beruserver.view.files.RemoteFiles;
import org.beru.server.beruserver.view.ui.Actions;
import org.beru.server.beruserver.view.ui.Context;
import org.beru.server.beruserver.view.ui.control.TreeItemFile;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class SftpController implements Initializable {
    public TreeView<String> treeView;
    public TreeView<String> remoteFilesTreeView;
    public HBox bottomPanel;
    public TabPane remoteFileView;
    public TabPane localFileView;
    public MFXProgressBar progressBar;
    public Label logInfoText;

    LocalFiles localFiles;
    RemoteFiles remoteFiles;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Active.sftpController = this;
        initLocal();
    }
    private void initLocal(){
        String file = Path.LOCAL.toString();
        localFiles = new LocalFiles(file);
        treeView.setRoot(localFiles.getRootItem());
        localFiles.loadTreeItem((TreeItemFile<?>) treeView.getRoot(), file);
        style();
        treeView.setOnMouseClicked(event -> {
            Active.tabPane = localFileView;
            if(event.getButton() == MouseButton.PRIMARY)
                openFileAction(event, treeView);
            else if(event.getButton() == MouseButton.SECONDARY){
                treeView.setContextMenu(getContext(event, treeView));
            }
        });
    }
    public void login(){
        String remotePath = Path.REMOTE.toString();
        remoteFiles = new RemoteFiles(remotePath);
        remoteFilesTreeView.setRoot(remoteFiles.getRootItem());
        remoteFiles.loadTreeItem((TreeItemFile<?>) remoteFilesTreeView.getRoot(), remotePath);
        style();

        remoteFilesTreeView.setOnMouseClicked((event -> {
            Active.tabPane = remoteFileView;
            if(event.getButton() == MouseButton.PRIMARY)
                openFileAction(event, remoteFilesTreeView);
            else if(event.getButton() == MouseButton.SECONDARY)
                remoteFilesTreeView.setContextMenu(getContext(event, remoteFilesTreeView));
        }));

        Active.ssh.setProgressBar(progressBar);
        Active.ssh.setLabel(logInfoText);
    }
    public void openFileAction(MouseEvent event, TreeView<?> treeView){
        Node node = event.getPickResult().getIntersectedNode();
        if(node instanceof Text || (node instanceof TreeCell<?> && ((TreeCell<?>) node).getText() != null)){
            TreeItemFile<?> item = (TreeItemFile<?>) treeView.getSelectionModel().getSelectedItem();
            if(item.getFile() instanceof LocalFile localFile){
                Active.tabPane = localFileView;
                localFile.setPath(getPath(item));
                Actions.openFileExplorer(localFile, false);
            }else if(item.getFile() instanceof RemoteFile fileInfo){
                Active.tabPane = remoteFileView;
                try {
                    Active.ssh.sftp.cd(getPath(item));
                } catch (SftpException e) {
                    throw new RuntimeException(e);
                }
                Actions.openFileExplorer(fileInfo, false);
            }
        }
    }
    public ContextMenu getContext(MouseEvent event, TreeView<?> treeView){
        Node node = event.getPickResult().getIntersectedNode();
        if(node instanceof Text || (node instanceof TreeCell<?> && ((TreeCell<?>) node).getText() != null)){
            TreeItemFile<?> item = (TreeItemFile<?>) treeView.getSelectionModel().getSelectedItem();
            if(item.getFile() instanceof LocalFile localFile){
                return Context.file(localFile);
            }else if(item.getFile() instanceof RemoteFile fileInfo){
                return Context.file(fileInfo);
            }
        }
        return null;
    }
    public String getPath(TreeItem<?> item) {
        StringBuilder path = new StringBuilder("/home/");
        buildPath(item, path);
        return path.toString();
    }
    public void buildPath(TreeItem<?> item, StringBuilder builder){
        if(item.getParent() != null){
            buildPath(item.getParent(), builder);
            builder.append('/');
        }
        builder.append(item.getValue());
    }
    private void style(){
        bottomPanel.getStyleClass().add(JMetroStyleClass.BACKGROUND);
    }

    public void newConnection(ActionEvent actionEvent) {
        Actions.openFTPLogin();
    }
}
