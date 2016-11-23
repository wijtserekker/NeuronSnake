package wijtse.model.brain;

/**
 * Created by wijtse on 22-11-16.
 */
public class BrainDimensions {

    private int inputNeurons;
    private int outputNeurons;
    private int nrOfHiddenLayers;
    private int neuronsPerHiddenLayer;

    public BrainDimensions(int inputNeurons, int outputNeurons, int nrOfHiddenLayers, int neuronsPerHiddenLayer) {
        this.inputNeurons = inputNeurons;
        this.outputNeurons = outputNeurons;
        this.nrOfHiddenLayers = nrOfHiddenLayers;
        this.neuronsPerHiddenLayer = neuronsPerHiddenLayer;
    }

    public int getNrOfInputNeurons() {
        return inputNeurons;
    }

    public int getNrOfOutputNeurons() {
        return outputNeurons;
    }

    public int getNrOfHiddenLayers() {
        return nrOfHiddenLayers;
    }

    public int getNeuronsPerHiddenLayer() {
        return neuronsPerHiddenLayer;
    }
}