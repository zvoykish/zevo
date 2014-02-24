package com.zvoykish.zevo.framework;

import com.zvoykish.zevo.framework.entities.Generation;
import com.zvoykish.zevo.framework.entities.Individual;
import com.zvoykish.zevo.framework.events.MassiveMutationEvent;
import com.zvoykish.zevo.framework.events.NewGenerationEvent;
import com.zvoykish.zevo.framework.genetics.Genotype;
import com.zvoykish.zevo.framework.operators.CrossOver;
import com.zvoykish.zevo.framework.operators.Mutation;
import com.zvoykish.zevo.framework.operators.Selection;
import com.zvoykish.zevo.model.Evaluator;
import com.zvoykish.zevo.model.GenesisInitializer;
import com.zvoykish.zevo.utils.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 01/05/2010
 * Time: 02:41:51
 */
public class EvoController implements Controller {
    private EvoConfiguration configuration;
    private EvoFunctions functions;
    private List<EvolutionListener> listeners;

    private int currentGenerationNumber;
    private Generation currentGeneration;
    private double latestEvaluation;
    private int generationsWithoutImprovementCount;
    private int numberOfGenerationsBeforeApplyingMassiveMutation;

    public EvoController(EvoConfiguration configuration, EvoFunctions functions) {
        this.configuration = configuration;
        this.functions = functions;
        this.listeners = new ArrayList<>();
        this.currentGenerationNumber = 0;
        latestEvaluation = -1;
        generationsWithoutImprovementCount = 0;
        numberOfGenerationsBeforeApplyingMassiveMutation = configuration.getNumberOfGenerationsToApplyMassiveMutation();
    }

    @Override
    public void init() {
        currentGeneration = GenerationFactory.createEmptyGeneration();
        int n = configuration.getIndividualsCount();
        GenesisInitializer genesisInitializer = functions.getGenesisInitializer();
        Evaluator evaluator = functions.getEvaluator();
        double bestEvaluation = -1.0;
        Individual bestIndividual = null;
        for (int i = 0; i < n; i++) {
            Genotype newGenotype = genesisInitializer.getNewGenotype();
            Individual individual = IndividualFactory.createNewIndividual(newGenotype);
            if (individual.getGenotype().isValid()) {
                double currentEvaluation = evaluator.evaluate(individual);
                if (currentEvaluation > bestEvaluation/* && currentEvaluation > Double.MIN_VALUE*/) {
                    bestEvaluation = currentEvaluation;
                    bestIndividual = individual;
                }
                currentGeneration.addIndividual(individual);
            }
        }
        currentGenerationNumber++;
        for (EvolutionListener listener : listeners) {
            listener.onNewGeneration(
                    new NewGenerationEvent(currentGenerationNumber, currentGeneration, bestIndividual, bestEvaluation));
        }
    }

    @Override
    public Pair<Individual, Double> advanceSingleGeneration() {
        Generation newGeneration = GenerationFactory.createEmptyGeneration();

        // Elitism
        Iterator<Individual> it = currentGeneration.getIndividuals().iterator();
        Individual bestIndividual = it.next();
        Evaluator evaluator = functions.getEvaluator();
        double bestEvaluation = evaluator.evaluate(bestIndividual);
        while (it.hasNext()) {
            Individual individual = it.next();
            double evaluation = evaluator.evaluate(individual);
            if (evaluation > bestEvaluation) {
//                if ((bestEvaluation == -1 && evaluation != -1) ||
//                        (isPreferMaxEvaluation && evaluation > bestEvaluation) || (!isPreferMaxEvaluation && evaluation < bestEvaluation)) {
                bestIndividual = individual;
                bestEvaluation = evaluation;
            }
        }

        if (bestEvaluation > latestEvaluation) {
            latestEvaluation = bestEvaluation;
            generationsWithoutImprovementCount = 0;
        }
        else {
            generationsWithoutImprovementCount++;
        }

        if (configuration.isElitismEnabled()) {
            newGeneration.addIndividual(bestIndividual);
        }

        Selection selection = functions.getSelection();
        CrossOver crossOver = functions.getCrossOver();
        Mutation mutation = functions.getMutation();

        // Check if stuck in local optimum
        if (numberOfGenerationsBeforeApplyingMassiveMutation != -1 &&
                generationsWithoutImprovementCount >= numberOfGenerationsBeforeApplyingMassiveMutation)
        {
            // If stuck in local optimum - apply massive mutation to shake the system, zero the counter and make the next massive mutation
            // happen after a longer period (to allow "rolling down the hill" before the next incline)
            mutation.applyMassiveMutation(numberOfGenerationsBeforeApplyingMassiveMutation /
                    configuration.getNumberOfGenerationsToApplyMassiveMutation(), this);
            generationsWithoutImprovementCount = 0;
            numberOfGenerationsBeforeApplyingMassiveMutation += configuration
                    .getNumberOfGenerationsToApplyMassiveMutation();
        }

        while (newGeneration.getIndividuals().size() < configuration.getIndividualsCount()) {
            List<Individual> candidates = selection.getTwoCandidates(currentGeneration);
            Individual offspring = crossOver.doCrossOver(candidates);
            Individual postMutation = mutation.doMutation(offspring);
            if (postMutation.getGenotype().isValid()) {
                newGeneration.addIndividual(postMutation);
            }
        }

        // Store new generation
        currentGenerationNumber++;
        currentGeneration = newGeneration;

        for (EvolutionListener listener : listeners) {
            listener.onNewGeneration(
                    new NewGenerationEvent(currentGenerationNumber, newGeneration, bestIndividual, bestEvaluation));
        }

        // Since an event is sent - I'm returning the best individual+evaluation from performance
        // considerations alone (Best individual was already found, no need to cause client to re-calculate it)
        return new Pair<>(bestIndividual, bestEvaluation);
    }

    @Override
    public void register(EvolutionListener listener) {
        listeners.add(listener);
    }

    @Override
    public void fireEvent(NewGenerationEvent e) {
        for (EvolutionListener listener : listeners) {
            listener.onNewGeneration(e);
        }
    }

    @Override
    public void fireEvent(MassiveMutationEvent e) {
        for (EvolutionListener listener : listeners) {
            listener.onMassiveMutation(e);
        }
    }

    @Override
    public void onFinish(Individual bestIndividual) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
