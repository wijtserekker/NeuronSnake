package wijtse.view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import wijtse.controller.Clock;
import wijtse.model.game.Board;

/**
 * Created by wijtse on 18-11-16.
 */
public class BoardView extends Application {

    //http://paletton.com/#uid=7560Q1kBHfL-dbNAyec-djDzcnuk0joO1cFo1jmY3rkq7-jLkpYuPiXwemmv2vovpxLrxkv6mqJlq7B3olsgkkqlgU
    public static final Color SNAKE_COLOR = Color.rgb(246, 113, 46);
    public static final Color SNAKE_HEAD_COLOR = Color.rgb(219, 74, 0);
    public static final Color FOOD_COLOR = Color.rgb(3, 119, 81);
    public static final Color BACKGROUND_COLOR = Color.BLACK;

    private static final int BOARD_WIDTH = 30;
    private static final int BOARD_HEIGHT = 10;
    public static final int BOARD_TILE_SIZE = 20;

    private Group root;
    private StackPane canvasHolder;
    private Canvas canvas;
    private GraphicsContext canvasGraphics;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Network");
        root = new Group();
        canvasHolder = new StackPane();
        //Setup canvas
        canvas = new Canvas(BOARD_WIDTH * BOARD_TILE_SIZE, BOARD_HEIGHT * BOARD_TILE_SIZE);
        canvasGraphics = canvas.getGraphicsContext2D();

        canvasHolder.getChildren().add(canvas);
        root.getChildren().add(canvasHolder);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        Board board = new Board(BOARD_WIDTH, BOARD_HEIGHT, 1, 12, 0.01);
        Clock clock = new Clock(board, canvasGraphics);
        clock.start();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                clock.pleaseStop();
            }
        });
    }

}
