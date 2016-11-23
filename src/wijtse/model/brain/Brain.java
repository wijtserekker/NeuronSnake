package wijtse.model.brain;

import java.util.ArrayList;

/**
 * Created by wijtse on 16-11-16.
 */
public class Brain {

    private ArrayList<Neuron> inputLayer;
    private ArrayList<Neuron> outputLayer;
    private ArrayList<ArrayList<Neuron>> hiddenLayers;

    public Brain(BrainDimensions dimensions, ArrayList<Double> dna) {
        //Create layers
        inputLayer = new ArrayList<>();
        for (int i = 0; i < dimensions.getNrOfInputNeurons(); i++) {
            inputLayer.add(new Neuron());
        }

        outputLayer = new ArrayList<>();
        for (int i = 0; i < dimensions.getNrOfOutputNeurons(); i++) {
            outputLayer.add(new Neuron());
        }

        hiddenLayers = new ArrayList<>();
        for (int i = 0; i < dimensions.getNrOfHiddenLayers(); i++) {
            hiddenLayers.add(new ArrayList<>());
            for (int j = 0; j < dimensions.getNeuronsPerHiddenLayer(); j++) {
                hiddenLayers.get(i).add(new Neuron());
            }
        }

        int dnaAllele = 0;
        //Link layers
        for (Neuron inputNeuron : inputLayer) {
            for (Neuron hiddenNeuron : hiddenLayers.get(0)) {
                inputNeuron.addAxon(new Axon(dna.get(dnaAllele), hiddenNeuron));
                dnaAllele++;
            }
        }

        for (int i = 0; i < dimensions.getNrOfHiddenLayers() - 1; i++) {
            for (Neuron srcNeuron : hiddenLayers.get(i)) {
                for (Neuron destNeuron : hiddenLayers.get(i + 1)) {
                    srcNeuron.addAxon(new Axon(dna.get(dnaAllele), destNeuron));
                    dnaAllele++;
                }
            }
        }

        for (Neuron hiddenNeuron : hiddenLayers.get(dimensions.getNrOfHiddenLayers() - 1)) {
            for (Neuron outputNeuron : outputLayer) {
                hiddenNeuron.addAxon(new Axon(dna.get(dnaAllele), outputNeuron));
                dnaAllele++;
            }
        }
    }

    public ArrayList<Double> think(ArrayList<Double> inputValues) {
        ArrayList<Double> result = new ArrayList<>();
        //Propagate signal through brain

        //Set input values
        for (int i = 0; i < inputLayer.size(); i++) {
            inputLayer.get(i).setValue(inputValues.get(i));
        }

        //Reset hidden layers
        for (ArrayList<Neuron> hiddenLayer : hiddenLayers) {
            for (Neuron hiddenNeuron : hiddenLayer) {
                hiddenNeuron.setValue(0.0);
            }
        }

        //Reset output layer
        for (Neuron outputNeuron : outputLayer) {
            outputNeuron.setValue(0.0);
        }

        //===============================================================
        //Propagate signal from the input layer to the first hidden layer
        for (Neuron inputNeuron : inputLayer) {
            for (Axon axon : inputNeuron.getAxons()) {
                axon.getDestNeuron().addToValue(inputNeuron.getValue() * axon.getWeight());
            }
        }
        //Apply activation function to the first hidden layer
        for (Neuron hiddenNeuron : hiddenLayers.get(0)) {
            hiddenNeuron.applyActivationFunction();
        }

        //Propagate signal from the hidden layers to the output layer
        for (int i = 0; i < hiddenLayers.size(); i++) {
            for (Neuron hiddenNeuron : hiddenLayers.get(i)) {
                for (Axon axon : hiddenNeuron.getAxons()) {
                    axon.getDestNeuron().addToValue(hiddenNeuron.getValue() * axon.getWeight());
                }
            }
            //Apply activation function to the following layer
            if (i + 1 < hiddenLayers.size()) {
                for (Neuron hiddenNeuron : hiddenLayers.get(i + 1)) {
                    hiddenNeuron.applyActivationFunction();
                }
            } else {
                for (Neuron outputNeuron : outputLayer) {
                    outputNeuron.applyActivationFunction();
                }
            }
        }

        //===============================================================
        //Get result from the output layer
        for (Neuron outputNeuron : outputLayer) {
            result.add(outputNeuron.getValue());
        }

        return result;
    }

    public ArrayList<Neuron> getInputLayer() {
        return inputLayer;
    }

    public ArrayList<Neuron> getOutputLayer() {
        return outputLayer;
    }

    public ArrayList<ArrayList<Neuron>> getHiddenLayers() {
        return hiddenLayers;
    }
}
