package wijtse.model.brain.standard;

import java.util.ArrayList;

/**
 * Created by wijtse on 16-11-16.
 */
public class Neuron {

    private double value;
    private ArrayList<Axon> axons;

    public Neuron() {
        axons = new ArrayList<>();
        value = 0;
    }

    public void addToValue(double x) {
        value += x;
    }

    public void addAxon(Axon axon) {
        axons.add(axon);
    }

    public void applyActivationFunction() {
        value = 1.0/(1.0 + Math.exp(-1 * value));
    }

    public ArrayList<Axon> getAxons() {
        return axons;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

}
