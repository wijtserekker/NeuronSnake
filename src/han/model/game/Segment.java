package han.model.game;

import han.view.game.GameGraphics;
import javafx.scene.paint.Color;

/**
 * Created by han on 18-11-16.
 * Part of the snake
 */
public class Segment {

    private Snake.Direction direction;
    private Location location;
    private GameGraphics graphics;

    public Segment(Color color) {
        this.direction = Snake.Direction.RIGHT;
        this.graphics = new GameGraphics(color);
    }

    public Snake.Direction getDirection() {
        return direction;
    }

    public void setDirection(Snake.Direction direction) {
        this.direction = direction;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public GameGraphics getGraphics() {
        return graphics;
    }
}
