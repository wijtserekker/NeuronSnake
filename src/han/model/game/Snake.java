package han.model.game;

import han.controller.game.NeuronSnake;
import han.model.network.Edge;
import han.model.network.Network;
import han.model.network.Node;
import han.view.network.NetworkView;
import javafx.scene.paint.Color;
import wijtse.model.brain.Neuron;

import java.util.ArrayList;
import java.util.List;

import static han.model.game.Board.BOARD_SIZE_X;
import static han.model.game.Board.BOARD_SIZE_Y;
import static han.view.game.GameView.GRID_SIZE;

/**
 * Created by han on 18-11-16.
 * collection of snake segments form a snake
 */
public class Snake {

    public static final int RGB_MODIFIER = 10;
    public static final int VIEW_DISTANCE = 5;

    public enum Direction {LEFT, RIGHT, UP, DOWN}

    public static final int SNAKE_START_LENGTH = 3;

    public enum VariableAxis {X, Y}

    private double fitness;
    private int age;
    private Network network;
    private List<Segment> segments = new ArrayList<>();
    private Board board;

    public Snake(Board board) {
        this.board = board;
        double rgb = 0;
        for (int i = 0; i < SNAKE_START_LENGTH; i++) {
            rgb = 1 - ((double) i / RGB_MODIFIER);
            System.out.println(rgb);
            Segment segment = new Segment(new Color(rgb, rgb / 2, rgb / 2, 1));
            segment.setLocation(new Location((BOARD_SIZE_X / 2) - i, Board.BOARD_SIZE_Y / 2));
            segments.add(segment);
        }

    }

    public void onTick() {
        turn();
        move();
        updateDirections();
        network.setInputNodeGroup(look());
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
            switch (segment.getDirection()) {
                case LEFT:
                    segment.getLocation().setX(cx - 1);
                    break;
                case RIGHT:
                    segment.getLocation().setX(cx + 1);
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

    private List<Node> look() {
        List<Node> result = network.getInputNodeGroup();
        Segment head = segments.get(0);
        int hx = head.getLocation().getX();
        int hy = head.getLocation().getY();

        int dx = 0;
        int dy = 0;

        switch (head.getDirection()) {
            case LEFT:
                dx = -1;
                dy = 0;
                break;
            case RIGHT:
                dx = 1;
                dy = 0;
                break;
            case UP:
                dx = 0;
                dy = -1;
                break;
            case DOWN:
                dx = 0;
                dy = 1;
                break;
        }

        List<List<Location>> viewLines = new ArrayList<>();
        List<Location> line = new ArrayList<>();
        Location location;
        int lx;
        int ly;
        //straight (direction it is going)
        for (int i = 0; i < VIEW_DISTANCE; i++) {
            lx = hx + (dx * (i + 1));
            ly = hy + (dy * (i + 1));
            location = new Location(lx, ly);
            line.add(location);
        }
        //if going left or right
        if (dy == 0) {
            System.out.println(line);
            viewLines.add(line);
            line.clear();
            //down
            for (int i = 0; i < VIEW_DISTANCE; i++) {
                lx = hx;
                ly = hy + (i + 1);
                location = new Location(lx, ly);
                line.add(location);
            }
            System.out.println(line);
            viewLines.add(line);
            line.clear();
            //up
            for (int i = 0; i < VIEW_DISTANCE; i++) {
                lx = hx;
                ly = hy - (i + 1);
                location = new Location(lx, ly);
                line.add(location);
            }
            System.out.println(line);
            viewLines.add(line);
            line.clear();
            //direction + down
            for (int i = 0; i < VIEW_DISTANCE; i++) {
                lx = hx + (dx * (i + 1));
                ly = hy + (i + 1);
                location = new Location(lx, ly);
                line.add(location);
            }
            System.out.println(line);
            viewLines.add(line);
            line.clear();
            //direction + up
            for (int i = 0; i < VIEW_DISTANCE; i++) {
                lx = hx + (dx * (i + 1));
                ly = hy - (i + 1);
                location = new Location(lx, ly);
                line.add(location);
            }
            System.out.println(line);
            viewLines.add(line);
        }

        //if going up or down
        if (dx == 0) {
            viewLines.add(line);
            line.clear();
            //right
            for (int i = 0; i < VIEW_DISTANCE; i++) {
                lx = hx + (i + 1);
                ly = hy;
                location = new Location(lx, ly);
                line.add(location);
            }
            viewLines.add(line);
            line.clear();
            //left
            for (int i = 0; i < VIEW_DISTANCE; i++) {
                lx = hx - (i + 1);
                ly = hy;
                location = new Location(lx, ly);
                line.add(location);
            }
            viewLines.add(line);
            line.clear();
            //direction + right
            for (int i = 0; i < VIEW_DISTANCE; i++) {
                lx = hx + (i + 1);
                ly = hy + (dy * (i + 1));
                location = new Location(lx, ly);
                line.add(location);
            }
            viewLines.add(line);
            line.clear();
            //direction + left
            for (int i = 0; i < VIEW_DISTANCE; i++) {
                lx = hx - (i + 1);
                ly = hy + (dy * (i + 1));
                location = new Location(lx, ly);
                line.add(location);
            }
            viewLines.add(line);
        }

        System.out.println(segments.get(0).getLocation());
        System.out.println(viewLines);

        //Check wall
        for (int i = 0; i < result.size(); i = i + 3) {
            for (List<Location> currentLine : viewLines) {
                for(Location currentLocation : currentLine) {
                    int cx = currentLocation.getX();
                    int cy = currentLocation.getY();
                    if (cx < 0 || cx > BOARD_SIZE_X || cy < 0 || cy > BOARD_SIZE_Y) {
                        result.get(i).setStrength((1.0 / VIEW_DISTANCE) * (i + 1));
                        System.out.println("Set " + i + " to " + (1.0 / VIEW_DISTANCE) * (i + 1));
                    }
                }
            }
        }

        for (int i = 1; i < result.size(); i = i + 3) {

        }

        for (int i = 2; i < result.size(); i = i + 3) {

        }
        return result;
    }


    public List<Segment> getSegments() {
        return segments;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

}
