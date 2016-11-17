package han.view;

import han.view.network.ViewNetwork;
import javafx.scene.paint.Color;

/**
 * Created by han on 16-11-16.
 * Simple location class
 */
public class Graphics {
    private int x;
    private int y;
    private int px;
    private int py;
    private Color color;
    private int lw;

    public Graphics(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.px = (x + 1) * ViewNetwork.X_STANDARD_OFFSET;
        this.py = (y + 1) * ViewNetwork.Y_STANDARD_OFFSET;
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

    public int getPx() {
        return px;
    }

    public int getPy() {
        return py;
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
}
