package com.zvoykish.zevo.framework.entities;

import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 01/05/2010
 * Time: 01:06:24
 */
public interface Generation extends Serializable {
    List<Individual> getIndividuals();

    void addIndividual(Individual i);
}
