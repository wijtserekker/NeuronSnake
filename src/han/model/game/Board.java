package han.model.game;

import han.view.game.GameView;

/**
 * Created by han on 18-11-16.
 * The game board for snake
 */
public class Board {

    private Snake snake;

    public static final int BOARD_SIZE_X = 32;
    public static final int BOARD_SIZE_Y = 18;

    public Board() {
        this.snake = new Snake(this);
    }

    public Snake getSnake() {
        return snake;
    }
}
