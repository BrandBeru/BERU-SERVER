package org.beru.server.beruserver.model.file;

import java.io.File;

public class LocalFile extends FileType<File>{
    private final File file;

    public LocalFile(File file) {
        this.file = file;
        setType(this);
        setPath(file.getPath());
    }

    @Override
    public String getName() {
        return file.getName();
    }

    @Override
    public String getExtension() {
        return file.getName().split("\\.")[1];
    }

    @Override
    public boolean isDirectory() {
        return file.isDirectory();
    }

    @Override
    public long getSize() {
        return file.getUsableSpace();
    }

    public File getFile() {
        return file;
    }
}
