package com.zvoykish.zevo.utils;

import com.zvoykish.zevo.framework.EvoExtraConfiguration;
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
 * Time: 02:30:42
 */
public class ClassUtils extends GenericClassUtils {
    public static boolean isSelectionClass(Class clazz) {
        return isClassADerivedFromClassB(clazz, Selection.class);
    }

    public static boolean isCrossOverClass(Class clazz) {
        return isClassADerivedFromClassB(clazz, CrossOver.class);
    }

    public static boolean isMutationClass(Class clazz) {
        return isClassADerivedFromClassB(clazz, Mutation.class);
    }

    public static boolean isGenesisInitializerClass(Class clazz) {
        return isClassADerivedFromClassB(clazz, GenesisInitializer.class);
    }

    public static boolean isEvaluatorClass(Class clazz) {
        return isClassADerivedFromClassB(clazz, Evaluator.class);
    }

    public static boolean isFitnessCalculatorClass(Class clazz) {
        return isClassADerivedFromClassB(clazz, FitnessCalculator.class);
    }

    public static boolean isExtraConfigurationClass(Class clazz) {
        return isClassADerivedFromClassB(clazz, EvoExtraConfiguration.class);
    }
}
