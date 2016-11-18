package wijtse.model.game;

import javafx.scene.canvas.GraphicsContext;
import wijtse.view.BoardView;

/**
 * Created by wijtse on 18-11-16.
 */
public class SnakeSegment {

    public enum Direction {
        LEFT, RIGTH, UP, DOWN
    }

    private int x;
    private int y;
    private Direction direction;

    public SnakeSegment(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public void move() {
        switch (direction) {
            case LEFT:
                x--;
                break;
            case RIGTH:
                x++;
                break;
            case UP:
                y--;
                break;
            case DOWN:
                y++;
                break;
        }
    }

    public void draw(GraphicsContext graphics) {
        graphics.fillRect(BoardView.BOARD_TILE_SIZE * x, BoardView.BOARD_TILE_SIZE * y, BoardView.BOARD_TILE_SIZE, BoardView.BOARD_TILE_SIZE);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
