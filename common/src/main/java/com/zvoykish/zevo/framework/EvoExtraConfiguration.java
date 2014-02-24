package com.zvoykish.zevo.framework;

import com.zvoykish.zevo.utils.XMLUtils;
import org.jdom.Element;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 01/05/2010
 * Time: 17:02:15
 */
public abstract class EvoExtraConfiguration {
    private static final String TAG_MODEL_EXTRA = "ModelExtra";

    public void initExtraConfiguration(String configFilename) {
        Element rootElement = XMLUtils.getXmlFileRootElement(configFilename);
        if (rootElement != null) {
            // Fetch ModelExtra element and parse it
            Element modelExtraElement = rootElement.getChild(TAG_MODEL_EXTRA);
            parseModelExtraElement(modelExtraElement);
        }
    }

    protected abstract void parseModelExtraElement(Element modelExtraElement);

}
