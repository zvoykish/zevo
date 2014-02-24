package com.zvoykish.zevo.framework;

import com.zvoykish.zevo.framework.entities.Individual;
import com.zvoykish.zevo.framework.entities.IndividualImpl;
import com.zvoykish.zevo.framework.genetics.Genotype;
import com.zvoykish.zevo.model.genetics.Gene;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 01/05/2010
 * Time: 01:46:17
 */
public class IndividualFactory {
    public static <ConcreteGene extends Gene> Individual<ConcreteGene> createNewIndividual(
            Genotype<ConcreteGene> genotype)
    {
        return new IndividualImpl<>(genotype);
    }
}
