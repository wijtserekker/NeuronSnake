package han.model.network;

import han.model.network.nodes.HiddenNode;
import han.model.network.nodes.InputNode;
import han.model.network.nodes.OutputNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by han on 16-11-16.
 * Network class containing nodes of the network
 */
public class Network {

    /**
     * Lists to store the nodes that make up the network
     */
    private List<Node> inputNodeGroup = new ArrayList<>();
    private List<List<Node>> hiddenNodeGroupList = new ArrayList<>();
    private List<Node> outputNodeGroup = new ArrayList<>();
    private List<Edge> edgeList = new ArrayList<>();

    /**
     * Constructor for the network when provided with all the variable amount of nodes per node group
     * @param amountOfInputNodes Amount of input nodes in input node group
     * @param amountOfHiddenNodeGroups Amount of hidden node group
     * @param amountOfHiddenNodes Amount of hidden Nodes per hidden node group
     * @param amountOfOutputNodes Amount of output Nodes per output node group
     */
    public Network(int amountOfInputNodes, int amountOfHiddenNodeGroups, int amountOfHiddenNodes,
                   int amountOfOutputNodes) {
        createNodes(amountOfInputNodes, amountOfHiddenNodeGroups, amountOfHiddenNodes, amountOfOutputNodes);
        try {
            createEdges(inputNodeGroup, hiddenNodeGroupList.get(0));
        } catch (IndexOutOfBoundsException e) {
            System.out.println("There should at least be one hidden node group");
        }
        for (int i = 1; i < hiddenNodeGroupList.size(); i++) {
            createEdges(hiddenNodeGroupList.get(i - 1), hiddenNodeGroupList.get(i));
        }
        createEdges(hiddenNodeGroupList.get(hiddenNodeGroupList.size() - 1), outputNodeGroup);
    }

    /**
     * Adds the nodes to the network
     * @param amountOfInputNodes Amount of input nodes in input node group
     * @param amountOfHiddenNodeGroups Amount of hidden node group
     * @param amountOfHiddenNodes Amount of hidden Nodes per hidden node group
     * @param amountOfOutputNodes Amount of output Nodes per output node group
     */
    private void createNodes(int amountOfInputNodes, int amountOfHiddenNodeGroups, int amountOfHiddenNodes,
                             int amountOfOutputNodes) {
        for (int i = 0; i < amountOfInputNodes; i++) {
            inputNodeGroup.add(new InputNode(0, i));
        }
        for (int i = 0; i < amountOfOutputNodes; i++) {
            outputNodeGroup.add(new OutputNode(amountOfHiddenNodeGroups + 1, i));
        }
        for (int i = 0; i < amountOfHiddenNodeGroups; i++) {
            List<Node> hiddenNodeGroup = new ArrayList<>();
            for (int j = 0; j < amountOfHiddenNodes; j++) {
                hiddenNodeGroup.add(new HiddenNode(i + 1, j));
            }
            hiddenNodeGroupList.add(hiddenNodeGroup);
        }
    }

    private void createEdges(List<Node> sourceNodeList, List<Node> destinationNodeList) {
        for (Node sourceNode : sourceNodeList) {
            for (Node destinationNode : destinationNodeList) {
                Edge edge = new Edge(sourceNode, destinationNode);
                sourceNode.addEdge(edge);
                edgeList.add(edge);
            }
        }
    }

    public List<Node> getInputNodeGroup() {
        return inputNodeGroup;
    }

    public List<List<Node>> getHiddenNodeGroupList() {
        return hiddenNodeGroupList;
    }

    public List<Node> getOutputNodeGroup() {
        return outputNodeGroup;
    }

    public List<Edge> getEdgeList() {
        return edgeList;
    }

}
