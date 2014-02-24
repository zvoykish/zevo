package com.zvoykish.zevo.model.cwd.solution2binaryflags;

import com.zvoykish.zevo.framework.EvoConfiguration;
import com.zvoykish.zevo.framework.cwd.CWDExtraConfiguration;
import com.zvoykish.zevo.framework.entities.Individual;
import com.zvoykish.zevo.framework.genetics.Genotype;
import com.zvoykish.zevo.model.Evaluator;
import com.zvoykish.zevo.model.genetics.BinaryGene;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 07/05/2010
 * Time: 02:31:19
 */


@SuppressWarnings("UnusedDeclaration")
public class CWDEvaluator extends Evaluator<BinaryGene> {
    private final int individualsPerSpecies;
    private final double ILLEGAL_INDIVIDUAL_EVALUATION = -1;

    public CWDEvaluator(EvoConfiguration configuration) {
        super(configuration);
        individualsPerSpecies = ((CWDExtraConfiguration) configuration.getExtraConfiguration())
                .getIndividualsPerSpecies();
    }

    @Override
    public double evaluate(Individual<BinaryGene> individual) {
        // Result map will be key (Male+Female) --> Dog
        boolean[] malesTaken = new boolean[individualsPerSpecies];
        boolean[] dogsTaken = new boolean[individualsPerSpecies];

        Map<Integer, MatchKey> resultMap = new HashMap<>();
        Genotype<BinaryGene> genotype = individual.getGenotype();
        List<BinaryGene> genes = genotype.getGenes();
        int dogsOffset = individualsPerSpecies * individualsPerSpecies;

        // Go over all females (i is the female index - Fi)
        for (int i = 0; i < individualsPerSpecies; i++) {
            boolean found = false;
            int endGeneIndex = (i + 1) * individualsPerSpecies;
            MatchKey matchKey = resultMap.get(i);
            for (int j = i * individualsPerSpecies; j < endGeneIndex; j++) {
                BinaryGene gene = genes.get(j);
                int currentMale = j - (i * individualsPerSpecies);
                if (gene.getValue() == 1 && !malesTaken[currentMale] && (matchKey == null || !matchKey.hasMale())) {
                    malesTaken[currentMale] = true;
                    found = true;
                    if (matchKey == null) {
                        matchKey = new MatchKey();
                        resultMap.put(i, matchKey);
                    }
                    matchKey.setMale(currentMale);
                    break;
                }
            }

            if (!found) {
                return ILLEGAL_INDIVIDUAL_EVALUATION;
            }

            found = false;
            for (int j = dogsOffset + (i * individualsPerSpecies); j < dogsOffset + endGeneIndex; j++) {
                BinaryGene gene = genes.get(j);
                int currentDog = j - (dogsOffset + (i * individualsPerSpecies));
                if (gene.getValue() == 1 && !dogsTaken[currentDog] && !matchKey.hasDog()) {
                    dogsTaken[currentDog] = true;
                    found = true;
                    matchKey.setDog(currentDog);
                    break;
                }
            }

            if (!found) {
                return ILLEGAL_INDIVIDUAL_EVALUATION;
            }

            if (resultMap.size() == individualsPerSpecies) {
                break;
            }
        }

        return getEvaluationOfMatches(resultMap);
    }

    private double getEvaluationOfMatches(Map<Integer, MatchKey> map) {
        CWDExtraConfiguration extraCfg = (CWDExtraConfiguration) configuration.getExtraConfiguration();
        double result = 0.0;
        // Add in advance the invertor for the 6 preferences below times the map size (iterations)
        result += (individualsPerSpecies - 1) * 6 * map.size();
        for (Map.Entry<Integer, MatchKey> entry : map.entrySet()) {
            MatchKey entryValue = entry.getValue();
            int female = entry.getKey();
            int male = entryValue.getMale();
            int dog = entryValue.getDog();
            result -= extraCfg.getFemaleMalePref(female, male) + extraCfg.getMaleFemalePref(male, female);
            result -= extraCfg.getFemaleDogPref(female, dog) + extraCfg.getDogFemalePref(dog, female);
            result -= extraCfg.getMaleDogPref(male, dog) + extraCfg.getDogMalePref(dog, male);
        }

        return result;
    }

    private final class MatchKey implements Comparable<MatchKey> {
        private int male;
        private int dog;

        public MatchKey() {
            this.male = -1;
            this.dog = -1;
        }

        @Override
        public int hashCode() {
            return ((1 + individualsPerSpecies) * male) + dog;
        }

        @Override
        public int compareTo(MatchKey o) {
            return hashCode() - o.hashCode();
        }

        public int getMale() {
            return male;
        }

        public void setMale(int male) {
            this.male = male;
        }

        public int getDog() {
            return dog;
        }

        public void setDog(int dog) {
            this.dog = dog;
        }

        public boolean hasMale() {
            return male != -1;
        }

        public boolean hasDog() {
            return dog != -1;
        }

        public boolean isComplete() {
            return male != -1 && dog != -1;
        }
    }
}
