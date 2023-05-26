package org.beru.server.beruserver.view.ui;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import org.apache.commons.io.FileUtils;
import org.beru.server.beruserver.Main;
import org.beru.server.beruserver.StartView;
import org.beru.server.beruserver.controller.db.DBInfoController;
import org.beru.server.beruserver.controller.db.TableInfoController;
import org.beru.server.beruserver.controller.files.FileExplorerController;
import org.beru.server.beruserver.model.db.model.Column;
import org.beru.server.beruserver.model.db.model.DB;
import org.beru.server.beruserver.model.db.model.Table;
import org.beru.server.beruserver.model.file.FileType;
import org.beru.server.beruserver.model.file.LocalFile;
import org.beru.server.beruserver.model.file.RemoteFile;
import org.beru.server.beruserver.resources.Active;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.io.IOException;

public class Actions {
    private static FXMLLoader loader;
    private static FontIcon icon;
    private static Tab tab;
    public static void loginDataBase(){
        Active.activeStage.close();
        try {
            new StartView().start("DataBaseManager");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }public static void loginSSH(){
        Active.activeStage.close();
        try {
            new StartView().start("SSHManager");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void openFTPLogin(){
        try {
            new StartView().start("login/FTPLogin");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void openDBLogin(){
        try {
            new StartView().start("login/DBLogin");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void openFileExplorer(FileType<?> file, boolean newTab){
        loader = new FXMLLoader(Main.class.getResource("view/files/FileExplorer.fxml"));
        icon = new FontIcon();
        tab = new Tab(file.getName(), icon);
        if (newTab)
            loaderInNewTab();
        else
            loader();
        FileExplorerController controller = loader.getController();
        controller.initialize(file);
    }
    public static void openDatabase(DB database){
        loader = new FXMLLoader(Main.class.getResource("view/db/DBInfo.fxml"));
        icon = new FontIcon("mdi2d-database:20");
        tab = new Tab(database.getName(), icon);
        loader();
        DBInfoController controller = loader.getController();
        controller.setDatabase(database);
    }
    public static void openTable(Table table){
        loader = new FXMLLoader(Main.class.getResource("view/db/TableInfo.fxml"));
        icon = new FontIcon("mdi2t-table:20");
        tab = new Tab(table.getName(), icon);
        loader();
        TableInfoController controller = loader.getController();
        controller.setTable(table);
    }
    public static void openColumn(Column column){
        System.out.println("Open: " + column);
    }
    public static void loader(){
        TabPane tabPane = Active.tabPane;
        try {
            AnchorPane anchorPane = loader.load();
            anchorPane.setPrefSize(tabPane.getWidth(), tabPane.getHeight());
            tab.setContent(anchorPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        tabPane.getTabs().set((tabPane.getTabs().size()-1),tab);
    }
    public static void loaderInNewTab(){
        TabPane tabPane = Active.tabPane;
        try {
            AnchorPane anchorPane = loader.load();
            anchorPane.setPrefSize(tabPane.getWidth(), tabPane.getHeight());
            tab.setContent(anchorPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        tabPane.getTabs().add(tab);
    }
    public static void deleteFile(FileType<?> fileType){
        if(new Messages().confirm("Are you sure you want to move \""+fileType.getName()+"\" to trash?")){
            if(fileType instanceof RemoteFile){
                Active.ssh.delete(fileType.getPath());
            }else if(fileType instanceof LocalFile localFile){
                try{
                    if(localFile.isDirectory())
                        FileUtils.deleteDirectory(localFile.getFile());
                    else
                        FileUtils.delete(localFile.getFile());
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            Active.fileExplorerController.listFiles();
        }
    }
    public static void rename(FileType<?> fileType){
        String message = new Messages().input("Name: ", "Rename file " + fileType.getName() + " To: ");
        if(!message.equals("")){
            if(fileType instanceof RemoteFile){
                String parent = new File(fileType.getPath()).getParent();
                Active.ssh.rename(fileType.getPath(), parent+"/"+message);
            }else{
                File file = new File(fileType.getPath());
                file.renameTo(new File(file.getParent()+"/"+message));
            }
            Active.fileExplorerController.listFiles();
        }
    }
}
