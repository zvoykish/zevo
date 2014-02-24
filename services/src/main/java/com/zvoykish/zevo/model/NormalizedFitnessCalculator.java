package com.zvoykish.zevo.model;

import com.zvoykish.zevo.framework.EvoConfiguration;
import com.zvoykish.zevo.framework.entities.Generation;
import com.zvoykish.zevo.framework.entities.Individual;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 02/05/2010
 * Time: 02:11:22
 */
@SuppressWarnings("UnusedDeclaration")
public class NormalizedFitnessCalculator extends FitnessCalculator {
    private Generation lastGeneration;
    private double lastGenerationEvaluation;

    public NormalizedFitnessCalculator(EvoConfiguration configuration, Evaluator evaluator) {
        super(configuration, evaluator);
        lastGeneration = null;
        lastGenerationEvaluation = -1;
    }

    @Override
    public double getFitness(Individual individual, Generation g) {
        double individualEvaluation = evaluator.evaluate(individual);
        // If individual is invalid - return 0, fitness won't allow him to be used in next generation
        if (individualEvaluation == -1) {
            return 0.0;
        }

        double generationEvaluation = 0;
        if (lastGeneration == g) {
            generationEvaluation = lastGenerationEvaluation;
        }
        else {
            lastGeneration = g;
            generationEvaluation += individualEvaluation;
            List<Individual> others = g.getIndividuals();
            for (Individual other : others) {
                if (other != individual) {
                    double otherEvaluation = evaluator.evaluate(other);
                    if (otherEvaluation != -1) {
                        generationEvaluation += otherEvaluation;
                    }
                }
            }
            lastGenerationEvaluation = generationEvaluation;
        }

        if (generationEvaluation == 0) {
            return 0;
        }

        return individualEvaluation / generationEvaluation;
    }
}
