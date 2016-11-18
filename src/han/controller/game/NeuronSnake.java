package han.controller.game;

import han.model.network.Network;
import han.view.network.NetworkView;

/**
 * Created by han on 17-11-16.
 * Class with main method to start main
 */
public class NeuronSnake {

    private static Network network = new Network(15, 1, 15, 2);
    private static NetworkView nwView = new NetworkView();

    /**
     * Main method run when program is started
     * @param args Arguments that can be provided: none
     */
    public static void main(String[] args) {

        //TODO do stuff to init


        Thread nwThread = new Thread(nwView);
        nwThread.start();

        Thread clockThread = new Thread(new Clock());
        clockThread.start();



        //TODO TESTING
        //Network network = new Network(15, 1, 15, 2);
        //NetworkView.startNetwork(network);
    }

    public static Network getNetwork() {
        return network;
    }

    public static void setNetwork(Network network) {
        NeuronSnake.network = network;
    }

    public static NetworkView getNwView() {
        return nwView;
    }
}
