package com.zvoykish.zevo.model;

import com.zvoykish.zevo.framework.EvoConfiguration;
import com.zvoykish.zevo.framework.entities.Individual;
import com.zvoykish.zevo.model.genetics.Gene;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 01/05/2010
 * Time: 15:45:45
 */
public abstract class Evaluator<ConcreteGene extends Gene> {
    protected EvoConfiguration configuration;

    protected Evaluator(EvoConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * Returns the evaluation of the individial.<br/>
     * <br/><b>Higher value means that individual is better!!!</b><br/><br/>
     *
     * @param individual Individual to evaluate
     * @return evaluation value
     */
    public abstract double evaluate(Individual<ConcreteGene> individual);
}
