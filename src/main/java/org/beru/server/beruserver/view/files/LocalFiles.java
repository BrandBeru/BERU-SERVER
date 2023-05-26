package org.beru.server.beruserver.view.files;

import javafx.scene.control.TreeItem;
import org.beru.server.beruserver.model.file.LocalFile;
import org.beru.server.beruserver.view.ui.control.TreeItemFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

public class LocalFiles extends FileManagement{
    private TreeItemFile<LocalFiles> rootItem;
    private boolean showHiden = true;
    public LocalFiles(String file){
        System.out.println("File->"+file);
        LocalFile localFile = new LocalFile(new File(file));
        rootItem = new TreeItemFile<>(localFile);
        rootItem.setExpanded(true);
    }
    @Override
    public void loadTreeItem(TreeItemFile<?> selected, String path){
        File fPath = new File(path);
        Deque<TreeItemFile<LocalFiles>> elements = new LinkedList<>();
        selected.getChildren().clear();
        Arrays.stream(fPath.listFiles()).forEach(f -> {
            TreeItemFile<LocalFiles> item = new TreeItemFile<>(new LocalFile(f));

            if(!showHiden && f.isHidden())
                return;
            if(f.isDirectory()) {
                elements.addFirst(item);
                if(f.listFiles().length > 0)
                    item.getChildren().add(new TreeItem<>());
            }
            else
                elements.add(item);

            item.expandedProperty().addListener(((observable, oldValue, newValue) -> {
                loadTreeItem(item, f.getAbsolutePath());
            }));
        });
        selected.getChildren().addAll(elements);
    }

    @Override
    public TreeItemFile<LocalFiles> getRootItem() {
        return rootItem;
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
}
