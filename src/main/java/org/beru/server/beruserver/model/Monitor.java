package org.beru.server.beruserver.model;

import javafx.scene.control.ProgressBar;

public interface Monitor {
    void init(int op, String src, String dest, long max);
    void count(long count);
    void end();
}
