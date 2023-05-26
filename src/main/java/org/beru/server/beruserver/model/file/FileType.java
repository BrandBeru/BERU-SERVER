package org.beru.server.beruserver.model.file;

import java.util.List;

public abstract class FileType<T> {
    private T type;
    private String path;
    private String name;
    private String extension;
    private long size;
    private boolean folder;

    public String getPath() {
        return path;
    }
    public void setPath(String path){
        this.path = path;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public void setFolder(boolean folder) {
        this.folder = folder;
    }

    public String getExtension(){
        return extension;
    }
    public boolean isDirectory(){
        return folder;
    }
    public long getSize(){
        return size;
    }
    public T getType() {
        return type;
    }
    public void setType(Object type) {
        this.type = (T) type;
    }
}
