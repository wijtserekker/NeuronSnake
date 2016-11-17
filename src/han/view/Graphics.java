package han.view;

import javafx.scene.paint.Color;

/**
 * Created by han on 16-11-16.
 * Simple location class
 */
public class Graphics {
    private int x;
    private int y;
    private Color color;
    private int lw;
    private int py;
    private int px;
    private int nodeSize;

    public Graphics(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public Graphics(Color color, int lw) {
        this.color = color;
        this.lw = lw;
    }

    public String locationToString() {
        return "(" + x + ", " + y + ")";
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getLw() {
        return lw;
    }

    public int getPy() {
        return py;
    }

    public void setPy(int py) {
        this.py = py;
    }

    public int getPx() {
        return px;
    }

    public void setPx(int px) {
        this.px = px;
    }

    public int getNodeSize() {
        return nodeSize;
    }

    public void setNodeSize(int nodeSize) {
        this.nodeSize = nodeSize;
    }
}
