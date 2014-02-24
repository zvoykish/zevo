package com.zvoykish.zevo.model.genetics;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 05/05/2010
 * Time: 00:10:40
 */
public final class BinaryGene implements Gene {
    private final boolean flag;

    public BinaryGene(int zeroOrOne) {
        if (zeroOrOne < 0 || zeroOrOne > 1) {
            throw new RuntimeException("Binary gene must be initialized with 0 or 1 only!");
        }
        flag = zeroOrOne == 1;
    }

    public final int getValue() {
        return flag ? 1 : 0;
    }

    @Override
    public String toString() {
        return flag ? "1" : "0";
    }

    @Override
    public Component getAsComponent() {
        return new JLabel(toString());
    }
}
