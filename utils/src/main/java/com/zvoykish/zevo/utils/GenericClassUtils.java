package com.zvoykish.zevo.utils;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 01/05/2010
 * Time: 00:57:49
 */
public class GenericClassUtils {

    protected static boolean isClassADerivedFromClassB(Class classA, Class classB) {
        return classA != null && classB.isAssignableFrom(classA);
    }

}
