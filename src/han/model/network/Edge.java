package han.model.network;

import han.view.Graphics;
import han.view.network.NetworkView;
import javafx.scene.paint.Color;

import java.text.DecimalFormat;
import java.text.NumberFormat;
/**
 * Created by han on 16-11-16.
 * Class representing an edge between two nodes
 */
public class Edge {

    /**
     * Private Variables
     */
    private Node sourceNode;
    private Node destinationNode;
    private double weight;
    private Graphics graphics;
    public static NumberFormat formatter = new DecimalFormat("#0.00");

    /**
     * Constants
     */
    public static final int MIN_WEIGHT = -1;
    public static final int MAX_WEIGHT = 1;
    public static final double CHANCE_TO_GET_WEIGHT = 0.6;
    public static final double WEIGHT_CHANCE_EDGES_MODIFIER = 1.2;
    public static final double WEIGHT_CHANCE_PEERS_MODIFIER = 2;
    public static final double WEIGHT_CHANCE_DESTINATIONS_MODIFIER = 4;

    /**
     * Updates the color and alpha of the edge
     */
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setGraphics(Graphics graphics) {
        this.graphics = graphics;
    }

    public String toString() {
        String result = "";
        result = result + "[" + sourceNode.getGraphics().locationToString() + ", "
                + destinationNode.getGraphics().locationToString() + ", "
                + String.valueOf(formatter.format(weight)) + "]\t\t";
        return result;
    }
}
