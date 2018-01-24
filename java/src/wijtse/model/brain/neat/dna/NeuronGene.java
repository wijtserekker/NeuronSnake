package wijtse.model.brain.neat.dna;

/**
 * Created by wijtse on 28-11-16.
 */
public class NeuronGene {

    public enum Type {
        INPUT, HIDDEN, OUTPUT
    }

    private int number;
    private boolean enabled;
    private Type type;

    public NeuronGene(int number, boolean enabled, Type type) {
        this.number = number;
        this.enabled = enabled;
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public Type getType() {
        return type;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
