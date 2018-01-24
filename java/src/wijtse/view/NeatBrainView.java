package wijtse.view;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import wijtse.model.brain.neat.Brain;
import wijtse.model.brain.neat.Neuron;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by wijtse on 29-11-16.
 */
public class NeatBrainView extends Application {

    private static final int MAX_NEURON_SIZE = 80;
    private static final int MAX_LAYER_GAP = 300;
    private static int CANVAS_WIDTH = 1024;
    private static int CANVAS_HEIGHT = 768;
    private static int CANVAS_MIN_MARGIN = 20;

    private Group root;
    private StackPane canvasHolder;
    private Canvas canvas;
    private GraphicsContext canvasGraphics;

    private int neuronSize;
    private ArrayList<Neuron> inputNeurons;
    private ArrayList<Neuron> hiddenNeurons;
    private ArrayList<Neuron> outputNeurons;

    public NeatBrainView(Brain brain) {
        for (int inputNeuron : brain.getInputNeurons()) {
            inputNeurons.add(brain.getNeurons().get(inputNeuron));
        }

        for (int outputNeuron : brain.getOutputNeurons()) {
            outputNeurons.add(brain.getNeurons().get(outputNeuron));
        }

        Set<Integer> neurons = brain.getNeurons().keySet();
        for (int neuron : neurons) {
            if (!inputNeurons.contains(neuron) && !outputNeurons.contains(neuron)) {
                hiddenNeurons.add(brain.getNeurons().get(neuron));
            }
        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Network");
        root = new Group();

        canvasHolder = new StackPane();
        //Setup canvas
        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        canvasGraphics = canvas.getGraphicsContext2D();
        canvasHolder.getChildren().add(canvas);
        root.getChildren().add(canvasHolder);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void draw(Brain brain) {

    }
}