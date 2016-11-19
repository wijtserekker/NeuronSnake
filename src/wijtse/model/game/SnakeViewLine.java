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

    public String toString() {
        String result = "";

        switch (this) {
            case RIGHT:
                result = "RIGHT";
                break;
            case UP_RIGHT:
                result = "UP_RIGHT";
                break;
            case UP:
                result = "UP";
                break;
            case UP_LEFT:
                result = "UP_LEFT";
                break;
            case LEFT:
                result = "LEFT";
                break;
            case DOWN_LEFT:
                result = "DOWN_LEFT";
                break;
            case DOWN:
                result = "DOWN";
                break;
            case DOWN_RIGHT:
                result = "DOWN_RIGHT";
                break;
        }

        return result;
    }

}
