package com.zvoykish.zevo.framework.operators;

import com.zvoykish.zevo.framework.Controller;
import com.zvoykish.zevo.framework.EvoConfiguration;
import com.zvoykish.zevo.framework.GeneticOperator;
import com.zvoykish.zevo.framework.entities.Individual;
import com.zvoykish.zevo.framework.events.MassiveMutationEvent;
import com.zvoykish.zevo.model.genetics.Gene;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 01/05/2010
 * Time: 01:08:13
 */
public abstract class Mutation<ConcreteGene extends Gene> implements GeneticOperator {
    protected EvoConfiguration configuration;
    private double mutationProbabilityManipulator;
    private double mutationProbability;

    protected Mutation(EvoConfiguration configuration) {
        this.configuration = configuration;
        this.mutationProbabilityManipulator = 0.0;
        mutationProbability = this.configuration.getMutationProbability();
    }

    public abstract Individual<ConcreteGene> doMutation(Individual<ConcreteGene> individual);

    public void applyMassiveMutation(double factor, Controller controller) {
        mutationProbabilityManipulator = factor;
        MassiveMutationEvent mutationEvent = new MassiveMutationEvent(factor);
        controller.fireEvent(mutationEvent);
    }

    protected double getProbabilityForMutation() {
        double currentProbability = mutationProbability + mutationProbabilityManipulator;
        mutationProbabilityManipulator = Math.max(mutationProbabilityManipulator - 0.01, 0.0);
        return currentProbability;
    }
}
