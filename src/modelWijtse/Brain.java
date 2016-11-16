package modelWijtse;

import java.util.ArrayList;

/**
 * Created by wijtse on 16-11-16.
 */
public class Brain {

    private ArrayList<Neuron> inputLayer;
    private ArrayList<Neuron> outputLayer;
    private ArrayList<ArrayList<Neuron>> hiddenLayers;

    public Brain(int inputNeurons, int outputNeurons, int nrOfHiddenLayers, int neuronsPerHiddenLayer, String DNA) {
        //Create layers
        inputLayer = new ArrayList<>();
        for (int i = 0; i < inputNeurons; i++) {
            inputLayer.add(new Neuron());
        }

        outputLayer = new ArrayList<>();
        for (int i = 0; i < outputNeurons; i++) {
            outputLayer.add(new Neuron());
        }

        hiddenLayers = new ArrayList<>();
        for (int i = 0; i < nrOfHiddenLayers; i++) {
            hiddenLayers.add(new ArrayList<>());
            for (int j = 0; j < neuronsPerHiddenLayer; j++) {
                hiddenLayers.get(i).add(new Neuron());
            }
        }

        //Link layers
        for (Neuron inputNeuron : inputLayer) {
            for (Neuron hiddenNeuron : hiddenLayers.get(0)) {
                inputNeuron.addAxon(new Axon(0, hiddenNeuron)); //TODO replace hardcoded 0 with DNA shit
            }
        }

        for (int i = 0; i < nrOfHiddenLayers - 1; i++) {
            for (Neuron srcNeuron : hiddenLayers.get(i)) {
                for (Neuron destNeuron : hiddenLayers.get(i + 1)) {
                    srcNeuron.addAxon(new Axon(0, destNeuron)); //TODO replace hardcoded 0 with DNA shit
                }
            }
        }

        for (Neuron hiddenNeuron : hiddenLayers.get(nrOfHiddenLayers - 1)) {
            for (Neuron outputNeuron : outputLayer) {
                hiddenNeuron.addAxon(new Axon(0, outputNeuron)); //TODO replace hardcoded 0 with DNA shit
            }
        }
    }

}
