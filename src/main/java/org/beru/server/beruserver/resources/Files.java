package org.beru.server.beruserver.resources;

import java.io.File;
import java.io.IOException;

public class Files {
    public static final String CACHE = Path.CACHE+"/cache.json";
    public static final String CONFIG = Path.CONFIG+"/config.json";
    public static boolean files() throws IOException {
        File cache = new File(CACHE);
        File config = new File(CONFIG);
        return cache.createNewFile() && config.createNewFile();
    }
}
