package com.zvoykish.zevo.framework.events;

import com.zvoykish.zevo.framework.entities.Generation;
import com.zvoykish.zevo.framework.entities.Individual;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 01/05/2010
 * Time: 01:11:01
 */
public class NewGenerationEvent {
    private int generationNumber;
    private Generation newGeneration;
    private Individual bestIndividual;
    private double bestEvaluation;

    public NewGenerationEvent(int generationNumber, Generation newGeneration, Individual bestIndividual,
                              double bestEvaluation)
    {
        this.generationNumber = generationNumber;
        this.newGeneration = newGeneration;
        this.bestIndividual = bestIndividual;
        this.bestEvaluation = bestEvaluation;
    }

    public int getGenerationNumber() {
        return generationNumber;
    }

    public Generation getNewGeneration() {
        return newGeneration;
    }

    public Individual getBestIndividual() {
        return bestIndividual;
    }

    public double getBestEvaluation() {
        return bestEvaluation;
    }
}
