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
 * Time: 02:02:23
 */
public interface EvoConfiguration {
    String getModelName();

    String getFilename();

    int getIndividualsCount();

    int getTimeLimit();

    int getGenerationsLimit();

    int getIntervalPerCsvSample();

    double getCrossOverProbability();

    double getMutationProbability();

    int getNumberOfGenerationsToApplyMassiveMutation();

    Class<? extends Selection> getSelectionClass();

    Class<? extends CrossOver> getCrossOverClass();

    Class<? extends Mutation> getMutationClass();

    Class<? extends GenesisInitializer> getGenesisInitializerClass();

    Class<? extends Evaluator> getEvaluatorClass();

    Class<? extends FitnessCalculator> getFitnessCalculatorClass();

    boolean isPreferMaxEvaluation();

    boolean isElitismEnabled();

    boolean isExtraConfigurationSupported();

    EvoExtraConfiguration getExtraConfiguration();
}
