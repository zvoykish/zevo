package com.zvoykish.zevo.framework.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 01/05/2010
 * Time: 02:21:29
 */
public class GenerationImpl implements Generation {
    private List<Individual> individuals;

    public GenerationImpl() {
        individuals = new ArrayList<>();
    }

    @Override
    public List<Individual> getIndividuals() {
        return individuals;
    }

    @Override
    public void addIndividual(Individual i) {
        individuals.add(i);
    }

}
