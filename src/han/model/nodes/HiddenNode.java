package han.model.nodes;

import han.model.Edge;
import han.model.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by han on 16-11-16.
 */
public class HiddenNode implements Node {

    private List<Edge> edgeList = new ArrayList<>();

    @Override
    public List<Edge> getEdges() {
        return edgeList;
    }

    @Override
    public void addEdge(Edge edge) {
        edgeList.add(edge);
    }
}
