package com.zvoykish.zevo.framework.entities;

import com.zvoykish.zevo.framework.genetics.Genotype;
import com.zvoykish.zevo.model.genetics.Gene;

import javax.annotation.Nonnull;
import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 01/05/2010
 * Time: 01:50:33
 */
public class IndividualImpl<ConcreteGene extends Gene> implements Individual<ConcreteGene> {
    @Nonnull
    private Genotype<ConcreteGene> genotype;

    public IndividualImpl(@Nonnull Genotype<ConcreteGene> genotype) {
        this.genotype = genotype;
    }

    @Nonnull
    @Override
    public Genotype<ConcreteGene> getGenotype() {
        return genotype;
    }

    @Nonnull
    @Override
    public String toString() {
        return genotype.toString();
    }

    @Nonnull
    @Override
    public String toPrettyString() {
        return genotype.toPrettyString();
    }

    @Nonnull
    @Override
    public JComponent getAsComponent() {
        return genotype.getAsComponent();
    }
}
