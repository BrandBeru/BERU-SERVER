package org.beru.server.beruserver.controller.files;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.FlowPane;
import org.beru.server.beruserver.model.file.FileType;
import org.beru.server.beruserver.model.file.LocalFile;
import org.beru.server.beruserver.model.file.RemoteFile;
import org.beru.server.beruserver.resources.Active;
import org.beru.server.beruserver.view.files.FileManagement;
import org.beru.server.beruserver.view.ui.Context;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.util.*;

public class FileExplorerController {
    public FlowPane filesPane;
    public Label pathText;
    public ProgressBar progressBar;
    public TextField searchText;
    public Label itemsText;
    public Label spaceText;
    private final ToggleGroup filesGroup = new ToggleGroup();

    private FileType<?> file;
    private FileManagement fileManagement;

    private List<FileType<?>> fPaths = new ArrayList<>();
    private int pos;

    public void initialize(FileType<?> file) {
        this.file = file;
        Active.fileExplorerController = this;
        pathText.setText(file.getPath());
        fPaths.add(file);
        listFiles();

        filesPane.setOnDragOver(event -> {
            if(event != null){
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
        });
        System.out.println(file.getPath());
        filesPane.setOnDragDropped(event -> {
            Active.fileExplorerController = this;
            if(file instanceof LocalFile && inRemote){
                Dragboard db = event.getDragboard();
                File urlFile = new File(db.getUrl());
                if(urlFile.isDirectory())
                    Active.ssh.downloadFolder(urlFile.getPath(), ((LocalFile) file).getFile().getPath());
                else
                    Active.ssh.download(db.getUrl(), ((LocalFile) file).getFile().getPath());
                event.setDropCompleted(true);
                event.consume();
            }else if(file instanceof RemoteFile && !inRemote){
                Dragboard db = event.getDragboard();
                Active.ssh.send(db.getUrl(), file.getPath());

                event.setDropCompleted(true);
                event.consume();
            }
        });
    }
    public void listFiles(){
        filesPane.getChildren().clear();
        if(file instanceof RemoteFile f){
            List<RemoteFile> files = Active.ssh.list();
            files.forEach(this::load);
        }else if(file instanceof LocalFile f){
            List<File> files = Arrays.asList(Objects.requireNonNull(f.getFile().listFiles()));
            files.forEach(file -> load(new LocalFile(file)));
        }
    }
    static boolean inRemote;
    public void load(FileType<?> fType){
        ToggleButton file = new ToggleButton(fType.getName());
        file.setGraphic(fType.isDirectory() ? new FontIcon("mdi2f-folder:32") : new FontIcon("mdi2f-file:32"));
        file.setContentDisplay(ContentDisplay.TOP);
        file.setToggleGroup(filesGroup);
        file.setWrapText(true);
        file.setMinSize(64,64);
        file.setMaxSize(64, 128);
        file.setPrefSize(64, Button.USE_COMPUTED_SIZE);
        filesPane.getChildren().add(file);

        file.setOnMouseClicked(event -> {
            Active.fileExplorerController = this;
            if(event.getButton() == MouseButton.PRIMARY){
                if(event.getClickCount() >= 2) {
                    fPaths.add(fType);
                    open(fType);
                    pos++;
                }
            }else if(event.getButton() == MouseButton.SECONDARY){
                file.setContextMenu(Context.file(fType));
            }
        });
        file.setOnDragDetected(event -> {
            Dragboard db = file.startDragAndDrop(TransferMode.MOVE);
            var content = new ClipboardContent();
            inRemote = (fType instanceof RemoteFile);
            content.putUrl(fType.getPath());

            db.setContent(content);

            event.consume();
        });
    }
    public void open(FileType<?> file){
        if(file instanceof RemoteFile f){
            try {
                Active.ssh.sftp.cd(f.getPath());
            } catch (SftpException e) {
                throw new RuntimeException(e);
            }
        }
        if(file.isDirectory()){
            filesPane.getChildren().clear();
            pathText.setText(file.getPath());
            this.file = file;
            listFiles();
        }
    }

    public void back(ActionEvent actionEvent) {
        if(pos<=0)
            pos = 0;
        else
            pos--;
        System.out.println(pos);
        open(fPaths.get(pos));
    }

    public void forward(ActionEvent actionEvent) {
    }

    public void searchFiles(ActionEvent actionEvent) {
    }
}
