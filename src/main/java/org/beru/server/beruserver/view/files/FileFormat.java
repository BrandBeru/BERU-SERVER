package org.beru.server.beruserver.view.files;

public class FileFormat {
    private String name;
    private String globalPath;
    private long size;
    private String ATime;
    private String MTIme;
    private boolean directory;

    public FileFormat(String name, String globalPath, long size, String ATime, String MTIme, boolean directory) {
        this.name = name;
        this.globalPath = globalPath;
        this.size = size;
        this.ATime = ATime;
        this.MTIme = MTIme;
        this.directory = directory;
    }

    public FileFormat() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGlobalPath() {
        return globalPath;
    }

    public void setGlobalPath(String globalPath) {
        this.globalPath = globalPath;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getATime() {
        return ATime;
    }

    public void setATime(String ATime) {
        this.ATime = ATime;
    }

    public String getMTIme() {
        return MTIme;
    }

    public void setMTIme(String MTIme) {
        this.MTIme = MTIme;
    }

    public boolean isDirectory() {
        return directory;
    }

    public void setDirectory(boolean directory) {
        this.directory = directory;
    }
}
