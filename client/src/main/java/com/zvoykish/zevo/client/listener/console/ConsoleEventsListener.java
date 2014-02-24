package com.zvoykish.zevo.client.listener.console;

import com.zvoykish.zevo.client.listener.AbstractListener;
import com.zvoykish.zevo.framework.EvoConfiguration;
import com.zvoykish.zevo.framework.events.MassiveMutationEvent;
import com.zvoykish.zevo.framework.events.NewGenerationEvent;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 10/05/2010
 * Time: 21:06:34
 */
public class ConsoleEventsListener extends AbstractListener {

    public ConsoleEventsListener(EvoConfiguration configuration) {
        super(configuration);
    }

    @Override
    public void onNewGeneration(NewGenerationEvent event) {
        super.onNewGeneration(event);

        int number = event.getGenerationNumber();
        double currentEvaluation = event.getBestEvaluation();
        if (currentEvaluation == -1) {
            String msg = "Generation #" + number +
                    " - NO RELEVANT INDIVIDUAL FOUND! WAITING FOR NEXT GENERATION... (best evaluation so far: " +
                    bestEvaluation + ")";
            logger.log(msg);
            System.out.println(new Date().toString() + " - " + msg);
        }
        else {
            boolean isImproved = currentEvaluation > bestEvaluation;
            String msg = "Generation #" + number + " " +
                    (isImproved ? ("improved evaluation: " + bestEvaluation + " --> " + currentEvaluation) : (
                            "best evaluation: " + currentEvaluation + " (best evaluation so far: " + bestEvaluation +
                                    ")"));
            logger.log(msg);
            if (isImproved || event.getGenerationNumber() % 100 == 0) {
                System.out.println(new Date().toString() + " - " + msg);
            }

            bestEvaluation = Math.max(bestEvaluation, currentEvaluation);
        }
    }

    @Override
    public void onMassiveMutation(MassiveMutationEvent event) {
        String msg = STRING_APPLYING_MASSIVE_MUTATION + " (factor: " + event.getFactor() + ")";
        logger.log(msg);
        System.out.println(new Date() + " - " + msg);
    }
}
