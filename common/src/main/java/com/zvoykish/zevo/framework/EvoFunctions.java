package com.zvoykish.zevo.framework;

import com.zvoykish.zevo.framework.operators.CrossOver;
import com.zvoykish.zevo.framework.operators.Mutation;
import com.zvoykish.zevo.framework.operators.Selection;
import com.zvoykish.zevo.model.Evaluator;
import com.zvoykish.zevo.model.FitnessCalculator;
import com.zvoykish.zevo.model.GenesisInitializer;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 01/05/2010
 * Time: 17:36:43
 */
public class EvoFunctions {
    private GenesisInitializer genesisInitializer;
    private Selection selection;
    private CrossOver crossOver;
    private Mutation mutation;
    private Evaluator evaluator;
    private FitnessCalculator fitnessCalculator;

    public EvoFunctions() {
    }

    public GenesisInitializer getGenesisInitializer() {
        return genesisInitializer;
    }

    public Selection getSelection() {
        return selection;
    }

    public CrossOver getCrossOver() {
        return crossOver;
    }

    public Mutation getMutation() {
        return mutation;
    }

    public Evaluator getEvaluator() {
        return evaluator;
    }

    public FitnessCalculator getFitnessCalculator() {
        return fitnessCalculator;
    }

    public EvoFunctions setGenesisInitializer(GenesisInitializer genesisInitializer) {
        this.genesisInitializer = genesisInitializer;
        return this;
    }

    public EvoFunctions setSelection(Selection selection) {
        this.selection = selection;
        return this;
    }

    public EvoFunctions setCrossOver(CrossOver crossOver) {
        this.crossOver = crossOver;
        return this;
    }

    public EvoFunctions setMutation(Mutation mutation) {
        this.mutation = mutation;
        return this;
    }

    public EvoFunctions setEvaluator(Evaluator evaluator) {
        this.evaluator = evaluator;
        return this;
    }

    public EvoFunctions setFitnessCalculator(FitnessCalculator fitnessCalculator) {
        this.fitnessCalculator = fitnessCalculator;
        return this;
    }
}
