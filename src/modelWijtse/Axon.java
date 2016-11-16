package modelWijtse;

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

    public Neuron getDestNeuron() {
        return destNeuron;
    }
}
