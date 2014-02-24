package com.zvoykish.zevo.framework.genetics;

import com.zvoykish.zevo.model.genetics.Gene;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 01/05/2010
 * Time: 16:33:55
 */
public class GenotypeImpl<ConcreteGene extends Gene> implements Genotype<ConcreteGene> {
    @Nonnull
    private List<ConcreteGene> genes;

    public GenotypeImpl(List<ConcreteGene> genes) {
        if (genes == null) {
            throw new RuntimeException("Gene list can't be null!");
        }
        this.genes = genes;
    }

    @Override
    public int length() {
        return genes.size();
    }

    @Override
    public ConcreteGene getGene(int index) {
        return genes.get(index);
    }

    @Nonnull
    @Override
    public List<ConcreteGene> getGenes() {
        return genes;
    }

    @Override
    public List<ConcreteGene> getSequence(int start) {
        return getSequence(start, genes.size());
    }

    @Override
    public List<ConcreteGene> getSequence(int start, int end) {
        if (start < 0 || end > genes.size() || start >= end) {
            throw new RuntimeException("Illegal start/end for genes sequence: start/end=" + start + "/" + end);
        }
        List<ConcreteGene> sequence = new ArrayList<>();
        for (int i = start; i < end; i++) {
            sequence.add(genes.get(i));
        }
        return sequence;
    }

    @Override
    public String toString() {
        return genes.toString();
    }

    @Override
    public JComponent getAsComponent() {
        JPanel panel = new JPanel(new GridLayout(genes.size(), 1));
        for (int i = 0; i < genes.size(); i++) {
            JPanel genePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            genePanel.add(new JLabel("Gene " + i + ": "));
            genePanel.add(genes.get(i).getAsComponent());
            panel.add(genePanel);
        }
        return panel;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public String toPrettyString() {
        return toString();
    }
}
