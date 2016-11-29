package wijtse.model.brain.neat;

import java.util.ArrayList;

/**
 * Created by wijtse on 28-11-16.
 */
public class Neuron {

    private double value;
    private ArrayList<Axon> incomingAxons;
    private ArrayList<Axon> outgoingAxons;
    private boolean calculated;

    public Neuron() {
        value = 0.0;
        calculated = false;
        incomingAxons = new ArrayList<>();
        outgoingAxons = new ArrayList<>();
    }

    public void addIncomingAxon(Axon axon) {
        incomingAxons.add(axon);
    }

    public void addOutgoingAxon(Axon axon) {
        incomingAxons.add(axon);
    }

    public void setValue(double value) {
        this.value = value;
        calculated = true;
    }

    public ArrayList<Axon> getIncomingAxons() {
        return incomingAxons;
    }

    public ArrayList<Axon> getOutgoingAxons() {
        return outgoingAxons;
    }

    public double calculateValue() {
        if (!calculated) {
            for (Axon incomingAxon : incomingAxons) {
                value += incomingAxon.getWeight() * incomingAxon.getSrcNeuron().calculateValue();
            }
            applyActivationFunction();
            calculated = true;
        }
        return value;
    }

    private void applyActivationFunction() {
        value = 1.0/(1.0 + Math.exp(-1 * value));
    }

    public void reset() {
        value = 0.0;
        calculated = false;
    }
}
