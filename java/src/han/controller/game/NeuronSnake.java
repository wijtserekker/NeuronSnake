package han.controller.game;

import han.model.game.Board;
import han.model.network.Network;
import han.view.game.GameView;
import han.view.network.NetworkView;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;

/**
 * Created by han on 17-11-16.
 * Class with main method to start main
 */
public class NeuronSnake {

//    private static Network network = new Network(15, 1, 15, 2);
//    private static Board board = new Board();

    /**
     * Main method run when program is started
     * @param args Arguments that can be provided: none
     */
    public static void main(String[] args) {

        JFXPanel panel = new JFXPanel();

        NetworkView networkView = new NetworkView();
        GameView gameView = new GameView();

        networkView.setGameView(gameView);
        gameView.setNetworkView(networkView);

        Platform.runLater(new Runnable() {
            public void run() {
                try {
                    networkView.start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Platform.runLater(new Runnable() {
            public void run() {
                try {
                    gameView.start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Thread clockThread = new Thread(new Clock(networkView, gameView));
        clockThread.start();


        //TODO TESTING
        //Network network = new Network(15, 1, 15, 2);
        //NetworkView.startNetwork(network);
    }
//
//    public static Network getNetwork() {
//        return network;
//    }
//
//    public static void setNetwork(Network network) {
//        NeuronSnake.network = network;
//    }
//
//    public static Board getBoard() {
//        return board;
//    }
//
//    public static void setBoard(Board board) {
//        NeuronSnake.board = board;
//    }
}
