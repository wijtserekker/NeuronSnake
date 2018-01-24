from han.exception import IllegalValueException
from han.model.network.edge import Edge
from han.model.network.node import Node


class Network:
    """Class that represents the network model."""

    def __init__(self, input_nodes: 'int', hidden_node_groups: 'int', hidden_nodes: 'int', output_nodes: 'int'):
        """Initialization funciton for the Network class. input_nodes is the amount of input nodes in the network,
        hidden_node_groups is the amount of hidden node groups, hidden_nodes is the amount of hidden nodes that are
        in each group, and output_nodes is the amount of output nodes in the network.
        """

        # Make sure legal values are used.
        if input_nodes < 0 or hidden_node_groups < 1 or hidden_nodes < 1 or output_nodes < 1:
            raise IllegalValueException('All arguments need to be greater than 0 when constructing the network!')

        # Initialize lists.
        self.input_nodes = []
        self.hidden_node_groups = []
        self.output_nodes = []
        self.edges = []

        # Fill all lists with nodes.

        # Input nodes.
        for i in range(0, input_nodes):
            self.input_nodes.append(Node())

        # Hidden nodes.
        for i in range(0, hidden_node_groups):
            temp_hidden_node_group = []
            for j in range(0, hidden_nodes):
                temp_hidden_node_group.append(Node())
            self.hidden_node_groups.append(temp_hidden_node_group)

        # Output nodes.
        for i in range(0, output_nodes):
            self.output_nodes.append(Node())

        # Create all edges between nodes.

        # Create edges from input nodes to the first group of hidden nodes.
        for input_node in self.input_nodes:
            for hidden_node in self.hidden_node_groups[0]:
                edge = Edge(input_node, hidden_node, 1)
                self.edges.append(edge)
                input_node.add_output_edge(edge)

        # Create edges from hidden nodes.
        for i in range(0, len(self.hidden_node_groups)):
            hidden_node_group = hidden_node_groups[i]

            # Edges between hidden node groups.
            if i < len(self.hidden_node_groups) - 1:
                hidden_node_target_group = hidden_node_groups[i + 1]
                for hidden_node in hidden_node_group:
                    for hidden_target_node in hidden_node_target_group:
                        edge = Edge(hidden_node, hidden_target_node, 1)
                        self.edges.append(edge)
                        hidden_node.add_output_edge(edge)

            # Edges from the last hidden node group to the output node.
            elif i == len(self.hidden_node_groups) - 1:
                for hidden_node in hidden_node_group:
                    for output_node in self.output_nodes:
                        edge = Edge(hidden_node, output_node, 1)
                        self.edges.append(edge)
                        hidden_node.add_output_edge(edge)


