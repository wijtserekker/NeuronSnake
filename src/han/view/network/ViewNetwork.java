package han.view.network;

import han.model.network.Edge;
import han.model.network.Network;
import han.model.network.Node;
import han.view.Graphics;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;

/**
 * Created by han on 16-11-16.
 * Simple view to visualize network
 */
public class ViewNetwork extends Application {

    private static Network network;

    public static int CANVAS_WIDTH = 1024;
    public static int CANVAS_HEIGHT = 768;
    public static int NODE_SIZE = 30;
    public static int X_STANDARD_OFFSET = 150;
    public static int Y_STANDARD_OFFSET = 100;

    public static void main(String[] args) {
        network = new Network(6, 3, 6, 5);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Network");
        Group root = new Group();

        StackPane canvasHolder = new StackPane();
        Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        canvasHolder.setStyle("-fx-background-color: black");

        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawShapes(gc);
        canvasHolder.getChildren().add(canvas);
        root.getChildren().add(canvasHolder);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void drawShapes(GraphicsContext gc) {
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1);

        for (int i = 0; i < network.getInputNodeGroup().size(); i++) {
            Node inputNode = network.getInputNodeGroup().get(i);
            Graphics graphics = inputNode.getGraphics();
            gc.setFill(graphics.getColor());
            int x = graphics.getPx();
            int y = graphics.getPy();
            gc.fillOval(x, y, NODE_SIZE, NODE_SIZE);
            gc.strokeText(graphics.locationToString(), x, y);
        }

        for (int i = 0; i < network.getHiddenNodeGroupList().size(); i++) {
            List<Node> hiddenNodeGroup = network.getHiddenNodeGroupList().get(i);
            for (int j = 0; j < hiddenNodeGroup.size(); j++) {
                Node hiddenNode = hiddenNodeGroup.get(j);
                Graphics graphics = hiddenNode.getGraphics();
                gc.setFill(graphics.getColor());
                int x = graphics.getPx();
                int y = graphics.getPy();
                System.out.println(graphics.locationToString());
                gc.fillOval(x, y, NODE_SIZE, NODE_SIZE);
                gc.strokeText(graphics.locationToString(), x, y);
            }

        }

        gc.setFill(Color.BLUE);

        for (int i = 0; i < network.getOutputNodeGroup().size(); i++) {
            Node outputNode = network.getOutputNodeGroup().get(i);
            Graphics graphics = outputNode.getGraphics();
            gc.setFill(graphics.getColor());
            int x = graphics.getPx();
            int y = graphics.getPy();
            gc.fillOval(x, y, NODE_SIZE, NODE_SIZE);
            gc.strokeText(graphics.locationToString(), x, y);
        }

        for (int i = 0; i < network.getEdgeList().size(); i++) {
            Edge edge = network.getEdgeList().get(i);
            Node sourceNode = edge.getSourceNode();
            Node destinationNode = edge.getDestinationNode();
            Graphics graphics = edge.getGraphics();
            gc.setStroke(graphics.getColor());
            gc.setLineWidth(graphics.getLw());
            int sx = sourceNode.getGraphics().getPx() + (int)(0.5 * NODE_SIZE);
            int sy = sourceNode.getGraphics().getPy() + (int)(0.5 * NODE_SIZE);
            int dx = destinationNode.getGraphics().getPx() + (int)(0.5 * NODE_SIZE);
            int dy = destinationNode.getGraphics().getPy() + (int)(0.5 * NODE_SIZE);
            gc.strokeLine(sx, sy, dx, dy);
        }


        /*
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        gc.strokeLine(40, 10, 10, 40);
        gc.fillOval(10, 60, 30, 30);
        gc.strokeOval(60, 60, 30, 30);
        gc.fillRoundRect(110, 60, 30, 30, 10, 10);
        gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
        gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
        gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
        gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
        gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
        gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
        gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
        gc.fillPolygon(new double[]{10, 40, 10, 40},
                new double[]{210, 210, 240, 240}, 4);
        gc.strokePolygon(new double[]{60, 90, 60, 90},
                new double[]{210, 210, 240, 240}, 4);
        gc.strokePolyline(new double[]{110, 140, 110, 140},
                new double[]{210, 210, 240, 240}, 4);
        */
    }
}

