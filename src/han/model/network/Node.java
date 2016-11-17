package han.model.network;

import han.model.network.Edge;
import han.view.Graphics;
import han.view.network.NetworkView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by han on 16-11-16.
 * Hidden nodes in hidden layer of network
 */
public class Node {

    public enum TypeOfNode {
        INPUT, HIDDEN, OUTPUT;
    }

    public static final double INPUT_R = 170.0 / 255;
    public static final double INPUT_G = 57.0 / 255;
    public static final double INPUT_B = 57.0 / 255;

    public static final double HIDDEN_R = 133.0 / 255;
    public static final double HIDDEN_G = 199.0 / 255;
    public static final double HIDDEN_B = 94.0 / 255;

    public static final double OUTPUT_R = 100.0 / 255;
    public static final double OUTPUT_G = 100.0 / 255;
    public static final double OUTPUT_B = 180.0 / 255;


    private List<Edge> edgeList = new ArrayList<>();
    private Graphics graphics;
    private double strength;
    private TypeOfNode typeOfNode;

    public Node(int x, int y, TypeOfNode typeOfNode) {
        this.strength = 0;
        this.typeOfNode = typeOfNode;
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

    public Graphics getGraphics() {
        return graphics;
    }

    public List<Edge> getEdges() {
        return edgeList;
    }

    public void addEdge(Edge edge) {
        edgeList.add(edge);
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

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
}
