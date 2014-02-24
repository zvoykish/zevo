package com.zvoykish.zevo.framework;

import com.zvoykish.zevo.framework.events.MassiveMutationEvent;
import com.zvoykish.zevo.framework.events.NewGenerationEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 01/05/2010
 * Time: 01:12:23
 */
public interface EvolutionListener {
    public void onNewGeneration(NewGenerationEvent event);

    public void onMassiveMutation(MassiveMutationEvent event);

    public void onStart();

    public void onFinish();
}
