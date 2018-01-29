from han.exception import IllegalValueException


class Network:
    """Class that represents the network model."""

    def __init__(self, input_nodes: 'int', hidden_node_groups: 'int', hidden_nodes: 'int', output_nodes: 'int',
                 weight_function):
        """Initialization function for the Network class. input_nodes is the amount of input nodes in the network,
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
        self.nodes = []
        self.edges = []

        self.node_counter = 0
        self.edge_counter = 0

        # Fill all lists with nodes.

        # Input nodes.
        for i in range(0, input_nodes):
            node = Node(self.node_counter)
            self.node_counter += 1
            self.input_nodes.append(node)
            self.nodes.append(node)

        # Hidden nodes.
        for i in range(0, hidden_node_groups):
            temp_hidden_node_group = []
            for j in range(0, hidden_nodes):
                node = Node(self.node_counter)
                self.node_counter += 1
                temp_hidden_node_group.append(node)
                self.nodes.append(node)
            self.hidden_node_groups.append(temp_hidden_node_group)

        # Output nodes.
        for i in range(0, output_nodes):
            node = Node(self.node_counter)
            self.node_counter += 1
            self.output_nodes.append(node)
            self.nodes.append(node)

        # Create all edges between nodes.

        # Create edges from input nodes to the first group of hidden nodes.
        for input_node in self.input_nodes:
            for hidden_node in self.hidden_node_groups[0]:
                edge = Edge(input_node, hidden_node, 1, self.edge_counter)
                self.edge_counter += 1
                self.edges.append(edge)
                hidden_node.add_input_edge(edge)

        # Create edges from hidden nodes.
        for i in range(0, len(self.hidden_node_groups)):
            hidden_node_group = self.hidden_node_groups[i]

            # Edges between hidden node groups.
            if i < len(self.hidden_node_groups) - 1:
                hidden_node_target_group = self.hidden_node_groups[i + 1]
                for hidden_node in hidden_node_group:
                    for hidden_target_node in hidden_node_target_group:
                        edge = Edge(hidden_node, hidden_target_node, 1, self.edge_counter)
                        self.edge_counter += 1
                        self.edges.append(edge)
                        hidden_target_node.add_input_edge(edge)

            # Edges from the last hidden node group to the output node.
            elif i == len(self.hidden_node_groups) - 1:
                for hidden_node in hidden_node_group:
                    for output_node in self.output_nodes:
                        edge = Edge(hidden_node, output_node, 1, self.edge_counter)
                        self.edge_counter += 1
                        self.edges.append(edge)
                        output_node.add_input_edge(edge)

        # Assign weights to the edges
        weight_function(self.edges)


class Node:
    """Class that represents an node in the model."""

    def __init__(self, id):
        """Initialization function for the Node class."""

        # Initialize attributes.
        self.value = 0
        self.input_edges = []
        self.id = id

    def add_input_edge(self, edge):
        """Function to add an output edge to this node."""

        self.input_edges.append(edge)

    def __str__(self):
        return "Node(" + str(self.value) + ", " + str(self.id) + ")"

    def __repr__(self):
        return self.__str__()


class Edge:
    """Class that represents an edge in the model."""

    def __init__(self, source_node: 'Node', destination_node: 'Node', weight: 'float', id):
        """Initialization function for the Edge class. source_node is the source node of the edge, destination_node is
        the destination node of the thread, weight is the weight of the edge.
        """

        # Set attributes.
        self.source_node = source_node
        self.destination_node = destination_node
        self.weight = weight
        self.id = id

    def __str__(self):
        return "Edge(" + str(self.source_node) + ", " + str(self.destination_node) + ", " + str(self.weight) \
               + ", " + str(self.id) + ")"

    def __repr__(self):
        return self.__str__()
