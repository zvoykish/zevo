package com.zvoykish.zevo.framework.entities;

import com.zvoykish.zevo.framework.genetics.Genotype;
import com.zvoykish.zevo.model.genetics.Gene;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 01/05/2010
 * Time: 01:06:12
 */
public interface Individual<ConcreteGene extends Gene> extends Serializable {
    long getId();

    @Nonnull
    Genotype<ConcreteGene> getGenotype();

    @Nonnull
    String toString();

    @Nonnull
    String toPrettyString();

    @Nonnull
    JComponent getAsComponent();
}
