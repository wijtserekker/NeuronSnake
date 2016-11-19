package han.model.game;

import han.controller.game.NeuronSnake;
import han.model.network.Network;
import han.view.network.NetworkView;
import wijtse.model.brain.Neuron;

import java.util.ArrayList;
import java.util.List;

import static han.view.game.GameView.GRID_SIZE;

/**
 * Created by han on 18-11-16.
 * collection of snake segments form a snake
 */
public class Snake {

    public List<Segment> getSegments() {
        return segments;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public enum Direction{LEFT, RIGHT, UP, DOWN};

    public static final int SNAKE_START_LENGTH = 3;

    private double fitness;
    private int age;
    private Network network;
    private List<Segment> segments = new ArrayList<>();
    private Board board;

    public Snake(Board board) {
        this.board = board;
        for (int i = 0; i < SNAKE_START_LENGTH; i++) {
            Segment segment = new Segment();
            segment.setLocation(new Location((Board.BOARD_SIZE_X / 2) - i, Board.BOARD_SIZE_Y / 2));
            segments.add(segment);
        }

    }

    public void onTick() {
        turn();
        move();
        updateDirections();
    }

    private void updateDirections() {
        for (int i = segments.size() - 1; i > 0; i--) {
            segments.get(i).setDirection(segments.get(i - 1).getDirection());
        }
    }

    private void move() {
        for (Segment segment : segments) {
            int cx = segment.getLocation().getX();
            int cy = segment.getLocation().getY();
            System.out.println(cx);
            switch (segment.getDirection()) {
                case LEFT:
                    segment.getLocation().setX(cx - 1);
                    break;
                case RIGHT:
                    segment.getLocation().setX(cx + 1);
                    System.out.println(segment.getLocation().getX());
                    break;
                case UP:
                    segment.getLocation().setY(cy - 1);
                    break;
                case DOWN:
                    segment.getLocation().setY(cy + 1);
                    break;
            }
        }
    }

    private void turn() {
        if (network.getOutputNodeGroup().get(0).getStrength() >= 0.5 &&
                network.getOutputNodeGroup().get(1).getStrength() < 0.5) {
            turnLeft();
        } else if (network.getOutputNodeGroup().get(0).getStrength() < 0.5 &&
                network.getOutputNodeGroup().get(1).getStrength() >= 0.5) {
            turnRight();
        }
    }

    private void turnLeft() {
        switch (segments.get(0).getDirection()) {
            case LEFT:
                segments.get(0).setDirection(Direction.DOWN);
                break;
            case RIGHT:
                segments.get(0).setDirection(Direction.UP);
                break;
            case UP:
                segments.get(0).setDirection(Direction.LEFT);
                break;
            case DOWN:
                segments.get(0).setDirection(Direction.RIGHT);
                break;
        }
    }

    private void turnRight() {
        switch (segments.get(0).getDirection()) {
            case LEFT:
                segments.get(0).setDirection(Direction.UP);
                break;
            case RIGHT:
                segments.get(0).setDirection(Direction.DOWN);
                break;
            case UP:
                segments.get(0).setDirection(Direction.RIGHT);
                break;
            case DOWN:
                segments.get(0).setDirection(Direction.LEFT);
                break;
        }
    }


}
