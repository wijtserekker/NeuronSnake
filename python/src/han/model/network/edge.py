from han.model.network.node import Node


class Edge:
    """Class that represents an edge in the model."""

    def __init__(self, source_node: 'Node', destination_node: 'Node', weight: 'float'):
        self.source_node = source_node
        self.destination_node = destination_node
        self.weight = weight
