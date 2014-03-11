package com.zvoykish.zevo.framework;

import com.zvoykish.zevo.framework.entities.Generation;
import com.zvoykish.zevo.framework.entities.GenerationImpl;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 01/05/2010
 * Time: 02:20:55
 */
public class GenerationFactory {
    private static ThreadLocal<Integer> generationNumber = new ThreadLocal<>();

    public static Generation createEmptyGeneration() {
        return new GenerationImpl();
    }

    public static Integer getGenerationNumber() {
        return generationNumber.get();
    }

    public static void setGenerationNumber(Integer generationNumber) {
        GenerationFactory.generationNumber.set(generationNumber);
    }
}
