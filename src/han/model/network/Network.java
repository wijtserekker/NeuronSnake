package han.model.network;

import han.controller.network.NetworkController;

import java.util.*;

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
    private HashMap<Integer, Edge> edgeList = new HashMap<>();
    private int totalAmountOfGroups = 0;
    private List<Integer> dimensions = new ArrayList<>();

    private HashMap<Integer, Edge> a = new HashMap<>();
    private HashMap<Integer, Edge> b = new HashMap<>();

    /**
     * Constructor for the network when provided with all the variable amount of nodes per node group
     *
     * @param amountOfInputNodes       Amount of input nodes in input node group
     * @param amountOfHiddenNodeGroups Amount of hidden node group
     * @param amountOfHiddenNodes      Amount of hidden Nodes per hidden node group
     * @param amountOfOutputNodes      Amount of output Nodes per output node group
     */
    public Network(int amountOfInputNodes, int amountOfHiddenNodeGroups, int amountOfHiddenNodes,
                   int amountOfOutputNodes) {
        addToDimensions(amountOfInputNodes, amountOfHiddenNodeGroups, amountOfHiddenNodes,
                amountOfOutputNodes);
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

        //TODO TESTING
        //edgeList = NetworkController.combineEdges(a, b);
        //System.out.println(a);
        //System.out.println(b);
        System.out.println(edgeList + "\nsize = " + edgeList.size() + "\nactive = " + getActiveEdges(edgeList));
    }

    /**
     * Adds the nodes to the network
     *
     * @param amountOfInputNodes       Amount of input nodes in input node group
     * @param amountOfHiddenNodeGroups Amount of hidden node group
     * @param amountOfHiddenNodes      Amount of hidden Nodes per hidden node group
     * @param amountOfOutputNodes      Amount of output Nodes per output node group
     */
    private void createNodes(int amountOfInputNodes, int amountOfHiddenNodeGroups, int amountOfHiddenNodes,
                             int amountOfOutputNodes) {
        this.totalAmountOfGroups = 1 + amountOfHiddenNodeGroups + 1;
        for (int i = 0; i < amountOfInputNodes; i++) {
            inputNodeGroup.add(new Node(0, i, Node.TypeOfNode.INPUT));
        }
        for (int i = 0; i < amountOfOutputNodes; i++) {
            outputNodeGroup.add(new Node(amountOfHiddenNodeGroups + 1, i, Node.TypeOfNode.OUTPUT));
        }
        for (int i = 0; i < amountOfHiddenNodeGroups; i++) {
            List<Node> hiddenNodeGroup = new ArrayList<>();
            for (int j = 0; j < amountOfHiddenNodes; j++) {
                hiddenNodeGroup.add(new Node(i + 1, j, Node.TypeOfNode.HIDDEN));
            }
            hiddenNodeGroupList.add(hiddenNodeGroup);
        }
    }

    /**
     * Create edges between each node in adjecent layers
     * @param sourceNodeList list of source nodes
     * @param destinationNodeList lst of destination nodes
     */
    private void createEdges(List<Node> sourceNodeList, List<Node> destinationNodeList) {
        edgeList.putAll(NetworkController.generateRandomEdges(sourceNodeList, destinationNodeList, this));
        //TODO TESTING
        //a.putAll(NetworkController.generateRandomEdges(sourceNodeList, destinationNodeList, this));
        //b.putAll(NetworkController.generateRandomEdges(sourceNodeList, destinationNodeList, this));
    }

    /**
     * Add the dimension to the list that stores them
     * @param a input
     * @param b hidden groups
     * @param c hiddens
     * @param d outputs
     */
    public void addToDimensions(int a, int b, int c, int d) {
        dimensions.add(a);
        dimensions.add(b);
        dimensions.add(c);
        dimensions.add(d);
    }

    /**
     * Reads and calculates different inputs and output signals
     */
    public void propagateSignal() {
        initStage();
        propagateStage();
        updateStage();
    }
    private void updateStage() {

        for (List<Node> hiddenNodeGroup : hiddenNodeGroupList) {
            for (Node hiddenNode : hiddenNodeGroup) {
                hiddenNode.applyActivationFunction();
                hiddenNode.updateColor();
                for (Edge edge : hiddenNode.getEdges()) {
                    edge.updateColor();
                }
            }
        }

        for (Node outputNode : outputNodeGroup) {
            outputNode.applyActivationFunction();
            outputNode.updateColor();
        }
    }
    private void propagateStage() {
        for (Node inputNode : inputNodeGroup) {
            if (inputNode.getStrength() > 0) {
                for (Edge edge : inputNode.getEdges()) {
                    if (edge.getWeight() != 0) {
                        edge.getDestinationNode().addToInputSignals(edge.getWeight() * inputNode.getStrength());
                    }
                }
            }
        }

        for (List<Node> hiddenNodeGroup : hiddenNodeGroupList) {
            for (Node hiddenNode : hiddenNodeGroup) {
                hiddenNode.applyActivationFunction();
                hiddenNode.updateColor();
                for (Edge edge : hiddenNode.getEdges()) {
                    edge.updateColor();
                }
                if (hiddenNode.getStrength() > 0) {
                    for (Edge edge : hiddenNode.getEdges()) {
                        if (edge.getWeight() != 0) {
                            edge.getDestinationNode().addToInputSignals(edge.getWeight() * hiddenNode.getStrength());
                        }
                    }
                }
            }
        }
    }
    private void initStage() {
        Random random = new Random();
        for (Node inputNode : inputNodeGroup) {
            //TODO This function currently sets the inputs to a random double, this should be from surroundings
            //inputNode.getInputStrenght() or so
            double rand = random.nextDouble();
            if (rand < 0.5) {
                inputNode.setStrength(0);
            } else {
                inputNode.setStrength(random.nextDouble());
            }
            inputNode.updateColor();
            for (Edge edge : inputNode.getEdges()) {
                edge.updateColor();
            }
        }

        //Reset hidden nodes
        for (List<Node> hiddenNodeGroup : hiddenNodeGroupList) {
            for (Node hiddenNode : hiddenNodeGroup) {
                hiddenNode.setStrength(0);
                hiddenNode.setInputSignals(0);
            }
        }

        //Reset output nodes
        for (Node outputNode : outputNodeGroup) {
            outputNode.setStrength(0);
            outputNode.setInputSignals(0);
        }
    }

    public int getAmountOfInputNodes() {
        return dimensions.get(0);
    }

    public int getAmountOfHiddenNodeGroups() {
        return dimensions.get(1);
    }

    public int getAmountOfHiddenNodes() {
        return dimensions.get(2);
    }

    public int getAmountOfOutputNodes() {
        return dimensions.get(3);
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

    public HashMap<Integer, Edge> getEdgeList() {
        return edgeList;
    }

    public int getTotalAmountOfGroups() {
        return totalAmountOfGroups;
    }

    public int getActiveEdges(HashMap<Integer, Edge> in) {
        int result = 0;
        Set<Integer> keySet = in.keySet();
        for (Integer i : keySet) {
            if (in.get(i).getWeight() != 0) {
                result++;
            }
        }
        return result;
    }

    public void setInputNodeGroup(List<Node> inputNodeGroup) {
        this.inputNodeGroup = inputNodeGroup;
    }
}
