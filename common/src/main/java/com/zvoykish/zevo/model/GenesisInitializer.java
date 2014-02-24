package com.zvoykish.zevo.model;

import com.zvoykish.zevo.framework.EvoConfiguration;
import com.zvoykish.zevo.framework.genetics.Genotype;
import com.zvoykish.zevo.model.genetics.Gene;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 01/05/2010
 * Time: 15:43:34
 */
public abstract class GenesisInitializer<ConcreteGene extends Gene> {
    protected EvoConfiguration configuration;

    protected GenesisInitializer(EvoConfiguration configuration) {
        this.configuration = configuration;
    }

    public abstract Genotype<ConcreteGene> getNewGenotype();
}
