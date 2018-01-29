from threading import Thread
from tkinter import *

import math

from han.model.network.networkModel import Node, Edge


# Functions to determine colours of objects like nodes and edges
def get_edge_color(weight):
    weight_ff = int(255 - math.fabs(255 * weight))
    if weight < 0:
        colour = '#' + 'ff' + format(weight_ff, '02x') + format(weight_ff, '02x')
        return colour
    elif weight > 0:
        colour = '#' + format(weight_ff, '02x') + 'ff' + format(weight_ff, '02x')
        return colour
    elif weight == 0:
        return '#000000'


class NetworkView:

    def __init__(self, width, height):
        super().__init__()

        self.root = Tk()

        self.main_canvas = Canvas(self.root, width=width, height=height, bg='black')
        self.main_canvas.pack()
        self.root.update()

        self.input_drawings = {}
        self.hidden_group_drawings = []
        self.output_drawings = {}
        self.edge_drawings = {}
        self.node_drawings = {}

    def init_network(self, input_nodes: '[Node]', hidden_node_groups: '[[Node]]', output_nodes: '[Node]',
                     edges: '[Edge]', node_size: 'int', edge_size: 'int'):
        self.main_canvas.delete('all')

        # General.
        total_groups = 2 + len(hidden_node_groups)
        offset_x = self.main_canvas.winfo_width() / (total_groups + 1)

        # Input nodes.
        offset_x_input = offset_x
        offset_y_input = self.main_canvas.winfo_height() / (len(input_nodes) + 1)
        for i in range(0, len(input_nodes)):
            x0, y0, x1, y1 = offset_x_input - node_size / 2, (i + 1) * offset_y_input - node_size / 2, \
                             offset_x_input + node_size / 2, (i + 1) * offset_y_input + node_size / 2
            drawing = self.main_canvas.create_oval(x0, y0, x1, y1, fill="red")
            node_drawing = Drawing(drawing, (x0, y0, x1, y1))
            self.input_drawings[input_nodes[i]] = node_drawing
            self.node_drawings[input_nodes[i]] = node_drawing

        # Hidden nodes.
        for i in range(0, len(hidden_node_groups)):
            offset_x_hidden = (i + 2) * offset_x
            offset_y_hidden = self.main_canvas.winfo_height() / (len(hidden_node_groups[i]) + 1)
            self.hidden_group_drawings.append({})
            for j in range(0, len(hidden_node_groups[i])):
                x0, y0, x1, y1 = offset_x_hidden - node_size / 2, (j + 1) * offset_y_hidden - node_size / 2, \
                                 offset_x_hidden + node_size / 2, (j + 1) * offset_y_hidden + node_size / 2
                drawing = self.main_canvas.create_oval(x0, y0, x1, y1, fill="green")
                node_drawing = Drawing(drawing, (x0, y0, x1, y1))
                self.hidden_group_drawings[i][hidden_node_groups[i][j]] = node_drawing
                self.node_drawings[hidden_node_groups[i][j]] = node_drawing

        # Output nodes.
        offset_x_output = total_groups * offset_x
        offset_y_output = self.main_canvas.winfo_height() / (len(output_nodes) + 1)
        for i in range(0, len(output_nodes)):
            x0, y0, x1, y1 = offset_x_output - node_size / 2, (i + 1) * offset_y_output - node_size / 2, \
                             offset_x_output + node_size / 2, (i + 1) * offset_y_output + node_size / 2
            drawing = self.main_canvas.create_oval(x0, y0, x1, y1, fill="blue")
            node_drawing = Drawing(drawing, (x0, y0, x1, y1))
            self.output_drawings[output_nodes[i]] = node_drawing
            self.node_drawings[output_nodes[i]] = node_drawing

        # Edges.
        for i in range(0, len(edges)):
            x0 = self.node_drawings.get(edges[i].source_node).x1
            y0 = self.node_drawings.get(edges[i].source_node).middle_y
            x1 = self.node_drawings.get(edges[i].destination_node).x0
            y1 = self.node_drawings.get(edges[i].destination_node).middle_y
            drawing = self.main_canvas.create_line(x0, y0, x1, y1, fill="white", width=edge_size)
            self.edge_drawings[edges[i]] = Drawing(drawing, (x0, y0, x1, y1))

        self.main_canvas.pack()
        self.root.update()

    def update_network(self):
        for node, draw in self.node_drawings.items():
            pass
            # To move, Canvas.move(...)
            #self.main_canvas.itemconfig(draw.drawing, )
        for edge, draw in self.edge_drawings.items():
            colour = get_edge_color(edge.weight)
            self.main_canvas.itemconfig(draw.drawing, fill=colour)

        self.main_canvas.pack()
        self.root.update()

    def get_node_color(self, value):
        pass


class Drawing:
    def __init__(self, drawing, coordinates):
        self.drawing = drawing
        self.coordinates = coordinates
        self.x0 = coordinates[0]
        self.y0 = coordinates[1]
        self.x1 = coordinates[2]
        self.y1 = coordinates[3]
        self.middle_x = (self.x0 + self.x1) / 2
        self.middle_y = (self.y0 + self.y1) / 2

    def __str__(self):
        return "Drawing(" + str(self.coordinates) + ")"

    def __repr__(self):
        return self.__str__()
