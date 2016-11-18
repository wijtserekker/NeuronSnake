package wijtse.model.game;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by wijtse on 18-11-16.
 */
public class SnakeView {

    private static final ArrayList<SnakeViewLine> UP_LIST = new ArrayList<>(Arrays.asList(new SnakeViewLine[]{SnakeViewLine.RIGHT, SnakeViewLine.UP_RIGHT, SnakeViewLine.UP, SnakeViewLine.UP_LEFT, SnakeViewLine.LEFT}));
    private static final ArrayList<SnakeViewLine> DOWN_LIST = new ArrayList<>(Arrays.asList(new SnakeViewLine[]{SnakeViewLine.LEFT, SnakeViewLine.DOWN_LEFT, SnakeViewLine.DOWN, SnakeViewLine.DOWN_RIGHT, SnakeViewLine.RIGHT}));
    private static final ArrayList<SnakeViewLine> LEFT_LIST = new ArrayList<>(Arrays.asList(new SnakeViewLine[]{SnakeViewLine.UP, SnakeViewLine.UP_LEFT, SnakeViewLine.LEFT, SnakeViewLine.DOWN_LEFT, SnakeViewLine.DOWN}));
    private static final ArrayList<SnakeViewLine> RIGHT_LIST = new ArrayList<>(Arrays.asList(new SnakeViewLine[]{SnakeViewLine.DOWN, SnakeViewLine.DOWN_RIGHT, SnakeViewLine.RIGHT, SnakeViewLine.UP_RIGHT, SnakeViewLine.UP}));

    public static ArrayList<SnakeViewLine> getViewLinesByDirection(SnakeSegment.Direction direction) {
        switch (direction) {
            case UP:
                return UP_LIST;
            case DOWN:
                return DOWN_LIST;
            case LEFT:
                return LEFT_LIST;
            case RIGTH:
                return RIGHT_LIST;
            default:
                return new ArrayList<>();
        }
    }
}
