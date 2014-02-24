package com.zvoykish.zevo.framework.operators.selection;

import com.zvoykish.zevo.framework.EvoConfiguration;
import com.zvoykish.zevo.framework.EvoFunctions;
import com.zvoykish.zevo.framework.entities.Generation;
import com.zvoykish.zevo.framework.entities.Individual;
import com.zvoykish.zevo.framework.operators.Selection;
import com.zvoykish.zevo.model.FitnessCalculator;
import com.zvoykish.zevo.utils.Randomizer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 03/05/2010
 * Time: 02:00:22
 */
@SuppressWarnings({"UnusedDeclaration"})
public class FittestSelection extends Selection {

    private Generation lastGeneration;
    private double[] lastDistributions;

    public FittestSelection(EvoConfiguration configuration, EvoFunctions functions) {
        super(configuration, functions);
        lastGeneration = null;
        lastDistributions = null;
    }

    @Override
    public List<Individual> getTwoCandidates(Generation generation) {
        List<Individual> result = new ArrayList<>();
        List<Individual> individuals = generation.getIndividuals();
        double[] distributions;
        if (lastGeneration == generation) {
            distributions = lastDistributions;
        }
        else {
            FitnessCalculator fitnessCalculator = functions.getFitnessCalculator();
            distributions = new double[individuals.size()];
            for (int i = 0; i < individuals.size(); i++) {
                distributions[i] = fitnessCalculator.getFitness(individuals.get(i), generation);
            }

            lastGeneration = generation;
            lastDistributions = distributions;
        }

        int randomIndex1 = Randomizer.getRandomIndexForDistribution(distributions);
        if (randomIndex1 == -1) {
            randomIndex1 = 0;
        }
        result.add(individuals.get(randomIndex1));

        int randomIndex2 = Randomizer.getRandomIndexForDistribution(distributions);
        if (randomIndex2 == -1) {
            randomIndex2 = (randomIndex1 != 1) ? 1 : 2;
        }
        result.add(individuals.get(randomIndex2));

        return result;
    }
}
