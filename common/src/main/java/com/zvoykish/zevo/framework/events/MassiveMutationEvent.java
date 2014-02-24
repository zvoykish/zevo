package com.zvoykish.zevo.framework.events;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 10/05/2010
 * Time: 20:46:15
 */
public class MassiveMutationEvent {
    private double factor;

    public MassiveMutationEvent(double factor) {
        this.factor = factor;
    }

    public double getFactor() {
        return factor;
    }
}
