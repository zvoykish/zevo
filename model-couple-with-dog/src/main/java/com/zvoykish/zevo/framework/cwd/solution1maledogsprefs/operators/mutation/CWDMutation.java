package com.zvoykish.zevo.framework.cwd.solution1maledogsprefs.operators.mutation;

import com.zvoykish.zevo.framework.EvoConfiguration;
import com.zvoykish.zevo.framework.GenotypeFactory;
import com.zvoykish.zevo.framework.IndividualFactory;
import com.zvoykish.zevo.framework.cwd.CWDExtraConfiguration;
import com.zvoykish.zevo.framework.entities.Individual;
import com.zvoykish.zevo.framework.genetics.Genotype;
import com.zvoykish.zevo.framework.operators.Mutation;
import com.zvoykish.zevo.model.cwd.solution1maledogsprefs.genetics.FemalePreferenceGene;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 01/05/2010
 * Time: 16:53:12
 */
public class CWDMutation extends Mutation<FemalePreferenceGene> {

    public CWDMutation(EvoConfiguration configuration) {
        super(configuration);
    }

    @Override
    public Individual<FemalePreferenceGene> doMutation(Individual<FemalePreferenceGene> individual) {
        Genotype<FemalePreferenceGene> genotype = individual.getGenotype();
        int individualsPerSpecies = ((CWDExtraConfiguration) configuration.getExtraConfiguration())
                .getIndividualsPerSpecies();
        List<FemalePreferenceGene> newGenes = new ArrayList<>();
        List<FemalePreferenceGene> genes = genotype.getGenes();
        for (int i = 0; i < genes.size(); i++) {
            FemalePreferenceGene gene = genes.get(i);
            if (Math.random() < getProbabilityForMutation()) {
                FemalePreferenceGene newGene;
                // 50% for mutating the male preference, 50% for mutating the dog preference
                if (Math.random() < 0.5) {
                    // Mutate the male pref.
                    int newChosenMale = (1 + gene.getChosenMale()) % individualsPerSpecies;
                    newGene = new FemalePreferenceGene(i, newChosenMale, gene.getChosenDog());
                }
                else {
                    // Mutate the dog pref.
                    int newChosenDog = (1 + gene.getChosenDog()) % individualsPerSpecies;
                    newGene = new FemalePreferenceGene(i, gene.getChosenMale(), newChosenDog);
                }
                newGenes.add(newGene);
            }
            else {
                newGenes.add(gene);
            }
        }
        Genotype<FemalePreferenceGene> newGenotype = GenotypeFactory.createNewGenotype(newGenes);
        return IndividualFactory.createNewIndividual(newGenotype);
    }

}
