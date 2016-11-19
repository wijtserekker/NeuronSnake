package wijtse.model.brain;

import java.util.ArrayList;

/**
 * Created by wijtse on 17-11-16.
 */
public class GeneticAlgorithm {

    private static final double MUTATION_RATE = 0.2;

    private int dnaSize;

    public GeneticAlgorithm(int dnaSize) {
        this.dnaSize = dnaSize;
    }

    public ArrayList<Double> getRandomDNA() {
        ArrayList<Double> result = new ArrayList<>();

        for (int i = 0; i < dnaSize; i++) {
            result.add(Math.random()*8-4);
        }

        return result;
    }

    public ArrayList<Double> mutate(ArrayList<Double> dna) {
        ArrayList<Double> result = new ArrayList<>();

        for (double allele : dna) {
            if (Math.random() < MUTATION_RATE) {
                //Mutate allele
                result.add(Math.random()*8-4);
            } else {
                result.add(allele);
            }
        }

        return result;
    }

    public ArrayList<Double> breed(ArrayList<Double> dnaParentOne, ArrayList<Double> dnaParentTwo) {
        ArrayList<Double> result = new ArrayList<>();

        for (int i = 0; i < dnaSize; i++) {
            result.add(Math.random() > 0.5 ? dnaParentOne.get(i) : dnaParentTwo.get(i));
        }

        return result;
    }
}
