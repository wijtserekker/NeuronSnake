package wijtse.model.game;

/**
 * Created by wijtse on 18-11-16.
 */
public enum SnakeViewLine {

    RIGHT, UP_RIGHT, UP, UP_LEFT, LEFT, DOWN_LEFT, DOWN, DOWN_RIGHT;

    public int getXDistance() {
        int result = 0;

        switch (this) {
            case RIGHT:
                result = 1;
                break;
            case UP_RIGHT:
                result = 1;
                break;
            case UP:
                result = 0;
                break;
            case UP_LEFT:
                result = -1;
                break;
            case LEFT:
                result = -1;
                break;
            case DOWN_LEFT:
                result = -1;
                break;
            case DOWN:
                result = 0;
                break;
            case DOWN_RIGHT:
                result = 1;
                break;
        }

        return result;
    }

    public int getYDistance() {
        int result = 0;

        switch (this) {
            case RIGHT:
                result = 0;
                break;
            case UP_RIGHT:
                result = -1;
                break;
            case UP:
                result = -1;
                break;
            case UP_LEFT:
                result = -1;
                break;
            case LEFT:
                result = 0;
                break;
            case DOWN_LEFT:
                result = 1;
                break;
            case DOWN:
                result = 1;
                break;
            case DOWN_RIGHT:
                result = 1;
                break;
        }

        return result;
    }

}
