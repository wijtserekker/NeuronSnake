package han.model.network;

import han.view.network.Graphics;
import han.view.network.NetworkView;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by han on 16-11-16.
 * Nodes in a layer of network
 */
public class Node {
    /**
     * Enum to indicate what type of node a node is
     */
    public enum TypeOfNode {
        INPUT, HIDDEN, OUTPUT;
    }

    /**
     * Constant values for the colors of different types of nodes
     */
    public static final double INPUT_R = 170.0 / 255;
    public static final double INPUT_G = 57.0 / 255;
    public static final double INPUT_B = 57.0 / 255;

    public static final double HIDDEN_R = 133.0 / 255;
    public static final double HIDDEN_G = 199.0 / 255;
    public static final double HIDDEN_B = 94.0 / 255;

    public static final double OUTPUT_R = 100.0 / 255;
    public static final double OUTPUT_G = 100.0 / 255;
    public static final double OUTPUT_B = 180.0 / 255;

    /**
     * Constants
     */
    public static final double ACTIVATION_MODIFIER = 5.0;

    /**
     * Private variables
     */
    private List<Edge> edgeList = new ArrayList<>();
    private Graphics graphics;
    private double strength;
    private TypeOfNode typeOfNode;
    private double inputSignals;

    /**
     * Constructor for a node
     * @param x the x coordinate of the node (kinda id)
     * @param y they y coord
     * @param typeOfNode indicates what type of node this is: input, hidden or output
     */
    public Node(int x, int y, TypeOfNode typeOfNode) {
        this.strength = 0;
        this.typeOfNode = typeOfNode;
        this.inputSignals = 0;
        if (typeOfNode.equals(TypeOfNode.INPUT)) {
            this.graphics = new Graphics(x, y, new Color(INPUT_R, INPUT_G, INPUT_B,
                    NetworkView.MIN_ALPHA + ((1 - NetworkView.MIN_ALPHA) * strength)));
        } else if (typeOfNode.equals(TypeOfNode.HIDDEN)) {
            this.graphics = new Graphics(x, y, new Color(HIDDEN_R, HIDDEN_G, HIDDEN_B,
                    NetworkView.MIN_ALPHA + ((1 - NetworkView.MIN_ALPHA) * strength)));
        } else if (typeOfNode.equals(TypeOfNode.OUTPUT)) {
            this.graphics = new Graphics(x, y, new Color(OUTPUT_R, OUTPUT_G, OUTPUT_B,
                    NetworkView.MIN_ALPHA + ((1 - NetworkView.MIN_ALPHA) * strength)));
        }

    }

    /**
     * Updates the color and alpha of the node
     */
    public void updateColor() {
        if (typeOfNode.equals(TypeOfNode.INPUT)) {
            graphics.setColor(new Color(INPUT_R, INPUT_G, INPUT_B,
                    NetworkView.MIN_ALPHA + ((1 - NetworkView.MIN_ALPHA) * strength)));
        } else if (typeOfNode.equals(TypeOfNode.HIDDEN)) {
            graphics.setColor(new Color(HIDDEN_R, HIDDEN_G, HIDDEN_B,
                    NetworkView.MIN_ALPHA + ((1 - NetworkView.MIN_ALPHA) * strength)));
        } else if (typeOfNode.equals(TypeOfNode.OUTPUT)) {
            graphics.setColor(new Color(OUTPUT_R, OUTPUT_G, OUTPUT_B,
                    NetworkView.MIN_ALPHA + ((1 - NetworkView.MIN_ALPHA) * strength)));
        }
    }

    /**
     * Apply the activation function so that the strenght is always between 0 and 1
     */
    public void applyActivationFunction() {
        //System.out.println("Node\t\t\t\t\t"+ graphics.locationToString());
        //System.out.println("\tInput signals \t\t" + inputSignals + " / " + (ACTIVATION_MODIFIER * inputSignals));
        strength = 1.0/(1.0 + Math.exp(-1 * (ACTIVATION_MODIFIER * inputSignals)));
        //System.out.println("\tStrength (a)\t\t" + strength);
    }

    /**
     * Adds a new edge to the list
     * @param edge edge to be added
     */
    public void addEdge(Edge edge) {
        edgeList.add(edge);
    }

    /**
     * Add value d to input field
     * @param d value to increment with
     */
    public void addToInputSignals(double d) {
        this.inputSignals += d;
    }

    public TypeOfNode getTypeOfNode() {
        return typeOfNode;
    }

    public double getInputSignals() {
        return inputSignals;
    }

    public void setInputSignals(double inputSignals) {
        this.inputSignals = inputSignals;
    }

    public Graphics getGraphics() {
        return graphics;
    }

    public List<Edge> getEdges() {
        return edgeList;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

}
