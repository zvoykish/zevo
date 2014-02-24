package com.zvoykish.zevo.framework.operators.crossover;

import com.zvoykish.zevo.framework.EvoConfiguration;
import com.zvoykish.zevo.framework.GenotypeFactory;
import com.zvoykish.zevo.framework.IndividualFactory;
import com.zvoykish.zevo.framework.entities.Individual;
import com.zvoykish.zevo.framework.genetics.Genotype;
import com.zvoykish.zevo.framework.operators.CrossOver;
import com.zvoykish.zevo.model.genetics.Gene;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 01/05/2010
 * Time: 01:37:10
 */
public class SingleCutCrossOver<ConcreteGene extends Gene> extends CrossOver<ConcreteGene> {

    public SingleCutCrossOver(EvoConfiguration configuration) {
        super(configuration);
    }

    @Override
    public Individual<ConcreteGene> actualPerformCrossOver(Individual<ConcreteGene> ind1,
                                                           Individual<ConcreteGene> ind2)
    {
        Genotype<ConcreteGene> g1 = ind1.getGenotype();
        Genotype<ConcreteGene> g2 = ind2.getGenotype();
        int len = g1.length();
        // Choosing the random cut - this way we enforce at least 1 gene swap in the
        // cross-over (index can be any number between 1 and len)
        int randomCutIndex = 1 + ((int) (Math.random() * (len - 1)));
        List<ConcreteGene> newGenes = new ArrayList<>();
        newGenes.addAll(g1.getSequence(0, randomCutIndex));
        newGenes.addAll(g2.getSequence(randomCutIndex));

        Genotype<ConcreteGene> newGenotype = GenotypeFactory.createNewGenotype(newGenes);
        return IndividualFactory.createNewIndividual(newGenotype);
    }
}
