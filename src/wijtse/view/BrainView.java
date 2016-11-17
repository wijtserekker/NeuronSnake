package wijtse.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import wijtse.model.brain.Axon;
import wijtse.model.brain.Brain;
import wijtse.model.brain.GeneticAlgorithm;
import wijtse.model.brain.Neuron;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wijtse on 17-11-16.
 */
public class BrainView extends Application {

    private static final int MAX_NEURON_SIZE = 80;
    private static final int MAX_LAYER_GAP = 300;
    private static int CANVAS_WIDTH = 1024;
    private static int CANVAS_HEIGHT = 500;//768;
    private static int CANVAS_MIN_MARGIN = 20;

    private static Brain brain;
    private static GeneticAlgorithm geneticAlgorithm;
    private static Object monitor = new Object();
    private static boolean running = true;

    private Group root;
    private StackPane canvasHolder;
    private Canvas canvas;
    private GraphicsContext canvasGraphics;

    private int neuronSize;
    private int layerGap;
    private int xMargin;
    private ArrayList<Integer> yMargins;

    public static void main(String[] args) {
        int inputNeurons = 15;
        int outputNeurons = 2;
        int hiddenLayers = 2;
        int neuronsPerHiddenLayer = 15;

        int axons = inputNeurons * neuronsPerHiddenLayer + outputNeurons * neuronsPerHiddenLayer + (hiddenLayers - 1) * neuronsPerHiddenLayer * neuronsPerHiddenLayer;

        geneticAlgorithm = new GeneticAlgorithm(axons);

        brain = new Brain(inputNeurons, outputNeurons, hiddenLayers, neuronsPerHiddenLayer, geneticAlgorithm.getRandomDNA());
        new Thread() {
            public void run() {
                BrainView.pleaseStart(args);
            }
        }.start();
        while(true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ArrayList<Double> input = new ArrayList<>();
            for (int i = 0; i < inputNeurons; i++) {
                input.add(Math.random() > 0.5 ? 1.0 : 0.0);
            }
            long startTime = new Date().getTime();
            ArrayList<Double> output = brain.think(input);
            long endTime = new Date().getTime();
            System.out.println(endTime - startTime + " " + output);
            BrainView.updateCanvas();
        }
    }

    public static void pleaseStart(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Network");
        root = new Group();

        canvasHolder = new StackPane();
        //Setup canvas
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        canvasGraphics = canvas.getGraphicsContext2D();

        setUpVariables();
        draw();

        canvasHolder.getChildren().add(canvas);
        root.getChildren().add(canvasHolder);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        System.out.println("hi");
        new Thread() {
            @Override
            public void run() {
                while (running) {
                    synchronized (monitor) {
                        try {
                            monitor.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        draw();
                    }
                }
            }
        }.start();
        System.out.println("ho");
    }

    private void draw() {
        canvasGraphics.setFill(Color.BLACK);
        canvasGraphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        canvasGraphics.setStroke(Color.GREEN);
        canvasGraphics.setFill(Color.WHITE);

        //Draw input neurons
        for (int i = 0; i < brain.getInputLayer().size(); i++) {
            int axonNr = 0;
            for (Axon axon : brain.getInputLayer().get(i).getAxons()) {
                if (axon.getWeight() < 0) {
                    canvasGraphics.setStroke(Color.RED);
                }
                canvasGraphics.strokeLine(xMargin + neuronSize / 2, yMargins.get(0) + i*2*neuronSize + neuronSize / 2, xMargin + neuronSize + layerGap + neuronSize / 2, yMargins.get(1) + axonNr*2*neuronSize + neuronSize / 2);
                axonNr++;
                canvasGraphics.setStroke(Color.GREEN);
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
                        canvasGraphics.setStroke(Color.RED);
                    }
                    canvasGraphics.strokeLine(xMargin + (i+1) * (neuronSize + layerGap) + neuronSize / 2,
                            yMargins.get(i+1) + j*2*neuronSize + neuronSize / 2,
                            xMargin + (i+2) * (neuronSize + layerGap) + neuronSize / 2,
                            yMargins.get(i+2) + axonNr*2*neuronSize + neuronSize / 2);
                    axonNr++;
                    canvasGraphics.setStroke(Color.GREEN);
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

    private void setUpVariables() {
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

    public static void updateCanvas() {
        synchronized (monitor) {
            monitor.notify();
        }
    }

    public static void stopLoop() {
        running = false;
        synchronized (monitor) {
            monitor.notify();
        }
    }
}