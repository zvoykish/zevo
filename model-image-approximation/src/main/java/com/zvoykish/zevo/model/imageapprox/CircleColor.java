package com.zvoykish.zevo.model.imageapprox;

/**
 * Created with IntelliJ IDEA.
 * User: Zvoykish
 * Date: 3/6/14
 * Time: 18:38
 */
public class CircleColor {
    private int red, green, blue;

    public CircleColor(int rgb) {
        int value = 0xff000000 | rgb;
        this.red = ((value >> 16) & 0xFF);
        this.green = ((value >> 8) & 0xFF);
        this.blue = (value & 0xFF);
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }
}
