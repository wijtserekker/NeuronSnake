package wijtse.controller;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import wijtse.model.brain.standard.BrainDimensions;
import wijtse.model.brain.standard.GeneticAlgorithm;
import wijtse.model.game.Board;
import wijtse.view.BoardView;

/**
 * Created by wijtse on 22-11-16.
 */
public class NeuronSnake {

    public static final int BOARD_WIDTH = 60;
    public static final int BOARD_HEIGHT = 60;
    public static final int POPULATION_SIZE = 7;
    public static final int MAX_FOOD = 40;
    public static final double FOOD_SPAWN_RATE = 1.0;
    public static final int TILE_SIZE = 15;

    public static final int INPUT_NEURONS = 15;
    public static final int OUTPUT_NEURONS = 2;
    public static final int HIDDEN_LAYERS = 1;
    public static final int NEURONS_PER_HIDDEN_LAYER = 15;
    private static final int DNA_SIZE = INPUT_NEURONS * NEURONS_PER_HIDDEN_LAYER + OUTPUT_NEURONS * NEURONS_PER_HIDDEN_LAYER + (HIDDEN_LAYERS - 1) * NEURONS_PER_HIDDEN_LAYER * NEURONS_PER_HIDDEN_LAYER;

    public static final BrainDimensions BRAIN_DIMENSIONS = new BrainDimensions(INPUT_NEURONS, OUTPUT_NEURONS, HIDDEN_LAYERS, NEURONS_PER_HIDDEN_LAYER);

    public static void main(String[] args) {

        //INIT toolkit
        JFXPanel panel = new JFXPanel();

        Board board = new Board(BOARD_WIDTH, BOARD_HEIGHT, POPULATION_SIZE, MAX_FOOD, FOOD_SPAWN_RATE, new GeneticAlgorithm(DNA_SIZE));
        Clock clock = new Clock(board);
        BoardView view = new BoardView(board, TILE_SIZE, clock);
        clock.setView(view);

        Platform.runLater(new Runnable() {
            public void run() {
                try {
                    view.start(new Stage());
                    view.draw(board);
                    clock.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
