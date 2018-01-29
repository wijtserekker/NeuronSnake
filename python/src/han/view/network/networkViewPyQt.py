import sys
from PyQt5.QtWidgets import QApplication, QWidget
from PyQt5.QtGui import QIcon


class NetworkView(QWidget):

    def __init__(self, network):
        super().__init__()
        self.network = network

    def init_network(self, width, height):
        self.setGeometry(width, height)
        self.setWindowTitle
