package wijtse.view;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import wijtse.model.brain.Axon;
import wijtse.model.brain.Brain;
import wijtse.model.brain.Neuron;

import java.util.ArrayList;

/**
 * Created by wijtse on 17-11-16.
 */
public class BrainView extends Application {

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
    private int layerGap;
    private int xMargin;
    private ArrayList<Integer> yMargins;

    private boolean firstGo = true;

    public BrainView(Brain brain) {
        setUpVariables(brain);
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
        //TODO PLEASE REMOVE ===============
        if (firstGo) {
            firstGo = false;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //TODO PLEASE REMOVE ================
        canvasGraphics.setFill(Color.BLACK);
        canvasGraphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        canvasGraphics.setStroke(Color.GREEN);
        canvasGraphics.setFill(Color.WHITE);

        //Draw input neurons
        for (int i = 0; i < brain.getInputLayer().size(); i++) {
            int axonNr = 0;
            for (Axon axon : brain.getInputLayer().get(i).getAxons()) {
                if (axon.getWeight() < 0) {
                    canvasGraphics.setStroke(Color.rgb(255, 0, 0, 0.1 + 0.9 * brain.getInputLayer().get(i).getValue()));
                } else {
                    canvasGraphics.setStroke(Color.rgb(0, 255, 0, 0.1 + 0.9 * brain.getInputLayer().get(i).getValue()));
                }
                canvasGraphics.strokeLine(xMargin + neuronSize / 2, yMargins.get(0) + i*2*neuronSize + neuronSize / 2, xMargin + neuronSize + layerGap + neuronSize / 2, yMargins.get(1) + axonNr*2*neuronSize + neuronSize / 2);
                axonNr++;
            }
            canvasGraphics.setFill(Color.rgb(255, 255, 255, 0.1 + 0.9 * brain.getInputLayer().get(i).getValue()));
            canvasGraphics.fillOval(xMargin, yMargins.get(0) + i*2*neuronSize, neuronSize, neuronSize);
        }

        //Draw hidden neurons
        for (int i = 0; i < brain.getHiddenLayers().size(); i++) {
            for (int j = 0; j < brain.getHiddenLayers().get(i).size(); j++) {
                int axonNr = 0;
                for (Axon axon : brain.getHiddenLayers().get(i).get(j).getAxons()) {
                    if (axon.getWeight() < 0) {
                        canvasGraphics.setStroke(Color.rgb(255, 0, 0, 0.1 + 0.9 * brain.getHiddenLayers().get(i).get(j).getValue()));
                    } else {
                        canvasGraphics.setStroke(Color.rgb(0, 255, 0, 0.1 + 0.9 * brain.getHiddenLayers().get(i).get(j).getValue()));
                    }
                    canvasGraphics.strokeLine(xMargin + (i+1) * (neuronSize + layerGap) + neuronSize / 2,
                            yMargins.get(i+1) + j*2*neuronSize + neuronSize / 2,
                            xMargin + (i+2) * (neuronSize + layerGap) + neuronSize / 2,
                            yMargins.get(i+2) + axonNr*2*neuronSize + neuronSize / 2);
                    axonNr++;
                }
                canvasGraphics.setFill(Color.rgb(255, 255, 255, 0.1 + 0.9 * brain.getHiddenLayers().get(i).get(j).getValue()));
                canvasGraphics.fillOval(xMargin + (i+1) * (neuronSize + layerGap), yMargins.get(i + 1) + j*2*neuronSize, neuronSize, neuronSize);
            }
        }

        //Draw output neurons
        for (int i = 0; i < brain.getOutputLayer().size(); i++) {
            canvasGraphics.setFill(Color.rgb(255, 255, 255, 0.1 + 0.9 * brain.getOutputLayer().get(i).getValue()));
            canvasGraphics.fillOval(xMargin + (1 + brain.getHiddenLayers().size()) * (neuronSize + layerGap), yMargins.get(yMargins.size() - 1) + i*2*neuronSize, neuronSize, neuronSize);
        }
    }

    private void setUpVariables(Brain brain) {
        int layers = 2 + brain.getHiddenLayers().size();
        int maxNeuronsPerLayer = brain.getInputLayer().size() > brain.getOutputLayer().size() ? brain.getInputLayer().size() : brain.getOutputLayer().size();
        for (ArrayList<Neuron> hiddenLayer : brain.getHiddenLayers()) {
            if (hiddenLayer.size() > maxNeuronsPerLayer) {
                maxNeuronsPerLayer = hiddenLayer.size();
            }
        }

        neuronSize = (int) ((0.50 * (CANVAS_HEIGHT - 2 * CANVAS_MIN_MARGIN)) / maxNeuronsPerLayer);
        if (neuronSize > MAX_NEURON_SIZE) {
            neuronSize = MAX_NEURON_SIZE;
        }

        layerGap = (int) ((0.9 * (CANVAS_WIDTH - 2 * CANVAS_MIN_MARGIN)) / layers);
        if (layerGap > MAX_LAYER_GAP) {
            layerGap = MAX_LAYER_GAP;
        }

        xMargin = (CANVAS_WIDTH - neuronSize * layers - layerGap * (layers - 1)) / 2;
        yMargins = new ArrayList<>();
        yMargins.add((CANVAS_HEIGHT - neuronSize * brain.getInputLayer().size() - neuronSize * (brain.getInputLayer().size() - 1)) / 2);
        for (ArrayList<Neuron> hiddenLayer : brain.getHiddenLayers()) {
            yMargins.add((CANVAS_HEIGHT - neuronSize * hiddenLayer.size() - neuronSize * (hiddenLayer.size() - 1)) / 2);
        }
        yMargins.add((CANVAS_HEIGHT - neuronSize * brain.getOutputLayer().size() - neuronSize * (brain.getOutputLayer().size() - 1)) / 2);
    }
}