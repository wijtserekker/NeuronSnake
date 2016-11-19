package han.view.network;

import han.controller.game.Clock;
import han.controller.game.NeuronSnake;
import han.model.network.Edge;
import han.model.network.Network;
import han.model.network.Node;
import han.view.game.GameView;
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
import javafx.scene.text.Font;
import javafx.stage.WindowEvent;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Set;

/**
 * Created by han on 16-11-16.
 * Simple view to visualize network
 */
public class NetworkView extends Application {

    /**
     * Class variables
     */
    private static Network network;
    public static NumberFormat formatter = new DecimalFormat("#0.00");

    /**
     * Constants
     */
    public static final int CANVAS_WIDTH = 400;
    public static final int CANVAS_HEIGHT = 250;
    public static final int NODE_SIZE_MODIFIER = 2;
    public static final int X_STANDARD_OFFSET = 0;
    public static final int Y_STANDARD_OFFSET = 20;
    public static final int EFFECTIVE_WIDTH = CANVAS_WIDTH - (2 * X_STANDARD_OFFSET);
    public static final int EFFECTIVE_HEIGHT = CANVAS_HEIGHT - (2 * Y_STANDARD_OFFSET);
    public static final boolean DISPLAY_COORDINATES = false;
    public static final boolean DISPLAY_STRENGTHS = false;
    public static final boolean DISPLAY_WEIGHTS = false;
    public static final double MIN_ALPHA = 0.18;
    public static final int SPACE_BETWEEN_LINES = 12;
    public static final double FONT_SIZE = 9;
    public static final int FONT_LINE_WIDTH = 1;
    public static final double WEIGHT_DISTANCE_TO_NODE = 0.3;
    public static final int STAGE_OFFSET = 100;

    private Canvas canvas;
    private GameView gameView;

    public NetworkView() {
        this.network = new Network(15, 1, 15, 2);
    }

    /**
     * Initialization of the network
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {

        gameView.getBoard().getSnake().setNetwork(network);

        this.network = NeuronSnake.getNetwork();

        primaryStage.setTitle("Network");

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        primaryStage.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() -
                (CANVAS_WIDTH + STAGE_OFFSET));
        primaryStage.setY(primaryScreenBounds.getMinY() + primaryScreenBounds.getHeight() -
                ((CANVAS_HEIGHT / 2) + (primaryScreenBounds.getHeight() / 2)));

        Group root = new Group();
        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);

        canvas.setFocusTraversable(true);

        //TODO
        canvas.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                    network.propagateSignal();
                    canvas.getGraphicsContext2D().setFill(Color.BLACK);
                    canvas.getGraphicsContext2D().fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
                    initNetwork(canvas.getGraphicsContext2D());
                } else if (keyEvent.getCode().equals(KeyCode.SPACE)) {
                    network = new Network (network.getAmountOfInputNodes(), network.getAmountOfHiddenNodeGroups(),
                            network.getAmountOfHiddenNodes(), network.getAmountOfOutputNodes());
                    gameView.getBoard().getSnake().setNetwork(network);
                    canvas.getGraphicsContext2D().setFill(Color.BLACK);
                    canvas.getGraphicsContext2D().fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
                    initNetwork(canvas.getGraphicsContext2D());
                }
            }
        });

        GraphicsContext gc = canvas.getGraphicsContext2D();
        initNetwork(gc);
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    public void setNetwork(Network network) {
        NetworkView.network = network;
    }

    public Network getNetwork() {
        return network;
    }

    /**
     * Draw the network
     * @param gc context of the canvas in the main window
     */
    public void initNetwork(GraphicsContext gc) {
        gc.setFill((Color.BLACK));
        gc.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1);
        gc.setFont(new Font(FONT_SIZE));

        int nodeXOffset = EFFECTIVE_WIDTH / (network.getTotalAmountOfGroups() + 1);

        int inputNodeYOffset = EFFECTIVE_HEIGHT / (network.getInputNodeGroup().size() + 1);
        for (int i = 0; i < network.getInputNodeGroup().size(); i++) {
            Node inputNode = network.getInputNodeGroup().get(i);
            Graphics graphics = inputNode.getGraphics();
            gc.setFill(graphics.getColor());
            int inputNodeSize = inputNodeYOffset / NODE_SIZE_MODIFIER;
            inputNode.getGraphics().setNodeSize(inputNodeSize);
            int x = X_STANDARD_OFFSET + ((graphics.getX() + 1) * nodeXOffset);
            int y = Y_STANDARD_OFFSET + ((graphics.getY() + 1) * inputNodeYOffset) - (int)(0.5 * inputNodeSize);
            inputNode.getGraphics().setPx(x);
            inputNode.getGraphics().setPy(y);
            gc.fillOval(x, y, inputNodeSize, inputNodeSize);
            if (DISPLAY_COORDINATES) {
                gc.strokeText(graphics.locationToString(), x, y);
            }
            if (DISPLAY_STRENGTHS) {
                gc.strokeText(formatter.format(inputNode.getStrength()), x, y + SPACE_BETWEEN_LINES);
            }
        }

        for (int i = 0; i < network.getHiddenNodeGroupList().size(); i++) {
            List<Node> hiddenNodeGroup = network.getHiddenNodeGroupList().get(i);
            int hiddenNodeYOffset = EFFECTIVE_HEIGHT / (hiddenNodeGroup.size() + 1);
            for (int j = 0; j < hiddenNodeGroup.size(); j++) {
                Node hiddenNode = hiddenNodeGroup.get(j);
                Graphics graphics = hiddenNode.getGraphics();
                gc.setFill(graphics.getColor());
                int hiddenNodeSize = hiddenNodeYOffset / NODE_SIZE_MODIFIER;
                hiddenNode.getGraphics().setNodeSize(hiddenNodeSize);
                int x = X_STANDARD_OFFSET + ((graphics.getX() + 1) * nodeXOffset);
                int y = Y_STANDARD_OFFSET + ((graphics.getY() + 1) * hiddenNodeYOffset) - (int)(0.5 * hiddenNodeSize);
                hiddenNode.getGraphics().setPx(x);
                hiddenNode.getGraphics().setPy(y);
                gc.fillOval(x, y, hiddenNodeSize, hiddenNodeSize);
                if (DISPLAY_COORDINATES) {
                    gc.strokeText(graphics.locationToString(), x, y);
                }
                if (DISPLAY_STRENGTHS) {
                    gc.strokeText(formatter.format(hiddenNode.getStrength()), x, y + SPACE_BETWEEN_LINES);
                }
            }

        }

        int outputNodeYOffset = EFFECTIVE_HEIGHT / (network.getOutputNodeGroup().size() + 1);
        for (int i = 0; i < network.getOutputNodeGroup().size(); i++) {
            Node outputNode = network.getOutputNodeGroup().get(i);
            Graphics graphics = outputNode.getGraphics();
            gc.setFill(graphics.getColor());
            int outputNodeSize = outputNodeYOffset / NODE_SIZE_MODIFIER;
            outputNode.getGraphics().setNodeSize(outputNodeSize);
            int x = X_STANDARD_OFFSET + ((graphics.getX() + 1) * nodeXOffset);
            int y = Y_STANDARD_OFFSET + ((graphics.getY() + 1) * outputNodeYOffset) - (int)(0.5 * outputNodeSize);
            outputNode.getGraphics().setPx(x);
            outputNode.getGraphics().setPy(y);
            gc.fillOval(x, y, outputNodeSize, outputNodeSize);
            if (DISPLAY_COORDINATES) {
                gc.strokeText(graphics.locationToString(), x, y);
            }
            if (DISPLAY_STRENGTHS) {
                gc.strokeText(formatter.format(outputNode.getStrength()), x, y + SPACE_BETWEEN_LINES);
            }
        }

        Set<Integer> keySet = network.getEdgeList().keySet();
        for (Integer i : keySet) {
            Edge edge = network.getEdgeList().get(i);
            Node sourceNode = edge.getSourceNode();
            Node destinationNode = edge.getDestinationNode();
            Graphics graphics = edge.getGraphics();
            gc.setStroke(graphics.getColor());
            gc.setLineWidth(graphics.getLw());
            int sx = sourceNode.getGraphics().getPx() + (int)(0.5 * sourceNode.getGraphics().getNodeSize());
            int sy = sourceNode.getGraphics().getPy() + (int)(0.5 * sourceNode.getGraphics().getNodeSize());
            int dx = destinationNode.getGraphics().getPx() + (int)(0.5 * destinationNode.getGraphics().getNodeSize());
            int dy = destinationNode.getGraphics().getPy() + (int)(0.5 * destinationNode.getGraphics().getNodeSize());
            gc.strokeLine(sx, sy, dx, dy);
            if (DISPLAY_WEIGHTS) {
                if (sourceNode.getStrength() > 0 && edge.getWeight() != 0) {
                    gc.setLineWidth(FONT_LINE_WIDTH);
                    gc.setStroke(Color.WHITE);
                    gc.strokeText(formatter.format(edge.getWeight()),
                            sx - (WEIGHT_DISTANCE_TO_NODE * (0.5 * (sx - dx))),
                            sy - (WEIGHT_DISTANCE_TO_NODE * (0.5 * (sy - dy))));
                }
            }
        }
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public GameView getGameView() {
        return gameView;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }
}

