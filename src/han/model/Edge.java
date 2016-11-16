package han.model;

/**
 * Created by han on 16-11-16.
 * Class representing an edge between two nodes
 */
public class Edge {

    private Node sourceNode;
    private Node destinationNode;
    private double weight;

    public Edge(Node sourceNode, Node destinationNode) {
        this.sourceNode = sourceNode;
        this.destinationNode = destinationNode;
        this.weight = 0;
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

}
