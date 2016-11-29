package wijtse.model.brain.neat;

import wijtse.model.brain.neat.dna.AxonGene;
import wijtse.model.brain.neat.dna.DNA;
import wijtse.model.brain.neat.dna.NeuronGene;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by wijtse on 28-11-16.
 */
public class Brain {

    private DNA dna;
    private HashMap<Integer, Neuron> neurons;
    private ArrayList<Integer> inputNeurons;
    private ArrayList<Integer> outputNeurons;

    public Brain(DNA dna) {
        this.dna = dna;
        neurons = new HashMap<>();
        inputNeurons = new ArrayList<>();
        outputNeurons = new ArrayList<>();

        //Create neurons
        for (NeuronGene neuronGene : dna.getNeuronGenes()) {
            if (neuronGene.isEnabled()) {
                neurons.put(neuronGene.getNumber(), new Neuron());
                if (neuronGene.getType() == NeuronGene.Type.INPUT) {
                    inputNeurons.add(neuronGene.getNumber());
                } else if (neuronGene.getType() == NeuronGene.Type.OUTPUT) {
                    outputNeurons.add(neuronGene.getNumber());
                }
            }
        }

        //Create connections
        for (AxonGene axonGene : dna.getAxonGenes()) {
            if (axonGene.isEnabled()) {
                Neuron srcNeuron = neurons.get(axonGene.getSrcNeuron());
                Neuron destNeuron = neurons.get(axonGene.getDestNeuron());
                Axon axon = new Axon(axonGene.getWeight(), srcNeuron, destNeuron);
                srcNeuron.addOutgoingAxon(axon);
                destNeuron.addIncomingAxon(axon);
            }
        }
    }

    public ArrayList<Double> think(ArrayList<Double> input) {
        ArrayList<Double> result = new ArrayList<>();

        //Reset the brain
        for (int neuron : neurons.keySet()) {
            neurons.get(neuron).reset();
        }

        //Present the inputs to the input neurons
        int i = 0;
        for (int inputNeuron : inputNeurons) {
            neurons.get(inputNeuron).setValue(input.get(i));
            i++;
        }

        //Calculate the output
        for (int outputNeuron : outputNeurons) {
            result.add(neurons.get(outputNeuron).calculateValue());
        }

        return result;
    }

    public HashMap<Integer, Neuron> getNeurons() {
        return neurons;
    }

    public ArrayList<Integer> getInputNeurons() {
        return inputNeurons;
    }

    public ArrayList<Integer> getOutputNeurons() {
        return outputNeurons;
    }
}