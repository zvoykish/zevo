package com.zvoykish.zevo.framework.operators.crossover;

import com.zvoykish.zevo.framework.EvoConfiguration;
import com.zvoykish.zevo.framework.GenotypeFactory;
import com.zvoykish.zevo.framework.IndividualFactory;
import com.zvoykish.zevo.framework.entities.Individual;
import com.zvoykish.zevo.framework.genetics.Genotype;
import com.zvoykish.zevo.framework.operators.CrossOver;
import com.zvoykish.zevo.model.genetics.Gene;
import com.zvoykish.zevo.utils.Randomizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 07/05/2010
 * Time: 02:16:58
 */
@SuppressWarnings("UnusedDeclaration")
public class TwoIntervalsCrossOver extends CrossOver {
    public TwoIntervalsCrossOver(EvoConfiguration configuration) {
        super(configuration);
    }

    @Override
    public Individual actualPerformCrossOver(Individual ind1, Individual ind2) {
        Genotype genotype1 = ind1.getGenotype();
        Genotype genotype2 = ind2.getGenotype();
        int len = genotype1.length();

        // Randomize 4 indices (i1, i2, i3, i4) on input genotypes to form 2 intervals for
        // cross-over: (i1, i2) and (i3, i4)
        List<Integer> intervalsPoints = Randomizer.randomize(4, 0, len - 1);
        Collections.sort(intervalsPoints);
        int i1 = intervalsPoints.get(0);
        int i2 = intervalsPoints.get(1);
        int i3 = intervalsPoints.get(2);
        int i4 = intervalsPoints.get(3);

        List<Gene> newGenes = new ArrayList<>();
        if (i1 > 0) {
            newGenes.addAll(genotype1.getSequence(0, i1));
        }
        newGenes.addAll(genotype2.getSequence(i1, i2));
        newGenes.addAll(genotype1.getSequence(i2, i3));
        newGenes.addAll(genotype2.getSequence(i3, i4));
        newGenes.addAll(genotype1.getSequence(i4));

        Genotype newGenotype = GenotypeFactory.createNewGenotype(newGenes);
        return IndividualFactory.createNewIndividual(newGenotype);
    }
}
