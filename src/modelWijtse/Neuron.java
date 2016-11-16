package modelWijtse;

import java.util.ArrayList;

/**
 * Created by wijtse on 16-11-16.
 */
public class Neuron {

    private double value;
    private ArrayList<Axon> axons;

    public Neuron() {
        axons = new ArrayList<>();
    }

    public void addAxon(Axon axon) {
        axons.add(axon);
    }

    public ArrayList<Axon> getAxons() {
        return axons;
    }

}
