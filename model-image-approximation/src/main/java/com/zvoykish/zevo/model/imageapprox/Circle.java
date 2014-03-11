package com.zvoykish.zevo.model.imageapprox;

/**
 * Created with IntelliJ IDEA.
 * User: Zvoykish
 * Date: 3/5/14
 * Time: 02:13
 */
@SuppressWarnings("UnusedDeclaration")
public class Circle {
    private double radius;
    private String rgb;
    private double x;
    private double y;
    private double opacity;

    public Circle(CircleGene gene) {
        this.radius = gene.getRadius();
        this.x = gene.getX();
        this.y = gene.getY();
        String r = toHexWithPadding(gene.getR());
        String g = toHexWithPadding(gene.getG());
        String b = toHexWithPadding(gene.getB());
        this.rgb = "'#" + r + g + b + '\'';
        this.opacity = gene.getOpacity();
    }

    private String toHexWithPadding(int r1) {
        String s = Integer.toHexString(r1);
        return (s.length() == 1) ? ("0" + s) : s;
    }

    public double getRadius() {
        return radius;
    }

    public String getRgb() {
        return rgb;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getOpacity() {
        return opacity;
    }
}
