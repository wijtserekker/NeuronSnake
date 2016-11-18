package han.model.game;

/**
 * Created by han on 18-11-16.
 * The game board for snake
 */
public class Board {

    private Snake snake;

    public static final int BOARD_SIZE_X = 64;
    public static final int BOARD_SIZE_Y = 36;

    public Board() {
        this.snake = new Snake();
    }

}
