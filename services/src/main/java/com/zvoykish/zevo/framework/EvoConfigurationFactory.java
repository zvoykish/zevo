package com.zvoykish.zevo.framework;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 01/05/2010
 * Time: 19:09:03
 */
public class EvoConfigurationFactory {
    public static EvoConfiguration readXMLConfiguration(String xmlFilename) {
        return new XMLEvoConfiguration(xmlFilename);
    }
}
