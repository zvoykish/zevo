package com.zvoykish.zevo.framework;

import com.zvoykish.zevo.framework.genetics.Genotype;
import com.zvoykish.zevo.framework.genetics.GenotypeImpl;
import com.zvoykish.zevo.model.genetics.Gene;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 01/05/2010
 * Time: 16:42:04
 */
public class GenotypeFactory {
    @Nonnull
    public static <ConcreteGene extends Gene> Genotype<ConcreteGene> createNewGenotype(List<ConcreteGene> genes) {
        return new GenotypeImpl<>(genes);
    }
}
