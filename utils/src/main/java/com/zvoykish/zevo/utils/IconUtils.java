package com.zvoykish.zevo.utils;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 01/05/2010
 * Time: 00:59:06
 */
public class IconUtils {
    /**
     * Returns an ImageIcon, or null if the path was invalid.
     *
     * @param path        Relative path to image (from ClassLoader's original class location) (e.g. "images/image1.png")
     * @param description Image description
     * @return Icon based on image file in path
     */
    public static ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = Thread.currentThread().getContextClassLoader().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        }
        else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}
