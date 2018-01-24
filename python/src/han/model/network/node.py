from han.model.network.edge import Edge


class Node:
    """Class that represents an node in the model."""

    def __init__(self):
        self.value = 0
        self.output_edges = []

    def add_output_edge(self, edge: 'Edge'):
        self.output_edges.append(edge)

    def calculate_input_basic(self):
        self.value = 0
        for edge in self.output_edges:
            self.value += edge.source_node.value * edge.weight

