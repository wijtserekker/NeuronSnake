package han.model;

import java.util.List;

/**
 * Created by han on 16-11-16.
 * Interface for nodes
 */
public interface Node {

    List<Edge> getEdges();
    void addEdge(Edge edge);

}
