package han.model.network;

import han.view.Graphics;
import han.view.network.NetworkView;
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

    public static final double CHANCE_TO_GET_WEIGHT = 0.75;
    public static final double WEIGHT_CHANCE_EDGES_MODIFIER = 0.7;
    public static final double WEIGHT_CHANCE_PEERS_MODIFIER = 1.2;

    public Edge(Node sourceNode, Node destinationNode, Network network) {
        this.sourceNode = sourceNode;
        Color color = null;
        this.destinationNode = destinationNode;
        Random random = new Random();
        double chanceToGetWeight = CHANCE_TO_GET_WEIGHT;
        int peers = 1;
        if (sourceNode.getTypeOfNode().equals(Node.TypeOfNode.INPUT)) {
            peers = network.getAmountOfInputNodes();
        } else if (sourceNode.getTypeOfNode().equals(Node.TypeOfNode.HIDDEN)) {
            peers = network.getAmountOfHiddenNodes();
        } else if (sourceNode.getTypeOfNode().equals(Node.TypeOfNode.OUTPUT)) {
            peers = network.getAmountOfOutputNodes();
        }
        if (sourceNode.getEdges().size() != 0) {
            chanceToGetWeight = CHANCE_TO_GET_WEIGHT / ((peers * WEIGHT_CHANCE_PEERS_MODIFIER) *
                    (WEIGHT_CHANCE_EDGES_MODIFIER * sourceNode.getEdges().size()) );
        }
        if (random.nextDouble() < chanceToGetWeight) {
            this.weight = MIN_WEIGHT + (MAX_WEIGHT - MIN_WEIGHT) * random.nextDouble();
            if (weight > 0) {
                color = new Color(1 - weight, 1, 1 - weight,
                        NetworkView.MIN_ALPHA + ((1 - NetworkView.MIN_ALPHA) * sourceNode.getStrength()));
            } else if (weight < 0) {
                color = new Color(1, 1 + weight, 1 + weight ,
                        NetworkView.MIN_ALPHA + ((1 - NetworkView.MIN_ALPHA) * sourceNode.getStrength()));
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

    public void updateColor() {
        Color color = null;
        if (weight > 0) {
            color = new Color(1 - weight, 1, 1 - weight,
                    NetworkView.MIN_ALPHA + ((1 - NetworkView.MIN_ALPHA) * sourceNode.getStrength()));
        } else if (weight < 0) {
            color = new Color(1, 1 + weight, 1 + weight ,
                    NetworkView.MIN_ALPHA + ((1 - NetworkView.MIN_ALPHA) * sourceNode.getStrength()));
        } else {
            this.weight = 0;
            color = new Color(0, 0, 0, 0);
        }
        graphics.setColor(color);
    }

    public double getWeight() {
        return weight;
    }
}
