package com.zvoykish.zevo.model;

import com.zvoykish.zevo.framework.EvoConfiguration;
import com.zvoykish.zevo.framework.entities.Generation;
import com.zvoykish.zevo.framework.entities.Individual;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 02/05/2010
 * Time: 02:11:22
 */
public class EvaluationAsFitnessCalculator extends FitnessCalculator {

    public EvaluationAsFitnessCalculator(EvoConfiguration configuration, Evaluator evaluator) {
        super(configuration, evaluator);
    }

    @Override
    public double getFitness(Individual individual, Generation g) {
        double individualEvaluation = evaluator.evaluate(individual);
        // If individual is invalid - return 0, fitness won't allow him to be used in next generation
        if (individualEvaluation == -1) {
            return 0.0;
        }

        return individualEvaluation;
    }
}