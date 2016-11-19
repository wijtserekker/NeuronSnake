package han.view.game;

import han.controller.game.NeuronSnake;
import han.model.game.Board;
import han.model.game.Segment;
import han.model.network.Network;
import han.view.network.NetworkView;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import static han.model.game.Board.BOARD_SIZE_X;
import static han.model.game.Board.BOARD_SIZE_Y;

/**
 * Created by han on 18-11-16.
 * View for te gaem
 */
public class GameView extends Application {

    private Canvas canvas;

    public static final int GRID_SIZE = 24;

    private int canvasWidth = BOARD_SIZE_X * GRID_SIZE;
    private int canvasHeight = Board.BOARD_SIZE_Y * GRID_SIZE;
    private Board board;
    private NetworkView networkView;

    public GameView() {
        this.board = new Board();
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Network");

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        Group root = new Group();
        canvas = new Canvas(canvasWidth, canvasHeight);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        initGame(gc);
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void initGame(GraphicsContext gc) {
        gc.setFill((Color.BLACK));
        gc.fillRect(0, 0, canvasWidth, canvasHeight);
        Color color = new Color(.15, .15, .15, 1);
        gc.setStroke(color);
        gc.setLineWidth(1);
        for (int i = 0; i < BOARD_SIZE_X; i++) {
            for (int j = 0; j < BOARD_SIZE_Y; j++) {
                gc.strokeRect(i * GRID_SIZE, j * GRID_SIZE, GRID_SIZE, GRID_SIZE);
            }
        }

        for (Segment segment : board.getSnake().getSegments()) {
            gc.setFill(segment.getGraphics().getColor());
            gc.fillRect(segment.getLocation().getX() * GRID_SIZE, segment.getLocation().getY() * GRID_SIZE,
                    GRID_SIZE, GRID_SIZE);
        }
    }

    public Board getBoard() {
        return board;
    }

    public int getCanvasWidth() {
        return canvasWidth;
    }

    public int getCanvasHeight() {
        return canvasHeight;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public NetworkView getNetworkView() {
        return networkView;
    }

    public void setNetworkView(NetworkView networkView) {
        this.networkView = networkView;
    }
}
