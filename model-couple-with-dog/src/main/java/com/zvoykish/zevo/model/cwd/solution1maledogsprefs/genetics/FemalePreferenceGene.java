package com.zvoykish.zevo.model.cwd.solution1maledogsprefs.genetics;

import com.zvoykish.zevo.model.genetics.Gene;
import com.zvoykish.zevo.utils.IconUtils;

import javax.swing.*;
import java.awt.*;

/**
 * <b>IMMUTABLE</b> Gene class used to represent a female's choice in a solution (genotype).
 * Note that due to cross-over operations there might be several genes with a same
 * male choice or same dog choice.
 * This is handled by the evaluation operator, and considered as a 99%-chance-to-happen choice :)
 * <br/><br/>
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 01/05/2010
 * Time: 17:44:59
 */
public final class FemalePreferenceGene implements Gene {
    private final int female;
    private final int chosenMale;
    private final int chosenDog;
    private final static ImageIcon FEMALE_ICON = IconUtils.createImageIcon("images/female32.png", "Female icon");
    private final static ImageIcon MALE_ICON = IconUtils.createImageIcon("images/male32.png", "Male icon");
    private final static ImageIcon DOG_ICON = IconUtils.createImageIcon("images/dog32.png", "Dog icon");

    public FemalePreferenceGene(int female, int chosenMale, int chosenDog) {
        this.female = female;
        this.chosenMale = chosenMale;
        this.chosenDog = chosenDog;
    }

    public int getChosenMale() {
        return chosenMale;
    }

    public int getChosenDog() {
        return chosenDog;
    }

    @Override
    public String toString() {
        return "F" + female + " - M" + chosenMale + " - D" + chosenDog;
    }

    @Override
    public Component getAsComponent() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(String.valueOf(female), FEMALE_ICON, JLabel.HORIZONTAL));
        panel.add(new JLabel(String.valueOf(chosenMale), MALE_ICON, JLabel.HORIZONTAL));
        panel.add(new JLabel(String.valueOf(chosenDog), DOG_ICON, JLabel.HORIZONTAL));
        return panel;
    }
}
