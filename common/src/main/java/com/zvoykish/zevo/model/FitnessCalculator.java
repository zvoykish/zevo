package com.zvoykish.zevo.model;

import com.zvoykish.zevo.framework.EvoConfiguration;
import com.zvoykish.zevo.framework.entities.Generation;
import com.zvoykish.zevo.framework.entities.Individual;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 01/05/2010
 * Time: 15:47:14
 */
public abstract class FitnessCalculator {
    protected EvoConfiguration configuration;
    protected Evaluator evaluator;

    protected FitnessCalculator(EvoConfiguration configuration, Evaluator evaluator) {
        this.configuration = configuration;
        this.evaluator = evaluator;
    }

    public abstract double getFitness(Individual individual, Generation g);
}
