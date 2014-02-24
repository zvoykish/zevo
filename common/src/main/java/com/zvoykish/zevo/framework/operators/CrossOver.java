package com.zvoykish.zevo.framework.operators;

import com.zvoykish.zevo.framework.EvoConfiguration;
import com.zvoykish.zevo.framework.GeneticOperator;
import com.zvoykish.zevo.framework.entities.Individual;
import com.zvoykish.zevo.model.genetics.Gene;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 01/05/2010
 * Time: 01:07:57
 */
public abstract class CrossOver<ConcreteGene extends Gene> implements GeneticOperator {
    protected EvoConfiguration configuration;

    protected CrossOver(EvoConfiguration configuration) {
        this.configuration = configuration;
    }

    public Individual<ConcreteGene> doCrossOver(List<Individual<ConcreteGene>> individuals) {
        double chance = Math.random();
        if (chance < configuration.getCrossOverProbability()) {
            // Should do cross-over
            return actualPerformCrossOver(individuals.get(0), individuals.get(1));
        }
        else {
            // Shouldn't perform cross-over
            // Randomly choose one of the parents
            int randomParent = (int) (Math.random() * 2);
            return individuals.get(randomParent);
        }
    }

    public abstract Individual<ConcreteGene> actualPerformCrossOver(Individual<ConcreteGene> ind1,
                                                                    Individual<ConcreteGene> ind2);
}
