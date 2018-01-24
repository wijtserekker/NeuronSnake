package han.view.game;

import javafx.scene.paint.Color;

/**
 * Created by han on 18-11-16.
 */
public class GameGraphics {

    private Color color;

    public GameGraphics(Color color) {
        this.color = color;
    }


    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
