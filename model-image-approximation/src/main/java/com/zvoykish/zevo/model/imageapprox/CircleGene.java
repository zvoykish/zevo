package com.zvoykish.zevo.model.imageapprox;

import com.zvoykish.zevo.model.genetics.Gene;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Zvoykish
 * Date: 3/4/14
 * Time: 22:02
 */
public class CircleGene implements Gene {
    private double x, y, radius;
    private int r, g, b;
    private double transparency;

    public CircleGene(double x, double y, double radius, int r, int g, int b, double transparency) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.r = r;
        this.g = g;
        this.b = b;
        this.transparency = transparency;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getRadius() {
        return radius;
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    public double getTransparency() {
        return transparency;
    }

    public CircleGene buildWithX(double x) {
        return new CircleGene(x, y, radius, r, g, b, transparency);
    }

    public CircleGene buildWithY(double y) {
        return new CircleGene(x, y, radius, r, g, b, transparency);
    }

    public CircleGene buildWithRadius(double radius) {
        return new CircleGene(x, y, radius, r, g, b, transparency);
    }

    public CircleGene buildWithR(int r) {
        return new CircleGene(x, y, radius, r, g, b, transparency);
    }

    public CircleGene buildWithG(int g) {
        return new CircleGene(x, y, radius, r, g, b, transparency);
    }

    public CircleGene buildWithB(int b) {
        return new CircleGene(x, y, radius, r, g, b, transparency);
    }

    public CircleGene buildWithTransparency(double transparency) {
        return new CircleGene(x, y, radius, r, g, b, transparency);
    }

    @Override
    public Component getAsComponent() {
        return new JPanel();
    }
}
