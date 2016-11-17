package han.model.network;

import han.view.Graphics;
import javafx.scene.paint.Color;

import java.util.Random;

/**
 * Created by han on 16-11-16.
 * Class representing an edge between two nodes
 */
public class Edge {

    private Node sourceNode;
    private Node destinationNode;
    private double weight;
    private Graphics graphics;

    public static final int MIN_WEIGHT = -1;
    public static final int MAX_WEIGHT = 1;

    public static final double CHANCE_TO_GET_WEIGHT = 0.25;

    public Edge(Node sourceNode, Node destinationNode) {
        this.sourceNode = sourceNode;
        Color color = null;
        this.destinationNode = destinationNode;
        Random random = new Random();
        if (random.nextDouble() < CHANCE_TO_GET_WEIGHT) {
            this.weight = MIN_WEIGHT + (MAX_WEIGHT - MIN_WEIGHT) * random.nextDouble();
            if (weight > 0) {
                color = new Color(1 - weight, 1, 1 - weight, 1);
            } else if (weight < 0) {
                color = new Color(1, 1 + weight, 1 + weight , 1);
            }
        } else {
            this.weight = 0;
            color = new Color(0, 0, 0, 0);
        }
        this.graphics = new Graphics(color, 2);
    }

    public Node getSourceNode() {
        return sourceNode;
    }

    public void setSourceNode(Node node) {
        this.sourceNode = node;
    }

    public Node getDestinationNode() {
        return destinationNode;
    }

    public void setDestinationNode(Node node) {
        this.destinationNode = node;
    }

    public Graphics getGraphics() {
        return graphics;
    }
}
