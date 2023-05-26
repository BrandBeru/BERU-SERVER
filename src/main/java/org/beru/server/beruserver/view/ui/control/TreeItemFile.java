package org.beru.server.beruserver.view.ui.control;

import javafx.scene.control.TreeItem;
import org.beru.server.beruserver.model.file.LocalFile;
import org.beru.server.beruserver.model.file.RemoteFile;

public class TreeItemFile<T> extends TreeItem<String> {
    private T file;
    public TreeItemFile(RemoteFile file) {
        this.file = (T) file;
        setValue(file.getName());
    }
    public TreeItemFile(LocalFile file){
        this.file = (T) file;
        setValue(file.getName());
    }
    public TreeItemFile(String file){
        this.file = null;
        setValue(file);
    }

    public T getFile() {
        return file;
    }

    public void setFile(T file) {
        this.file = file;
    }
}
