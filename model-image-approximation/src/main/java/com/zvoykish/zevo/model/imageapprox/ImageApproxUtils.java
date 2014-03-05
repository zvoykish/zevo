package com.zvoykish.zevo.model.imageapprox;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created with IntelliJ IDEA.
 * User: Zvoykish
 * Date: 3/5/14
 * Time: 00:07
 */
public class ImageApproxUtils {
    public static Color[] getImagePixels(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        return getImagePixels(image, width, height);
    }

    public static Color[] getImagePixels(BufferedImage image, int width, int height) {
        int[] rgbValues = image.getRGB(0, 0, width, height, null, 0, width);
        Color[] pixels = new Color[rgbValues.length];
        for (int i = 0; i < rgbValues.length; i++) {
            pixels[i] = new Color(rgbValues[i]);
        }
        return pixels;
    }
}
