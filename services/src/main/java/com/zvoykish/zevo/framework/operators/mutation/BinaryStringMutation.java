package com.zvoykish.zevo.framework.operators.mutation;

import com.zvoykish.zevo.framework.EvoConfiguration;
import com.zvoykish.zevo.framework.GenotypeFactory;
import com.zvoykish.zevo.framework.IndividualFactory;
import com.zvoykish.zevo.framework.entities.Individual;
import com.zvoykish.zevo.framework.genetics.Genotype;
import com.zvoykish.zevo.framework.operators.Mutation;
import com.zvoykish.zevo.model.genetics.BinaryGene;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 01/05/2010
 * Time: 02:12:36
 */

@SuppressWarnings("UnusedDeclaration")
public class BinaryStringMutation extends Mutation<BinaryGene> {
    public BinaryStringMutation(EvoConfiguration configuration) {
        super(configuration);
    }

    @Override
    public Individual<BinaryGene> doMutation(Individual<BinaryGene> individual) {
        Genotype<BinaryGene> genotype = individual.getGenotype();
        List<BinaryGene> newGenes = new ArrayList<>();
        for (BinaryGene gene : genotype.getGenes()) {
            if (Math.random() < getProbabilityForMutation()) {
                if (gene == null) {
                    throw new RuntimeException("Trying to binary-mutate a null gene!");
                }

                // Toggle its value and add it to new genes
                int value = gene.getValue();
                value = (value + 1) % 2;
                newGenes.add(new BinaryGene(value));
            }
            else {
                newGenes.add(gene);
            }
        }

        Genotype<BinaryGene> newGenotype = GenotypeFactory.createNewGenotype(newGenes);
        return IndividualFactory.createNewIndividual(newGenotype);
    }
}
