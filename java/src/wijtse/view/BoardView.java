package wijtse.view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import wijtse.controller.Clock;
import wijtse.model.game.Board;
import wijtse.model.game.Snake;
import wijtse.model.game.SnakeSegment;

import java.util.ArrayList;

/**
 * Created by wijtse on 18-11-16.
 */
public class BoardView extends Application {

    //http://paletton.com/#uid=7560Q1kBHfL-dbNAyec-djDzcnuk0joO1cFo1jmY3rkq7-jLkpYuPiXwemmv2vovpxLrxkv6mqJlq7B3olsgkkqlgU
    public static final Color SNAKE_COLOR = Color.rgb(246, 113, 46);
    public static final Color SNAKE_HEAD_COLOR = Color.rgb(219, 74, 0);
    public static final Color FOOD_COLOR = Color.rgb(3, 119, 81);
    public static final Color BACKGROUND_COLOR = Color.BLACK;

    private Group root;
    private StackPane canvasHolder;
    private Canvas canvas;
    private GraphicsContext canvasGraphics;
    private Board board;
    private int tileSize;
    private Clock clock;
    private int bestFitness;

    public BoardView(Board board, int tileSize, Clock clock) {
        this.board = board;
        this.tileSize = tileSize;
        this.clock = clock;
        this.bestFitness = 0;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Network");
        root = new Group();
        canvasHolder = new StackPane();
        //Setup canvas
        canvas = new Canvas(board.getWidth() * tileSize, board.getHeight() * tileSize);
        canvasGraphics = canvas.getGraphicsContext2D();

        canvasHolder.getChildren().add(canvas);
        root.getChildren().add(canvasHolder);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
                    double mouseX = event.getX();
                    double mouseY = event.getY();

                    int x = -1;
                    int y = -1;

                    for (int testX = 0; testX < board.getWidth(); testX++) {
                        if (mouseX > testX * tileSize && mouseX < testX * tileSize + tileSize) {
                            for (int testY = 0; testY < board.getHeight(); testY++) {
                                if (mouseY > testY * tileSize && mouseY < testY * tileSize + tileSize) {
                                    x = testX;
                                    y = testY;
                                    break;
                                }
                            }
                            break;
                        }
                    }

                    if (x != -1) {
                        clock.openBrainViewOfSnake(x, y);
                    }
                }
            }
        });
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.SPACE)) {
                    clock.pauseOrResume();
                } else if (keyEvent.getCode().equals(KeyCode.UP)) {
                    clock.incSpeed();
                } else if (keyEvent.getCode().equals(KeyCode.DOWN)) {
                    clock.decSpeed();
                }
            }
        });

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                clock.pleaseStop();
                clock.pauseOrResume();
            }
        });
    }

    public void draw(Board board) {
        //Draw background
        canvasGraphics.setFill(BoardView.BACKGROUND_COLOR);
        canvasGraphics.fillRect(0, 0, board.getWidth() * tileSize, board.getHeight() * tileSize);

        //Draw snakes
        for (Snake snake : board.getPopulation()) {
            //Draw head segment
            canvasGraphics.setFill(BoardView.SNAKE_HEAD_COLOR);
            SnakeSegment segment = snake.getSegments().get(0);
            canvasGraphics.fillRect(tileSize * segment.getX(), tileSize * segment.getY(), tileSize, tileSize);

            //Draw tail segments
            canvasGraphics.setFill(BoardView.SNAKE_COLOR);
            for (int i = 1; i < snake.getSegments().size(); i++) {
                segment = snake.getSegments().get(i);
                canvasGraphics.fillRect(tileSize * segment.getX(), tileSize * segment.getY(), tileSize, tileSize);
            }
        }

        //Draw food
        canvasGraphics.setFill(BoardView.FOOD_COLOR);
        for (ArrayList<Integer> foodLocation : board.getFoodLocations()) {
            canvasGraphics.fillRect(foodLocation.get(0) * tileSize, foodLocation.get(1) * tileSize, tileSize, tileSize);
        }

        //Draw info
        int currentBestFitness = board.getTwoBestSnakes().get(0).getFitness();
        if (currentBestFitness > bestFitness) {
            bestFitness = currentBestFitness;
        }
        canvasGraphics.setFill(Color.WHITE);
        canvasGraphics.fillText("Snakes died: " + board.getSnakesDied(), 20, 30);
//        graphics.fillText("Generation: " + generation, 20, 30);
        canvasGraphics.fillText("Best fitness: " + bestFitness, 20, 30 + canvasGraphics.getFont().getSize() + 4);
        canvasGraphics.fillText("Best cur. fitness: " + currentBestFitness, 20, 30 + 2 * (canvasGraphics.getFont().getSize() + 4));
        canvasGraphics.setFill(Color.BLACK);
    }
}
