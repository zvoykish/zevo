package com.zvoykish.zevo.utils;

import com.zvoykish.zevo.framework.EvoConfiguration;

/**
 * Created with IntelliJ IDEA.
 * User: Zvoykish
 * Date: 3/7/14
 * Time: 15:30
 */
public class ZevoUtils {
    public static int getWorkerCount(EvoConfiguration configuration) {
        return Math.min(configuration.getMinimumNumberOfWorkerThreadsHint(), configuration.getIndividualsCount());
    }
}
