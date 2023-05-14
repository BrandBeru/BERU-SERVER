package org.beru.server.beruserver.view.ui;

import javafx.geometry.Dimension2D;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public class ScreenInformation {
    private double width;
    private double height;
    private double maxX;
    private double maxY;
    private double minX;
    private double minY;
    private double dpi;
    private Dimension2D size;
    public ScreenInformation(Screen screen){
        Rectangle2D rectangle2D = screen.getBounds();
        this.width = rectangle2D.getWidth();
        this.height = rectangle2D.getHeight();
        this.maxX = rectangle2D.getMaxX();
        this.maxY = rectangle2D.getMaxY();
        this.minX = rectangle2D.getMinX();
        this.minY = rectangle2D.getMinY();
        this.dpi = screen.getDpi();
        this.size = new Dimension2D(width, height);
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMaxY() {
        return maxY;
    }

    public double getMinX() {
        return minX;
    }

    public double getMinY() {
        return minY;
    }

    public double getDpi() {
        return dpi;
    }

    public Dimension2D getSize() {
        return size;
    }
}
