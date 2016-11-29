package wijtse.model.brain.neat;

/**
 * Created by wijtse on 28-11-16.
 */
public class Axon {

    private double weight;
    private Neuron srcNeuron;
    private Neuron destNeuron;

    public Axon(double weight, Neuron srcNeuron, Neuron destNeuron) {
        this.weight = weight;
        this.srcNeuron = srcNeuron;
        this.destNeuron = destNeuron;
    }

    public double getWeight() {
        return weight;
    }

    public Neuron getSrcNeuron() {
        return srcNeuron;
    }

    public Neuron getDestNeuron() {
        return destNeuron;
    }
}
