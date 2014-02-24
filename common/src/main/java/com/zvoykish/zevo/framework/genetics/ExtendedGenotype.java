package com.zvoykish.zevo.framework.genetics;

import com.zvoykish.zevo.model.genetics.Gene;

import javax.annotation.Nonnull;

/**
 * Created with IntelliJ IDEA.
 * User: Zvoykish
 * Date: 27/4/13
 * Time: 1:48 AM
 */
public interface ExtendedGenotype<ConcreteGene extends Gene, ExtendedInformationType> extends Genotype<ConcreteGene> {
    @Nonnull
    ExtendedInformationType getExtendedInformation();
}
