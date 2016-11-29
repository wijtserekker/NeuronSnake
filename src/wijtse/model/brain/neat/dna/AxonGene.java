package wijtse.model.brain.neat.dna;

/**
 * Created by wijtse on 28-11-16.
 */
public class AxonGene {

    private int srcNeuron;
    private int destNeuron;
    private double weight;
    private int innovationNr;
    private boolean enabled;

    public AxonGene(int srcNeuron, int destNeuron, double weight, int innovationNr, boolean enabled) {
        this.srcNeuron = srcNeuron;
        this.destNeuron = destNeuron;
        this.weight = weight;
        this.innovationNr = innovationNr;
        this.enabled = enabled;
    }

    public int getSrcNeuron() {
        return srcNeuron;
    }

    public int getDestNeuron() {
        return destNeuron;
    }

    public double getWeight() {
        return weight;
    }

    public int getInnovationNr() {
        return innovationNr;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
