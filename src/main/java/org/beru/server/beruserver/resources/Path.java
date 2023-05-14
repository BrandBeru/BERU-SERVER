package org.beru.server.beruserver.resources;

import org.beru.server.beruserver.model.SSH;

import java.io.File;

public class Path {
    public static final String HOME = System.getProperty("user.home");
    public static final String JAVA = System.getProperty("java.home");
    public static final String USER = System.getProperty("user.dir");
    public static final String CACHE = USER+"/.cache";
    public static final String CONFIG = USER+"/.config";
    public static StringBuilder LOCAL = new StringBuilder(HOME);
    public static StringBuilder REMOTE = new StringBuilder();

    public static boolean mkdirs() {
        File cache = new File(CACHE);
        File config = new File(CONFIG);

        return cache.mkdir() && config.mkdir();
    }
}
