package han.controller.network;

import han.model.network.Edge;
import han.model.network.Network;
import han.model.network.Node;
import han.view.network.Graphics;
import han.view.network.NetworkView;
import javafx.scene.paint.Color;

import java.util.*;

import static han.model.network.Edge.*;

/**
 * Created by han on 17-11-16.
 * class that can modify the network properties of a network
 */
public class NetworkController {

    public static final double CHANCE_OF_MUTATION = 0.1;

    public static HashMap<Integer, Edge> generateRandomEdges(List<Node> sourceNodeList, List<Node> destinationNodeList,
                                                             Network network) {
        HashMap<Integer, Edge> result = new HashMap<>();
        for (Node sourceNode : sourceNodeList) {
            Collections.shuffle(destinationNodeList);
            for (Node destinationNode : destinationNodeList) {
                Edge edge = new Edge();
                edge.setSourceNode(sourceNode);
                Color color = null;
                edge.setDestinationNode(destinationNode);
                Random random = new Random();
                int peers = 1;
                if (sourceNode.getTypeOfNode().equals(Node.TypeOfNode.INPUT)) {
                    peers = network.getAmountOfInputNodes();
                } else if (sourceNode.getTypeOfNode().equals(Node.TypeOfNode.HIDDEN)) {
                    peers = network.getAmountOfHiddenNodes();
                } else if (sourceNode.getTypeOfNode().equals(Node.TypeOfNode.OUTPUT)) {
                    peers = network.getAmountOfOutputNodes();
                }
                double chanceToGetWeight = CHANCE_TO_GET_WEIGHT /
                        (WEIGHT_CHANCE_DESTINATIONS_MODIFIER / destinationNodeList.size());
                if (sourceNode.getEdges().size() != 0) {
                    chanceToGetWeight = CHANCE_TO_GET_WEIGHT / ((peers * WEIGHT_CHANCE_PEERS_MODIFIER) *
                            (WEIGHT_CHANCE_EDGES_MODIFIER * sourceNode.getEdges().size()) *
                            (WEIGHT_CHANCE_DESTINATIONS_MODIFIER * destinationNodeList.size()));
                }
                if (random.nextDouble() < chanceToGetWeight) {
                    double weight = MIN_WEIGHT + (MAX_WEIGHT - MIN_WEIGHT) * random.nextDouble();
                    edge.setWeight(weight);
                    if (weight > 0) {
                        color = new Color(1 - weight, 1, 1 - weight,
                                NetworkView.MIN_ALPHA + ((1 - NetworkView.MIN_ALPHA) * sourceNode.getStrength()));
                    } else if (weight < 0) {
                        color = new Color(1, 1 + weight, 1 + weight,
                                NetworkView.MIN_ALPHA + ((1 - NetworkView.MIN_ALPHA) * sourceNode.getStrength()));
                    }
                } else {
                    edge.setWeight(0);
                    color = new Color(0, 0, 0, 0);
                }
                edge.setGraphics(new Graphics(color, 2));
                int sx = sourceNode.getGraphics().getX();
                int sy = sourceNode.getGraphics().getY();
                int dx = destinationNode.getGraphics().getX();
                int dy = destinationNode.getGraphics().getY();
                int position = cantorFunction(cantorFunction(sx, sy), cantorFunction(dx, dy));
                result.put(position, edge);
                edge.getSourceNode().addEdge(edge);
            }
        }
        return result;
    }

    public static HashMap<Integer, Edge> combineEdges(HashMap<Integer, Edge> a, HashMap<Integer, Edge> b) {
        HashMap<Integer, Edge> result = new HashMap<>();
        Random random = new Random();
        double r;
        Set<Integer> keySet = a.keySet();
        for (Integer i : keySet) {
            r = random.nextDouble();
            if (r < 0.5) {
                result.put(i, a.get(i));
            } else {
                result.put(i, b.get(i));
            }
        }
        return result;
    }

    public static HashMap<Integer, Edge> mutateEdges(HashMap<Integer, Edge> a) {
        HashMap<Integer, Edge> result = a;
        Random random = new Random();
        double r;
        Set<Integer> keySet = a.keySet();
        for (Integer i : keySet) {
            r = random.nextDouble();
            if (r < CHANCE_OF_MUTATION) {
                double weight = random.nextDouble();
                a.get(i).setWeight(weight);
            }
            result.put(i, a.get(i));
        }
        return result;
    }

    public static int cantorFunction(int a, int b) {
        return (int)((0.5 * (a + b) * (a + b + 1)) + b);
    }
}


