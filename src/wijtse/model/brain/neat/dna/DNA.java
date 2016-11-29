package wijtse.model.brain.neat.dna;

import java.util.ArrayList;

/**
 * Created by wijtse on 28-11-16.
 */
public class DNA {


    private ArrayList<NeuronGene> neuronGenes;
    private ArrayList<AxonGene> axonGenes;

    public DNA(ArrayList<NeuronGene> neuronGenes, ArrayList<AxonGene> axonGenes) {
        this.neuronGenes = neuronGenes;
        this.axonGenes = axonGenes;
    }

    public ArrayList<NeuronGene> getNeuronGenes() {
        return neuronGenes;
    }

    public ArrayList<AxonGene> getAxonGenes() {
        return axonGenes;
    }
}
