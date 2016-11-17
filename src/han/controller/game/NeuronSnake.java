package han.controller.game;

import han.model.network.Network;
import han.view.network.NetworkView;

/**
 * Created by han on 17-11-16.
 * Class with main method to start main
 */
public class NeuronSnake {

    public static void main(String[] args) {
        Network network = new Network(5, 2, 7, 2);
        NetworkView.startNetwork(network);
    }

}
