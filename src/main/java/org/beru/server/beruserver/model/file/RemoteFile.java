package org.beru.server.beruserver.model.file;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import org.beru.server.beruserver.resources.Active;

import java.util.ArrayList;
import java.util.List;

public class RemoteFile extends FileType<ChannelSftp.LsEntry>{
    public RemoteFile(String path, String name, String ext, long size, boolean folder) {
        setType(this);
        setPath(path);
        setName(name);
        setFolder(folder);
        setExtension(ext);
        setSize(size);
    }
}
