package org.beru.server.beruserver.model;

import com.jcraft.jsch.SftpProgressMonitor;
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;

public class ProgressMonitor implements SftpProgressMonitor {
    private Monitor monitor;
    public ProgressMonitor(Monitor monitor){
        this.monitor = monitor;
    }
    @Override
    public void init(int op, String src, String dest, long max) {
        Platform.runLater(()->{
            monitor.init(op,src,dest,max);
        });
    }

    @Override
    public boolean count(long count) {
        Platform.runLater(()->{
            monitor.count(count);
        });
        return true;
    }

    @Override
    public void end() {
        Platform.runLater(()->{
            monitor.end();
        });
    }
}
