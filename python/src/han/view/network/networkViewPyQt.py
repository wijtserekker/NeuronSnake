import tkinter

import math
from PyQt5.QtGui import QPainter, QPen, QColor, QBrush
from PyQt5.QtWidgets import QWidget

from han.model.network.networkModel import Node, Edge


# Functions to determine colours of objects like nodes and edges
def get_edge_color(edge: 'Edge'):
    alpha = get_node_alpha(edge.source_node)
    weight = edge.weight
    weight_ff = int(255 - math.fabs(255 * edge.weight))
    print(weight)
    if weight < 0:
        return QColor(255, weight_ff, weight_ff, alpha)
    elif weight > 0:
        return QColor(weight_ff, 255, weight_ff, alpha)
    elif weight == 0:
        return QColor(0, 0, 0, alpha)


def get_node_alpha(node: 'Node'):
    return int(200 * math.fabs(node.value)) + 55


class NetworkView(QWidget):
    def __init__(self, width, height, input_nodes: '[Node]', hidden_node_groups: '[[Node]]', output_nodes: '[Node]',
                     edges: '[Edge]', node_size: 'int', edge_size: 'int'):
        QWidget.__init__(self)

        # Use Tkinter to get screen width and height.
        root = tkinter.Tk()
        root.withdraw()

        # Set dimension variables.
        s_width, s_height = root.winfo_screenwidth(), root.winfo_screenheight()

        # Apply dimensions to window.
        self.setGeometry((s_width - width) / 2, (s_height - height) / 2, width, height)
        self.setWindowTitle("Network view")

        self.show()

        self.input_nodes = input_nodes
        self.hidden_node_groups = hidden_node_groups
        self.output_nodes = output_nodes
        self.edges = edges
        self.node_size = node_size
        self.edge_size = edge_size

        self.drawings = {}

    def paintEvent(self, e):
        qp = QPainter()
        qp.begin(self)
        qp.setRenderHint(QPainter.Antialiasing)
        self.draw_network(qp)
        qp.end()

    def draw_network(self, qp):

        # General.
        total_groups = 2 + len(self.hidden_node_groups)
        offset_x = self.width() / (total_groups + 1)
        invis_pen = QPen(QColor(0, 0, 0, 0))
        input_r, input_g, input_b = 255, 0, 0
        hidden_r, hidden_g, hidden_b = 0, 255, 0
        output_r, output_g, output_b = 0, 0, 255

        # Draw black background.
        qp.setPen(invis_pen)
        qp.setBrush(QColor(0, 0, 0, 255))
        qp.drawRect(0, 0, self.width(), self.height())

        # Input nodes.
        qp.setPen(invis_pen)
        offset_x_input = offset_x
        offset_y_input = self.height() / (len(self.input_nodes) + 1)
        for i in range(0, len(self.input_nodes)):
            qp.setBrush(QColor(input_r, input_g, input_b, get_node_alpha(self.input_nodes[i])))
            x, y = offset_x_input - self.node_size / 2, (i + 1) * offset_y_input - self.node_size / 2
            qp.drawEllipse(x, y, self.node_size, self.node_size)
            self.drawings[self.input_nodes[i]] = Drawing((x, y, self.node_size, self.node_size))

        # Hidden nodes.
        qp.setPen(invis_pen)
        for i in range(0, len(self.hidden_node_groups)):
            offset_x_hidden = (i + 2) * offset_x
            offset_y_hidden = self.height() / (len(self.hidden_node_groups[i]) + 1)
            for j in range(0, len(self.hidden_node_groups[i])):
                qp.setBrush(QColor(hidden_r, hidden_g, hidden_b, get_node_alpha(self.hidden_node_groups[i][j])))
                x, y = offset_x_hidden - self.node_size / 2, (j + 1) * offset_y_hidden - self.node_size / 2
                qp.drawEllipse(x, y, self.node_size, self.node_size)
                self.drawings[self.hidden_node_groups[i][j]] = Drawing((x, y, self.node_size, self.node_size))

        # Output nodes.
        qp.setPen(invis_pen)
        offset_x_output = offset_x * total_groups
        offset_y_output = self.height() / (len(self.output_nodes) + 1)
        for i in range(0, len(self.output_nodes)):
            qp.setBrush(QColor(output_r, output_g, output_b, get_node_alpha(self.output_nodes[i])))
            x, y = offset_x_output - self.node_size / 2, (i + 1) * offset_y_output - self.node_size / 2
            qp.drawEllipse(x, y, self.node_size, self.node_size)
            self.drawings[self.output_nodes[i]] = Drawing((x, y, self.node_size, self.node_size))

        # Edges.
        for i in range(0, len(self.edges)):
            qp.setPen(get_edge_color(self.edges[i]))
            drawing_s = self.drawings.get(self.edges[i].source_node)
            drawing_d = self.drawings.get(self.edges[i].destination_node)
            x0 = drawing_s.x + drawing_s.w
            y0 = drawing_s.y + (drawing_s.h / 2)
            x1 = drawing_d.x
            y1 = drawing_d.y + (drawing_d.h / 2)
            qp.drawLine(x0, y0, x1, y1)
            self.drawings[self.edges[i]] = Drawing((x0, y0, x1, y1))


class Drawing:
    def __init__(self, coordinates):
        self.coordinates = coordinates
        self.x0 = coordinates[0]
        self.y0 = coordinates[1]
        self.x1 = coordinates[2]
        self.y1 = coordinates[3]
        self.x = self.x0
        self.y = self.y0
        self.w = self.x1
        self.h = self.y1

    def __str__(self):
        return "Drawing(" + str(self.coordinates) + ")"

    def __repr__(self):
        return self.__str__()
