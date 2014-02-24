package com.zvoykish.zevo.framework.operators;

import com.zvoykish.zevo.framework.EvoConfiguration;
import com.zvoykish.zevo.framework.EvoFunctions;
import com.zvoykish.zevo.framework.GeneticOperator;
import com.zvoykish.zevo.framework.entities.Generation;
import com.zvoykish.zevo.framework.entities.Individual;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 01/05/2010
 * Time: 01:07:36
 */
public abstract class Selection implements GeneticOperator {
    protected EvoConfiguration configuration;
    protected EvoFunctions functions;

    protected Selection(EvoConfiguration configuration, EvoFunctions functions) {
        this.configuration = configuration;
        this.functions = functions;
    }

    public abstract List<Individual> getTwoCandidates(Generation generation);
}
