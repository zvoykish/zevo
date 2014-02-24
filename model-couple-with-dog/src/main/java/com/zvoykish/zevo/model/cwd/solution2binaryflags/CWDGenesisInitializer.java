package com.zvoykish.zevo.model.cwd.solution2binaryflags;

import com.zvoykish.zevo.framework.EvoConfiguration;
import com.zvoykish.zevo.framework.GenotypeFactory;
import com.zvoykish.zevo.framework.cwd.CWDExtraConfiguration;
import com.zvoykish.zevo.framework.genetics.Genotype;
import com.zvoykish.zevo.model.GenesisInitializer;
import com.zvoykish.zevo.model.genetics.BinaryGene;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 07/05/2010
 * Time: 02:10:59
 */
@SuppressWarnings("UnusedDeclaration")
public class CWDGenesisInitializer extends GenesisInitializer<BinaryGene> {
    public CWDGenesisInitializer(EvoConfiguration configuration) {
        super(configuration);
    }

    @Override
    public Genotype<BinaryGene> getNewGenotype() {
        List<BinaryGene> genes = new ArrayList<>();
        int individualsPerSpecies = ((CWDExtraConfiguration) configuration.getExtraConfiguration())
                .getIndividualsPerSpecies();
        int n = 2 * individualsPerSpecies * individualsPerSpecies; // One for F->M, another for F->D
        for (int i = 0; i < n; i++) {
            int randomBit = (int) (Math.random() * 2);
            genes.add(new BinaryGene(randomBit));
        }
        return GenotypeFactory.createNewGenotype(genes);
    }
}
