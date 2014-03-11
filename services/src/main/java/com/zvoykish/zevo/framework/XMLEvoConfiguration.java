package com.zvoykish.zevo.framework;

import com.zvoykish.zevo.framework.operators.CrossOver;
import com.zvoykish.zevo.framework.operators.Mutation;
import com.zvoykish.zevo.framework.operators.Selection;
import com.zvoykish.zevo.model.Evaluator;
import com.zvoykish.zevo.model.FitnessCalculator;
import com.zvoykish.zevo.model.GenesisInitializer;
import com.zvoykish.zevo.utils.ClassUtils;
import com.zvoykish.zevo.utils.Logger;
import com.zvoykish.zevo.utils.XMLUtils;
import org.jdom.Element;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 01/05/2010
 * Time: 02:03:38
 */
@SuppressWarnings({"unchecked"})
public class XMLEvoConfiguration implements EvoConfiguration {
    private String filename;
    private String modelName;
    private int individualsCount;
    private int timeLimit;
    private int generationsLimit;
    private int intervalPerCsvSample;
    private double crossOverProbability;
    private double mutationProbability;
    private int numberOfGenerationsToApplyMassiveMutation;
    private int minimumNumberOfWorkerThreadsHint;
    private Class<? extends Selection> selectionClass;
    private Class<? extends CrossOver> crossOverClass;
    private Class<? extends Mutation> mutationClass;
    private Class<? extends GenesisInitializer> genesisInitializerClass;
    private Class<? extends Evaluator> evaluatorClass;
    private Class<? extends FitnessCalculator> fitnessCalculatorClass;
    private boolean preferMaxEvaluation;
    private boolean elitismEnabled;
    private EvoExtraConfiguration extraConfiguration;

    private static final String ROOT_NAME = "EvoConfiguration";
    private static final String TAG_INDIVIDUALS_COUNT = "IndividualsCount";
    private static final String TAG_TIME_LIMIT = "TimeLimit";
    private static final String TAG_GENERATIONS_LIMIT = "GenerationsLimit";
    private static final String TAG_INTERVAL_PER_CSV_SAMPLE = "IntervalPerCsvSample";
    private static final String TAG_SELECTION_CLASS = "Selection";
    private static final String TAG_MUTATION_CLASS = "Mutation";
    private static final String TAG_CROSSOVER_CLASS = "CrossOver";
    private static final String TAG_NUMBER_OF_GENERATIONS_TO_APPLY_MASSIVE_MUTATION = "NumberOfGenerationsToApplyMassiveMutation";
    private static final String TAG_MIN_NUMBER_OF_WORKER_THREADS_HINT = "MinimumNumberOfWorkerThreadsHint";

    private static final String TAG_ELITISM_ENABLED = "ElitismEnabled";
    private static final String TAG_CROSSOVER_PROBABILITY = "CrossOverProbability";
    private static final String TAG_MUTATION_PROBABILITY = "MutationProbability";
    private static final String TAG_MODEL = "Model";
    private static final String TAG_NAME = "Name";
    private static final String TAG_PREFER_MAX_EVALUATION = "PreferMaxEvaluation";
    private static final String TAG_GENESIS_INITIALIZER_CLASS = "GenesisInitializer";
    private static final String TAG_FITNESS_CALCULATOR_CLASS = "FitnessCalculator";
    private static final String TAG_EVALUATOR_CLASS = "Evaluator";
    private static final String TAG_EXTRA_CONFIGURATION = "ExtraConfiguration";

    public XMLEvoConfiguration(String filename) {
        this.filename = filename;
        Element rootElement = XMLUtils.getXmlFileRootElement(filename);
        if (rootElement != null) {
            String rootName = rootElement.getName();
            if (rootName == null || !rootName.equals(ROOT_NAME)) {
                throw new RuntimeException("Bad XML file");
            }

            // Get individuals count (mandatory)
            String individualsCountStr = rootElement.getChildTextTrim(TAG_INDIVIDUALS_COUNT);
            try {
                individualsCount = Integer.valueOf(individualsCountStr);
            }
            catch (Exception e) {
                throw new RuntimeException("Invalid individuals-count value - " + individualsCountStr, e);
            }

            // Get time limit (default: 182)
            String timeLimitStr = rootElement.getChildTextTrim(TAG_TIME_LIMIT);
            Logger logger = Logger.getInstance();
            try {
                timeLimit = Integer.valueOf(timeLimitStr);
            }
            catch (Exception e) {
                logger.log(
                        "Warning: Invalid Time-Limit value " + timeLimitStr + ". Using default value of 182 seconds.");
                timeLimit = -1;
            }

            // Get generations limit (default: none)
            String generationsLimitStr = rootElement.getChildTextTrim(TAG_GENERATIONS_LIMIT);
            try {
                generationsLimit = Integer.valueOf(generationsLimitStr);
            }
            catch (Exception e) {
                logger.log("Warning: Invalid Generations-Limit value " + generationsLimitStr +
                        ". Disabling limit by generations.");
                generationsLimit = -1;
            }

            // Get interval per CSV sample (default: -1 = every generation)
            String intervalPerCsvSampleStr = rootElement.getChildTextTrim(TAG_INTERVAL_PER_CSV_SAMPLE);
            try {
                intervalPerCsvSample = Integer.valueOf(intervalPerCsvSampleStr);
            }
            catch (Exception e) {
                logger.log("Warning: Invalid Interval-per-CSV-sample value " + intervalPerCsvSampleStr +
                        ". Sampling every generation.");
                intervalPerCsvSample = -1;
            }

            /*************************
             * Parse runtime classes
             */
            Class tempClass;

            // Selection class
            String selectionClassName = null;
            try {
                selectionClassName = rootElement.getChildTextTrim(TAG_SELECTION_CLASS);
                tempClass = Class.forName(selectionClassName);
                if (ClassUtils.isSelectionClass(tempClass)) {
                    selectionClass = tempClass;
                }
            }
            catch (Exception e) {
                throw new RuntimeException("Invalid selection class - " + selectionClassName, e);
            }

            // Cross-over class
            String crossOverClassName = null;
            try {
                crossOverClassName = rootElement.getChildTextTrim(TAG_CROSSOVER_CLASS);
                tempClass = Class.forName(crossOverClassName);
                if (ClassUtils.isCrossOverClass(tempClass)) {
                    crossOverClass = tempClass;
                }
            }
            catch (Exception e) {
                throw new RuntimeException("Invalid cross-over class - " + crossOverClassName, e);
            }

            // Mutation class
            String mutationClassName = null;
            try {
                mutationClassName = rootElement.getChildTextTrim(TAG_MUTATION_CLASS);
                tempClass = Class.forName(mutationClassName);
                if (ClassUtils.isMutationClass(tempClass)) {
                    mutationClass = tempClass;
                }
            }
            catch (Exception e) {
                throw new RuntimeException("Invalid mutation class - " + mutationClassName, e);
            }

            String elitismEnabledStr = rootElement.getChildTextTrim(TAG_ELITISM_ENABLED);
            elitismEnabled = Boolean.parseBoolean(elitismEnabledStr);

            String preferMaxEvaluationStr = rootElement.getChildTextTrim(TAG_PREFER_MAX_EVALUATION);
            preferMaxEvaluation = Boolean.parseBoolean(preferMaxEvaluationStr);

            String crossOverProbabilityStr = rootElement.getChildTextTrim(TAG_CROSSOVER_PROBABILITY);
            try {
                crossOverProbability = Double.valueOf(crossOverProbabilityStr);
            }
            catch (Exception e) {
                throw new RuntimeException("Invalid cross-over probability value - " + crossOverProbabilityStr, e);
            }

            String mutationProbabilityStr = rootElement.getChildTextTrim(TAG_MUTATION_PROBABILITY);
            try {
                mutationProbability = Double.valueOf(mutationProbabilityStr);
            }
            catch (Exception e) {
                throw new RuntimeException("Invalid mutation probability value - " + mutationProbabilityStr, e);
            }

            String numberOfGenerationsForMassiveMutationStr = rootElement
                    .getChildTextTrim(TAG_NUMBER_OF_GENERATIONS_TO_APPLY_MASSIVE_MUTATION);
            try {
                numberOfGenerationsToApplyMassiveMutation = Integer.valueOf(numberOfGenerationsForMassiveMutationStr);
            }
            catch (Exception e) {
                logger.log("Warning: Invalid number of generation for massive mutation value " +
                        numberOfGenerationsForMassiveMutationStr + ". Disabling Massive-Mutation.");
                numberOfGenerationsToApplyMassiveMutation = -1;
            }

            String minimumNumberOfWorkerThreadsHintStr = rootElement
                    .getChildTextTrim(TAG_MIN_NUMBER_OF_WORKER_THREADS_HINT);
            try {
                minimumNumberOfWorkerThreadsHint = Integer.valueOf(minimumNumberOfWorkerThreadsHintStr);
            }
            catch (Exception e) {
                logger.log("Warning: Invalid number of worker threads hint value " +
                        minimumNumberOfWorkerThreadsHintStr + ". Defaulting to 64.");
                minimumNumberOfWorkerThreadsHint = 64;
            }

            // Parse model element
            Element modelElement = rootElement.getChild(TAG_MODEL);
            modelName = modelElement.getChildTextTrim(TAG_NAME);

            /*************************
             * Parse model runtime classes
             */

            // Genesis initializer class
            String genesisInitializerClassName = null;
            try {
                genesisInitializerClassName = modelElement.getChildTextTrim(TAG_GENESIS_INITIALIZER_CLASS);
                tempClass = Class.forName(genesisInitializerClassName);
                if (ClassUtils.isGenesisInitializerClass(tempClass)) {
                    genesisInitializerClass = tempClass;
                }
            }
            catch (Exception e) {
                throw new RuntimeException("Invalid genesis initializer class - " + genesisInitializerClassName, e);
            }

            // Evaluator class
            String evaluatorClassName = null;
            try {
                evaluatorClassName = modelElement.getChildTextTrim(TAG_EVALUATOR_CLASS);
                tempClass = Class.forName(evaluatorClassName);
                if (ClassUtils.isEvaluatorClass(tempClass)) {
                    evaluatorClass = tempClass;
                }
            }
            catch (Exception e) {
                throw new RuntimeException("Invalid evaluator class - " + evaluatorClassName, e);
            }

            // Fitness calculator class
            String fitnessCalculatorClassName = null;
            try {
                fitnessCalculatorClassName = modelElement.getChildTextTrim(TAG_FITNESS_CALCULATOR_CLASS);
                tempClass = Class.forName(fitnessCalculatorClassName);
                if (ClassUtils.isFitnessCalculatorClass(tempClass)) {
                    fitnessCalculatorClass = tempClass;
                }
            }
            catch (Exception e) {
                throw new RuntimeException("Invalid fitness calculator class - " + fitnessCalculatorClassName, e);
            }

            extraConfiguration = null;
            String extraConfigurationClassName = null;
            try {
                extraConfigurationClassName = modelElement.getChildTextTrim(TAG_EXTRA_CONFIGURATION);
                if (extraConfigurationClassName != null) {
                    // Found extra configuration - init it
                    tempClass = Class.forName(extraConfigurationClassName);
                    if (ClassUtils.isExtraConfigurationClass(tempClass)) {
                        extraConfiguration = ((Class<? extends EvoExtraConfiguration>) tempClass).newInstance();
                        extraConfiguration.initExtraConfiguration(filename);
                    }
                }
            }
            catch (Exception e) {
                extraConfiguration = null;
                throw new RuntimeException(
                        "Failed initializing extra configuration class - " + extraConfigurationClassName, e);
            }
        }
    }

    @Override
    public String getModelName() {
        return modelName;
    }

    @Override
    public String getFilename() {
        return filename;
    }

    @Override
    public int getIndividualsCount() {
        return individualsCount;
    }

    @Override
    public int getTimeLimit() {
        return timeLimit;
    }

    @Override
    public int getGenerationsLimit() {
        return generationsLimit;
    }

    @Override
    public int getIntervalPerCsvSample() {
        return intervalPerCsvSample;
    }

    @Override
    public double getCrossOverProbability() {
        return crossOverProbability;
    }

    @Override
    public double getMutationProbability() {
        return mutationProbability;
    }

    @Override
    public int getNumberOfGenerationsToApplyMassiveMutation() {
        return numberOfGenerationsToApplyMassiveMutation;
    }

    @Override
    public int getMinimumNumberOfWorkerThreadsHint() {
        return minimumNumberOfWorkerThreadsHint;
    }

    @Override
    public Class<? extends Selection> getSelectionClass() {
        return selectionClass;
    }

    @Override
    public Class<? extends CrossOver> getCrossOverClass() {
        return crossOverClass;
    }

    @Override
    public Class<? extends Mutation> getMutationClass() {
        return mutationClass;
    }

    @Override
    public Class<? extends GenesisInitializer> getGenesisInitializerClass() {
        return genesisInitializerClass;
    }

    public Class<? extends Evaluator> getEvaluatorClass() {
        return evaluatorClass;
    }

    public Class<? extends FitnessCalculator> getFitnessCalculatorClass() {
        return fitnessCalculatorClass;
    }

    @Override
    public boolean isPreferMaxEvaluation() {
        return preferMaxEvaluation;
    }

    @Override
    public boolean isElitismEnabled() {
        return elitismEnabled;
    }

    @Override
    public boolean isExtraConfigurationSupported() {
        return extraConfiguration != null;
    }

    @Override
    public EvoExtraConfiguration getExtraConfiguration() {
        return extraConfiguration;
    }
}
