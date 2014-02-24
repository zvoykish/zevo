package com.zvoykish.zevo.model.mathfunction;

import com.zvoykish.zevo.framework.EvoConfiguration;
import com.zvoykish.zevo.framework.GenotypeFactory;
import com.zvoykish.zevo.framework.genetics.Genotype;
import com.zvoykish.zevo.model.GenesisInitializer;
import com.zvoykish.zevo.model.genetics.BinaryGene;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 05/05/2010
 * Time: 00:17:37
 */
@SuppressWarnings("UnusedDeclaration")
public class MathFunctionGenesisInitializer extends GenesisInitializer<BinaryGene> {
    public MathFunctionGenesisInitializer(EvoConfiguration configuration) {
        super(configuration);
    }

    @Override
    public Genotype<BinaryGene> getNewGenotype() {
        List<BinaryGene> genes = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            int geneValue = (int) (Math.random() * 2);
            BinaryGene gene = new BinaryGene(geneValue);
            genes.add(gene);
        }
        return GenotypeFactory.createNewGenotype(genes);
    }
}
