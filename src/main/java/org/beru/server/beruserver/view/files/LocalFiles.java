package org.beru.server.beruserver.view.files;

import javafx.scene.control.TreeItem;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

public class LocalFiles extends FileManagement{
    private TreeItem<String> rootItem;
    private boolean showHiden = true;
    public LocalFiles(File file){
        rootItem = new TreeItem<String>(file.getName());
        rootItem.setExpanded(true);
    }
    @Override
    public void loadTreeItem(TreeItem<String> selected, String path){
        File fPath = new File(path);
        Deque<TreeItem<String>> elements = new LinkedList<TreeItem<String>>();
        selected.getChildren().clear();
        Arrays.stream(fPath.listFiles()).forEach(f -> {
            TreeItem<String> item = new TreeItem<>(f.getName());

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
    public TreeItem<String> getRootItem() {
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
