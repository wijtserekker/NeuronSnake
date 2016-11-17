package han.model.network;

import han.view.Graphics;

import java.util.List;

/**
 * Created by han on 16-11-16.
 * Interface for nodes
 */
public interface Node {

    List<Edge> getEdges();
    void addEdge(Edge edge);
    Graphics getGraphics();

}
