package org.beru.server.beruserver.resources;

import java.io.File;
import java.io.IOException;

public class Files {
    public static final String CACHE = Path.CACHE+"/cache.json";
    public static final String DB_CONFIG = Path.CONFIG+"/db_config.json";
    public static final String FTP_CONFIG = Path.CONFIG+"/ftp_config.json";
    public static final String SSH_CONFIG = Path.CONFIG+"/ssh_config.json";
    public static boolean files() throws IOException {
        File cache = new File(CACHE);
        File db_config = new File(DB_CONFIG);
        File ftp_config = new File(FTP_CONFIG);
        File shh_config = new File(SSH_CONFIG);

        return cache.createNewFile() && db_config.createNewFile() && ftp_config.createNewFile() && shh_config.createNewFile();
    }
}
