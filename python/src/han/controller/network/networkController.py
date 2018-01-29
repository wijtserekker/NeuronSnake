import random

from han.model.network.networkModel import *
# from han.view.network.networkViewTkinter import *
from han.view.network.networkViewPyQt import *


# Value propagation functions.
def calculate_value_basic(node: 'Node'):
    """Function that calculates what the total input value of this node is, and set the node value to this."""
    # The basic input to a node is the sum of the values of its predecessors multiplied by the weight of each edge.
    node.value = 0
    for edge in node.input_edges:
        node.value += edge.source_node.value * edge.weight


# Weight assignment functions.
def assign_weights_random(edges: '[Edge]'):
    """Function that assigns initial weights to edges purely randomly."""
    for edge in edges:
        edge.weight = random.random() * 2 - 1


# Network model properties.
INPUT_NODES = 15                            # Amount of input nodes
HIDDEN_NODE_GROUPS = 2                      # Amount of hidden node groups
HIDDEN_NODES = 10                           # Amount of hidden nodes per group
OUTPUT_NODES = 3                            # Amount of output nodes

# Network view properties.
W_WIDTH = 1280                              # Width of the window
W_HEIGHT = 920                              # Height of the window
NODE_SIZE = 30                              # Diameter of the node
EDGE_SIZE = 1                               # Thickness of the edge

VALUE_FUNCTION_BASIC = calculate_value_basic        # Basic value function

WEIGHT_FUNCTION_RANDOM = assign_weights_random      # Random weight function


class NetworkController:
    """NetworkController is a class that contains functions that can create, update and edit a network."""

    def __init__(self):
        """Initialization function for the NetworkController class."""

        # Initialize the attributes.
        self.network = Network(INPUT_NODES, HIDDEN_NODE_GROUPS, HIDDEN_NODES, OUTPUT_NODES, WEIGHT_FUNCTION_RANDOM)

        # self.network_view = NetworkView(W_WIDTH, W_HEIGHT)
        # self.network_view.init_network(self.network.input_nodes, self.network.hidden_node_groups,
        #                                self.network.output_nodes, self.network.edges, NODE_SIZE, EDGE_SIZE)

        self.network_view = NetworkView(self.network)

    def update_network(self):
        """Function that updates the network model and view."""

        # =========== Update model.

        # Update input nodes.
        # TODO input node update, depending on "game"

        # Update hidden nodes.
        for i in range(0, len(self.network.hidden_node_groups)):
            for hidden_node in self.network.hidden_node_groups[i]:
                VALUE_FUNCTION_BASIC(hidden_node)

        # Update output nodes
        for output_node in self.network.output_nodes:
                VALUE_FUNCTION_BASIC(output_node)

        # =========== Update view.

        self.network_view.update_network()

