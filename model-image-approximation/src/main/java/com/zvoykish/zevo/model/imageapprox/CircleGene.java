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
    private double opacity;

    public CircleGene(double x, double y, double radius, int r, int g, int b, double opacity) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.r = Math.max(Math.min(r, 255), 0);
        this.g = Math.max(Math.min(g, 255), 0);
        this.b = Math.max(Math.min(b, 255), 0);
        this.opacity = opacity;
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

    public double getOpacity() {
        return opacity;
    }

    public CircleGene buildWithX(double x) {
        return new CircleGene(x, y, radius, r, g, b, opacity);
    }

    public CircleGene buildWithY(double y) {
        return new CircleGene(x, y, radius, r, g, b, opacity);
    }

    public CircleGene buildWithRadius(double radius) {
        return new CircleGene(x, y, radius, r, g, b, opacity);
    }

    public CircleGene buildWithR(int r) {
        return new CircleGene(x, y, radius, r, g, b, opacity);
    }

    public CircleGene buildWithG(int g) {
        return new CircleGene(x, y, radius, r, g, b, opacity);
    }

    public CircleGene buildWithB(int b) {
        return new CircleGene(x, y, radius, r, g, b, opacity);
    }

    public CircleGene buildWithOpacity(double opacity) {
        return new CircleGene(x, y, radius, r, g, b, opacity);
    }

    @Override
    public Component getAsComponent() {
        return new JPanel();
    }

    @Override
    public String toString() {
        return "{" +
                "x=" + x +
                ", y=" + y +
                ", radius=" + radius +
                ", r=" + r +
                ", g=" + g +
                ", b=" + b +
                ", opacity=" + opacity +
                '}';
    }
}
