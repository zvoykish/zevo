package com.zvoykish.zevo.framework.operators.selection;

import com.zvoykish.zevo.framework.EvoConfiguration;
import com.zvoykish.zevo.framework.EvoFunctions;
import com.zvoykish.zevo.framework.entities.Generation;
import com.zvoykish.zevo.framework.entities.Individual;
import com.zvoykish.zevo.framework.operators.Selection;
import com.zvoykish.zevo.utils.Randomizer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 01/05/2010
 * Time: 02:20:19
 */
@SuppressWarnings("UnusedDeclaration")
public class DummySelection extends Selection {
    public DummySelection(EvoConfiguration configuration, EvoFunctions functions) {
        super(configuration, functions);
    }

    @Override
    public List<Individual> getTwoCandidates(Generation generation) {
        List<Individual> indList = generation.getIndividuals();
        List<Integer> randomIndices = Randomizer.randomize(2, 0, indList.size() - 1);
        List<Individual> candidates = new ArrayList<>();
        candidates.add(indList.get(randomIndices.get(0)));
        candidates.add(indList.get(randomIndices.get(1)));
        return candidates;
    }
}
