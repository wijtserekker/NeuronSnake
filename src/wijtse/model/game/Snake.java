package wijtse.model.game;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import wijtse.model.brain.Brain;
import wijtse.view.BoardView;
import wijtse.view.BrainView;

import java.util.ArrayList;

/**
 * Created by wijtse on 17-11-16.
 */
public class Snake {

    public static final int VIEW_DISTANCE = 5;
    public static final int INIT_LENGTH = 3;

    private int age;
    private ArrayList<Double> dna;
    private Brain brain;
    private ArrayList<SnakeSegment> segments;
    private Board board;
    private BrainView brainView;

    public Snake(Board board, int startX, int startY, ArrayList<Double> dna) {
        this.age = 0;
        this.dna = dna;
        this.brain = new Brain(Board.INPUT_NEURONS, Board.OUTPUT_NEURONS, Board.HIDDEN_LAYERS, Board.NEURONS_PER_HIDDEN_LAYER, dna);
        this.segments = new ArrayList<>();
        for (int i = 0; i < INIT_LENGTH; i++) {
            segments.add(new SnakeSegment(startX + i, startY, SnakeSegment.Direction.LEFT));
        }
        this.board = board;

        System.out.println("hi");
        //TODO LELIJK
        brainView = new BrainView(brain);
        Platform.runLater(new Runnable() {
            public void run() {
                try {
                    brainView.start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void move() {
        age++;

        //Move all the segments forward
        for (SnakeSegment segment : segments) {
            segment.move();
        }
        SnakeSegment.Direction lastSegmentDirection = segments.get(segments.size() - 1).getDirection();
        //Set the direction of a segment to the direction of the segment in front of it.
        for (int i = segments.size() - 1; i > 0; i--) {
            segments.get(i).setDirection(segments.get(i - 1).getDirection());
        }

        //Eat food with the head (first segment)
        if (board.eatFood(segments.get(0).getX(), segments.get(0).getY())) {
            switch (lastSegmentDirection) {
                case LEFT:
                    segments.add(new SnakeSegment(segments.get(segments.size()-1).getX() + 1, segments.get(segments.size()-1).getY(), lastSegmentDirection));
                    break;
                case RIGTH:
                    segments.add(new SnakeSegment(segments.get(segments.size()-1).getX() - 1, segments.get(segments.size()-1).getY(), lastSegmentDirection));
                    break;
                case UP:
                    segments.add(new SnakeSegment(segments.get(segments.size()-1).getX(), segments.get(segments.size()-1).getY() + 1, lastSegmentDirection));
                    break;
                case DOWN:
                    segments.add(new SnakeSegment(segments.get(segments.size()-1).getX(), segments.get(segments.size()-1).getY() - 1, lastSegmentDirection));
                    break;

            }
        }
    }

    public void adjustDirection() {
        ArrayList<Double> decision = brain.think(look());
        if (decision.get(0) > 0.5 && decision.get(1) < 0.5) {
            turnRight();
        } else if (decision.get(1) > 0.5 && decision.get(0) < 0.5) {
            turnLeft();
        }
        brainView.draw(brain); //TODO LELIJK
    }

    private void turnRight() {
        switch (segments.get(0).getDirection()) {
            case UP:
                segments.get(0).setDirection(SnakeSegment.Direction.RIGTH);
                break;
            case DOWN:
                segments.get(0).setDirection(SnakeSegment.Direction.LEFT);
                break;
            case LEFT:
                segments.get(0).setDirection(SnakeSegment.Direction.UP);
                break;
            case RIGTH:
                segments.get(0).setDirection(SnakeSegment.Direction.DOWN);
                break;
        }
    }

    private void turnLeft() {
        switch (segments.get(0).getDirection()) {
            case UP:
                segments.get(0).setDirection(SnakeSegment.Direction.LEFT);
                break;
            case DOWN:
                segments.get(0).setDirection(SnakeSegment.Direction.RIGTH);
                break;
            case LEFT:
                segments.get(0).setDirection(SnakeSegment.Direction.DOWN);
                break;
            case RIGTH:
                segments.get(0).setDirection(SnakeSegment.Direction.UP);
                break;
        }
    }

    private ArrayList<Double> look() {
        ArrayList<Double> result = new ArrayList<>();

        //Get the location of the head of the snake
        int headX = segments.get(0).getX();
        int headY = segments.get(0).getY();

        //Look into the possible directions for objects
        for (SnakeViewLine viewLine : SnakeView.getViewLinesByDirection(segments.get(0).getDirection())) {
            //Look for food
            double neuronValue = 0.0;
            for (int i = 1; i <= VIEW_DISTANCE; i++) {
                if (board.isThereFoodAt(headX + i * viewLine.getXDistance(), headY + i * viewLine.getYDistance())) {
                    neuronValue = (double)(VIEW_DISTANCE - i + 1) / (double)(VIEW_DISTANCE);
                    break;
                }
            }
            result.add(neuronValue);

            //Look for itself
            neuronValue = 0.0;
            for (int i = 1; i <= VIEW_DISTANCE; i++) {
                for (SnakeSegment segment : segments) {
                    if (segment.getX() == headX + i * viewLine.getXDistance() && segment.getY() == headY + i * viewLine.getYDistance()) {
                        neuronValue = (double)(VIEW_DISTANCE - i + 1) / (double)(VIEW_DISTANCE);
                        break;
                    }
                }
            }
            result.add(neuronValue);

            //Look for wall
            neuronValue = 0.0;
            for (int i = 1; i <= VIEW_DISTANCE; i++) {
                if (headX + i * viewLine.getXDistance() < 0 || headX + i * viewLine.getXDistance() >= board.getWidth() ||
                        headY + i * viewLine.getYDistance() < 0 || headY + i * viewLine.getYDistance() >= board.getHeight()) {
                    neuronValue = (double)(VIEW_DISTANCE - i + 1) / (double)(VIEW_DISTANCE);
                    break;
                }
            }
            result.add(neuronValue);
        }
        System.out.println(result);
        return result;
    }

    public int getFitness() {
        return 10 * segments.size() + age;
    }

    public ArrayList<Double> getDna() {
        return dna;
    }

    public void draw(GraphicsContext graphics) {
        graphics.setFill(BoardView.SNAKE_HEAD_COLOR);
        segments.get(0).draw(graphics);
        graphics.setFill(BoardView.SNAKE_COLOR);
        for (int i = 1; i < segments.size(); i++) {
            segments.get(i).draw(graphics);
        }
    }
}