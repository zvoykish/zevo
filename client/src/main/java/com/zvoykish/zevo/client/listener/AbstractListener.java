package com.zvoykish.zevo.client.listener;

import com.zvoykish.zevo.framework.EvoConfiguration;
import com.zvoykish.zevo.framework.EvolutionListener;
import com.zvoykish.zevo.framework.events.NewGenerationEvent;
import com.zvoykish.zevo.utils.Logger;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 10/05/2010
 * Time: 22:05:27
 */
public abstract class AbstractListener implements EvolutionListener {
    protected double bestEvaluation;
    private long lastCsvSampleTime;
    protected final Logger logger = Logger.getInstance();
    protected final Logger csvWriter = Logger.getInstance(true);

    private final int intervalPerCsvSample;
    protected static final String STRING_APPLYING_MASSIVE_MUTATION = "Applying massive mutation!";

    protected AbstractListener(EvoConfiguration configuration) {
        bestEvaluation = -1;
        lastCsvSampleTime = -1;
        intervalPerCsvSample = 1000 * configuration.getIntervalPerCsvSample();
    }

    @Override
    public void onNewGeneration(NewGenerationEvent event) {
        long currentTime = new Date().getTime();
        if (shouldLogGeneration(currentTime)) {
            lastCsvSampleTime = currentTime;
            int number = event.getGenerationNumber();
            double currentEvaluation = event.getBestEvaluation();
            csvWriter.log(number + "," + currentEvaluation);
        }
    }

    protected boolean shouldLogGeneration(long currentTime) {
        return intervalPerCsvSample == -1000 ||
                lastCsvSampleTime == -1 ||
                currentTime - lastCsvSampleTime >= intervalPerCsvSample;
    }

    @Override
    public void onStart() {
        csvWriter.log("Generation #,Best individual");
    }

    @Override
    public void onFinish() {
        csvWriter.close();
    }
}
