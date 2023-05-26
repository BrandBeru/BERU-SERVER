package org.beru.server.beruserver.model;

import com.jcraft.jsch.SftpProgressMonitor;
import io.github.palexdev.materialfx.controls.MFXProgressBar;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import org.beru.server.beruserver.resources.Active;

import java.text.NumberFormat;

public class ProgressMonitor implements SftpProgressMonitor {
    private String textInfo;
    private ProgressBar progressBar;
    private Label label;
    private long max;
    private long progress;
    public ProgressMonitor(String textInfo, MFXProgressBar progressBar, Label label){
        this.textInfo = textInfo;
        this.progressBar = progressBar;
        this.label = label;
    }
    @Override
    public void init(int op, String src, String dest, long max) {
        this.max = (max/1000000);
        System.out.println("SRc: "+src);
        System.out.println("Dest: "+dest);
        Platform.runLater(()->{
            progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
            label.setText(textInfo+": X Mb - "+this.max + " Mb");
        });
    }

    @Override
    public boolean count(long count) {
        progress += (count/1000);
        Platform.runLater(()->{
            label.setText(textInfo+": "+progress+" Kb - "+max + " Mb");
        });
        return true;
    }

    @Override
    public void end() {
        Platform.runLater(()->{
            progressBar.setProgress(1);
            Active.fileExplorerController.listFiles();
        });
    }
}
