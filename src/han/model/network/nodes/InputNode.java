package han.model.network.nodes;

import han.model.network.Edge;
import han.model.network.Node;
import han.view.Graphics;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by han on 16-11-16.
 * Input nodes in input layer of network
 */
public class InputNode implements Node {

    private List<Edge> edgeList = new ArrayList<>();
    private Graphics graphics;

    public InputNode(int x, int y) {
        this.graphics = new Graphics(x, y, new Color(170.0/255, 57.0/255, 57.0/255, 1));
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
