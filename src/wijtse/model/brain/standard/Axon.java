package wijtse.model.brain.standard;

/**
 * Created by wijtse on 16-11-16.
 */
public class Axon {

    private double weight;
    private Neuron destNeuron;

    public Axon(double weight, Neuron destNeuron) {
        this.weight = weight;
        this.destNeuron = destNeuron;
    }

    public double getWeight() {
        return weight;
    }

    public Neuron getDestNeuron() {
        return destNeuron;
    }
}
