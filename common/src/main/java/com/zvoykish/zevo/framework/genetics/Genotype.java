package com.zvoykish.zevo.framework.genetics;

import com.zvoykish.zevo.model.genetics.Gene;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 01/05/2010
 * Time: 16:13:30
 */
public interface Genotype<ConcreteGene extends Gene> extends Serializable {
    /**
     * @return the length of the genotype
     */
    int length();

    /**
     * Gets the i'th gene in the genotype
     *
     * @param index The requested gene index
     * @return The <i>index</i>'th gene
     */
    ConcreteGene getGene(int index);

    /**
     * Get all genes in the genotype
     *
     * @return List of genes representing the entire genotype sequence
     */
    @Nonnull
    List<ConcreteGene> getGenes();

    /**
     * Returns a sequence of the genotype, starting at the index <i>start</i>
     *
     * @param start Starting index
     * @return list of genes representing the genotype sequence starting from index <i>start</i>
     */
    List<ConcreteGene> getSequence(int start);

    /**
     * Returns a sequence of the genotype, starting at the index <i>start</i> and ends
     * before <i>end</i>
     *
     * @param start Starting index
     * @param end   End index (exclusive)
     * @return list of genes representing the genotype sequence starting from index <i>start</i>
     * and ends before <i>end</i>
     */
    List<ConcreteGene> getSequence(int start, int end);

    /**
     * Returns the genotype string representation
     *
     * @return String representing the genotype
     */
    String toString();

    JComponent getAsComponent();

    boolean isValid();

    String toPrettyString();
}
