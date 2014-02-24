package com.zvoykish.zevo.framework;

import com.zvoykish.zevo.framework.operators.CrossOver;
import com.zvoykish.zevo.framework.operators.Mutation;
import com.zvoykish.zevo.framework.operators.Selection;
import com.zvoykish.zevo.model.Evaluator;
import com.zvoykish.zevo.model.FitnessCalculator;
import com.zvoykish.zevo.model.GenesisInitializer;
import com.zvoykish.zevo.utils.Logger;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 01/05/2010
 * Time: 02:37:34
 */
public class ControllerFactory {
    public static Controller getController(EvoConfiguration configuration, EvoFunctions functions,
                                           EvolutionListener listener)
    {
        try {
            initFunctions(configuration, functions);
            EvoController controller = new EvoController(configuration, functions);
            controller.register(listener);
            listener.onStart();
            controller.init();
            return controller;
        }
        catch (Exception e) {
            Logger.getInstance().log("ERROR CREATING CONTROLLER! QUITTING...");
            throw new RuntimeException(e);
        }
    }

    private static void initFunctions(EvoConfiguration configuration, EvoFunctions functions)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException
    {
        Class<? extends GenesisInitializer> genesisInitializerClass = configuration.getGenesisInitializerClass();
        GenesisInitializer genesisInitializer = genesisInitializerClass.getConstructor(EvoConfiguration.class)
                .newInstance(configuration);

        Class<? extends Selection> selectionClass = configuration.getSelectionClass();
        Selection selection = selectionClass.getConstructor(EvoConfiguration.class, EvoFunctions.class)
                .newInstance(configuration, functions);

        Class<? extends CrossOver> crossOverClass = configuration.getCrossOverClass();
        CrossOver crossOver = crossOverClass.getConstructor(EvoConfiguration.class).newInstance(configuration);

        Class<? extends Mutation> mutationClass = configuration.getMutationClass();
        Mutation mutation = mutationClass.getConstructor(EvoConfiguration.class).newInstance(configuration);

        Class<? extends Evaluator> evaluatorClass = configuration.getEvaluatorClass();
        Evaluator evaluator = evaluatorClass.getConstructor(EvoConfiguration.class).newInstance(configuration);

        Class<? extends FitnessCalculator> fitnessCalculatorClass = configuration.getFitnessCalculatorClass();
        FitnessCalculator fitnessCalculator = fitnessCalculatorClass
                .getConstructor(EvoConfiguration.class, Evaluator.class).newInstance(configuration, evaluator);

        functions.setGenesisInitializer(genesisInitializer).setSelection(selection)
                .setCrossOver(crossOver).setMutation(mutation).setEvaluator(evaluator)
                .setFitnessCalculator(fitnessCalculator);
    }
}
