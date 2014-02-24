package com.zvoykish.zevo.model.cwd.solution1maledogsprefs;

import com.zvoykish.zevo.framework.EvoConfiguration;
import com.zvoykish.zevo.framework.GenotypeFactory;
import com.zvoykish.zevo.framework.cwd.CWDExtraConfiguration;
import com.zvoykish.zevo.framework.genetics.Genotype;
import com.zvoykish.zevo.model.GenesisInitializer;
import com.zvoykish.zevo.model.cwd.solution1maledogsprefs.genetics.FemalePreferenceGene;
import com.zvoykish.zevo.utils.Randomizer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 01/05/2010
 * Time: 16:04:42
 */
public class CWDGenesisInitializer extends GenesisInitializer<FemalePreferenceGene> {

    public CWDGenesisInitializer(EvoConfiguration configuration) {
        super(configuration);
    }

    @Override
    public Genotype<FemalePreferenceGene> getNewGenotype() {
        ArrayList<FemalePreferenceGene> genes = new ArrayList<>();
        int individualsPerSpecies = ((CWDExtraConfiguration) configuration.getExtraConfiguration())
                .getIndividualsPerSpecies();
        List<Integer> malePrefs = Randomizer.randomize(individualsPerSpecies, 0, individualsPerSpecies - 1);
        List<Integer> dogPrefs = Randomizer.randomize(individualsPerSpecies, 0, individualsPerSpecies - 1);
        for (int i = 0; i < malePrefs.size(); i++) {
            FemalePreferenceGene newGene = new FemalePreferenceGene(i, malePrefs.get(i), dogPrefs.get(i));
            genes.add(newGene);
        }
        return GenotypeFactory.createNewGenotype(genes);
    }
}
