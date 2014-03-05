package com.zvoykish.zevo.framework.entities;

import com.zvoykish.zevo.framework.genetics.Genotype;
import com.zvoykish.zevo.model.genetics.Gene;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 01/05/2010
 * Time: 01:50:33
 */
public class IndividualImpl<ConcreteGene extends Gene> implements Individual<ConcreteGene> {
    private static long counter = 0;
    private final long id;
    private final String uuid;

    @Nonnull
    private Genotype<ConcreteGene> genotype;

    public IndividualImpl(@Nonnull Genotype<ConcreteGene> genotype) {
        this.id = ++counter;
        this.uuid = UUID.randomUUID().toString() + UUID.randomUUID().toString();
        this.genotype = genotype;
    }

    @Override
    public long getId() {
        return id;
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
