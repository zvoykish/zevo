package com.zvoykish.zevo.model.cwd.solution1maledogsprefs;

import com.zvoykish.zevo.framework.EvoConfiguration;
import com.zvoykish.zevo.framework.cwd.CWDExtraConfiguration;
import com.zvoykish.zevo.framework.entities.Individual;
import com.zvoykish.zevo.model.Evaluator;
import com.zvoykish.zevo.model.cwd.solution1maledogsprefs.genetics.FemalePreferenceGene;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 02/05/2010
 * Time: 01:12:16
 */
@SuppressWarnings({"UnusedDeclaration"})
public class CWDEvaluator extends Evaluator<FemalePreferenceGene> {
    private CWDExtraConfiguration extraConfiguration;
    private int individualsPerSpecies;

    public CWDEvaluator(EvoConfiguration configuration) {
        super(configuration);
        extraConfiguration = (CWDExtraConfiguration) configuration.getExtraConfiguration();
        individualsPerSpecies = extraConfiguration.getIndividualsPerSpecies();
    }

    @Override
    public double evaluate(Individual<FemalePreferenceGene> individual) {
        List<FemalePreferenceGene> genes = individual.getGenotype().getGenes();
        List<Integer[]> genesLeftToMatch = new ArrayList<>();
        boolean[] malesFlags = new boolean[individualsPerSpecies];
        boolean[] dogsFlags = new boolean[individualsPerSpecies];

        double result = (individualsPerSpecies - 1) * 6 * genes.size();
        for (int i = 0; i < genes.size(); i++) {
            FemalePreferenceGene gene = genes.get(i);
            int chosenMale = gene.getChosenMale();
            int chosenDog = gene.getChosenDog();
            if (!malesFlags[chosenMale] && !dogsFlags[chosenDog]) {
                // Good match, can calculate and remove from map + list
                result -= extraConfiguration.getFemaleMalePref(i, chosenMale);
                result -= extraConfiguration.getFemaleDogPref(i, chosenDog);
                result -= extraConfiguration.getMaleFemalePref(chosenMale, i);
                result -= extraConfiguration.getMaleDogPref(chosenMale, chosenDog);
                result -= extraConfiguration.getDogFemalePref(chosenDog, i);
                result -= extraConfiguration.getDogMalePref(chosenDog, chosenMale);
                malesFlags[chosenMale] = true;
                dogsFlags[chosenDog] = true;
            }
            else {
                if (!malesFlags[chosenMale]) {
                    // Male is a good match, let's remove it from the map+list and keep aside
                    // So we'll choose a dog later on
                    malesFlags[chosenMale] = true;
                    genesLeftToMatch.add(new Integer[]{i, chosenMale, chosenDog, -1, 1});
                }
                else if (!dogsFlags[chosenDog]) {
                    // Dog is a good match, let's remove it from the map+list and keep aside
                    // So we'll choose a male later on
                    dogsFlags[chosenDog] = true;
                    genesLeftToMatch.add(new Integer[]{i, chosenMale, chosenDog, 1, -1});
                }
                else {
                    // Match was taken already as is, need to re-choose later.
                    genesLeftToMatch.add(new Integer[]{i, chosenMale, chosenDog, 1, 1});
                }
            }
        }

        for (Integer[] indices : genesLeftToMatch) {
            int femaleIndex = indices[0];
            int maleIndex = indices[1];
            int dogIndex = indices[2];

            if (indices[3] == 1) {
                maleIndex = chooseEntityClosestTo(maleIndex, malesFlags);
            }

            if (indices[4] == 1) {
                dogIndex = chooseEntityClosestTo(dogIndex, dogsFlags);
            }

            result -= extraConfiguration.getFemaleMalePref(femaleIndex, maleIndex);
            result -= extraConfiguration.getFemaleDogPref(femaleIndex, dogIndex);
            result -= extraConfiguration.getMaleFemalePref(maleIndex, femaleIndex);
            result -= extraConfiguration.getMaleDogPref(maleIndex, dogIndex);
            result -= extraConfiguration.getDogFemalePref(dogIndex, femaleIndex);
            result -= extraConfiguration.getDogMalePref(dogIndex, maleIndex);
            malesFlags[maleIndex] = true;
            dogsFlags[dogIndex] = true;
        }

        return result;
    }

    private int chooseEntityClosestTo(int wantedIndex, boolean[] entitiesFlags) {
        int index = wantedIndex;
        while (entitiesFlags[index]) {
            index = (index + 1) % individualsPerSpecies;
        }
        return index;
    }

}
