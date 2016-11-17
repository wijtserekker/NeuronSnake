package han.model.network.nodes;

import han.model.network.Edge;
import han.model.network.Node;
import han.view.Graphics;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by han on 16-11-16.
 * Output node in output layer of network
 */
public class OutputNode implements Node {

    private List<Edge> edgeList = new ArrayList<>();
    private Graphics graphics;

    public OutputNode(int x, int y) {
        this.graphics = new Graphics(x, y, new Color(86.0/255, 86.0/255, 148.0/255, 1));
    }

    public Graphics getGraphics() {
        return graphics;
    }

    @Override
    public List<Edge> getEdges() {
        return edgeList;
    }

    @Override
    public void addEdge(Edge edge) {
        edgeList.add(edge);
    }
}
