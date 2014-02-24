package com.zvoykish.zevo.utils;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import java.io.FileWriter;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 01/05/2010
 * Time: 01:03:22
 */
public class XMLUtils {
    public static void createXmlFile(String filename, Element rootElement) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(filename);
            XMLOutputter outputter = new XMLOutputter();
            outputter.output(rootElement != null ? rootElement : new Element("EmptyXml"), writer);
        }
        catch (Exception e) {
            throw new RuntimeException("Failed creating xml file " + filename + " from element - " + rootElement);
        }
        finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            }
            catch (Exception ignored) {
            }
        }
    }

    public static Element getXmlFileRootElement(String filename) {
        InputStream inputStream = null;
        SAXBuilder builder = new SAXBuilder();
        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
            Document document = builder.build(inputStream);
            return document.detachRootElement();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                catch (Exception ignored) {
                }
            }
        }
    }
}
