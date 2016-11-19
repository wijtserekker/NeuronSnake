package han.model.game;

/**
 * Created by han on 18-11-16.
 * Part of the snake
 */
public class Segment {

    private Snake.Direction direction;
    private Location location;

    public Segment() {
        this.direction = Snake.Direction.RIGHT;
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
}
