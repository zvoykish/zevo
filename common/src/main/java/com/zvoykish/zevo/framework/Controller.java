package com.zvoykish.zevo.framework;

import com.zvoykish.zevo.framework.entities.Individual;
import com.zvoykish.zevo.framework.events.MassiveMutationEvent;
import com.zvoykish.zevo.framework.events.NewGenerationEvent;
import com.zvoykish.zevo.utils.Pair;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 01/05/2010
 * Time: 02:40:47
 */
public interface Controller {
    void init();

    Pair<Individual, Double> advanceSingleGeneration(int generationNumber);

    void register(EvolutionListener listener);

    void fireEvent(NewGenerationEvent e);

    void fireEvent(MassiveMutationEvent e);

    void onFinish(Individual bestIndividual);
}
