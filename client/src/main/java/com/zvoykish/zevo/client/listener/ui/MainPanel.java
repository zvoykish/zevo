/*
 * Created by JFormDesigner on Mon May 10 22:39:37 IDT 2010
 */

package com.zvoykish.zevo.client.listener.ui;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.zvoykish.zevo.framework.entities.Individual;
import com.zvoykish.zevo.framework.events.NewGenerationEvent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Zvika
 */
public class MainPanel extends JPanel {
    private double avgEvaluation;
    private double bestEvaluation;
    private Individual bestIndividual;
    private int generationsCount;

    public MainPanel() {
        initComponents();
        initListeners();
        avgEvaluation = 0;
        bestEvaluation = -1;
        generationsCount = 0;
    }

    private void initListeners() {
        chkUpdateIndividual.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chkUpdateIndividual.isSelected() && bestIndividual != null) {
                    updateIndividual();
                }
            }
        });
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        JLabel label1 = new JLabel();
        lblGenerations = new JLabel();
        JLabel label3 = new JLabel();
        lblLastEvaluation = new JLabel();
        JLabel label5 = new JLabel();
        lblAverageEvaluation = new JLabel();
        JLabel label7 = new JLabel();
        lblMaxEvaluation = new JLabel();
        chkUpdateIndividual = new JCheckBox();
        JLabel label2 = new JLabel();
        scrollPane = new JScrollPane();
        CellConstraints cc = new CellConstraints();

        //======== this ========
        setLayout(new FormLayout(
                "default, $lcgap, default:grow(0.01)",
                "6*(default, $lgap), default:grow(0.01)"));

        //---- label1 ----
        label1.setText("Generations");
        add(label1, cc.xy(1, 1));
        add(lblGenerations, cc.xy(3, 1));

        //---- label3 ----
        label3.setText("Last Evaluation");
        add(label3, cc.xy(1, 3));
        add(lblLastEvaluation, cc.xy(3, 3));

        //---- label5 ----
        label5.setText("Avg. Evaluation");
        add(label5, cc.xy(1, 5));
        add(lblAverageEvaluation, cc.xy(3, 5));

        //---- label7 ----
        label7.setText("Max. Evaluation");
        add(label7, cc.xy(1, 7));
        add(lblMaxEvaluation, cc.xy(3, 7));

        //---- chkUpdateIndividual ----
        chkUpdateIndividual.setText("Update Best Individual?");
        add(chkUpdateIndividual, cc.xy(1, 9));

        //---- label2 ----
        label2.setText("Best Individual:");
        add(label2, cc.xy(1, 11));
        add(scrollPane, cc.xywh(1, 13, 3, 1));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel lblGenerations;
    private JLabel lblLastEvaluation;
    private JLabel lblAverageEvaluation;
    private JLabel lblMaxEvaluation;
    private JCheckBox chkUpdateIndividual;
    private JScrollPane scrollPane;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public void handle(NewGenerationEvent event) {
        lblGenerations.setText(String.valueOf(event.getGenerationNumber()));

        double currentEvaluation = event.getBestEvaluation();
        lblLastEvaluation.setText(String.valueOf(currentEvaluation));

        avgEvaluation = ((generationsCount * avgEvaluation) + currentEvaluation) / (generationsCount + 1);
        lblAverageEvaluation.setText(String.valueOf(avgEvaluation));

        if (currentEvaluation > bestEvaluation) {
            bestEvaluation = currentEvaluation;
            bestIndividual = event.getBestIndividual();
            lblMaxEvaluation.setText(String.valueOf(bestEvaluation));
            if (chkUpdateIndividual.isSelected()) {
                updateIndividual();
            }
        }

        generationsCount++;
    }

    private void updateIndividual() {
        scrollPane.setViewportView(bestIndividual.getAsComponent());
    }

    public void onFinish() {
        lblGenerations.setText(lblGenerations.getText() + " (DONE!)");
    }
}
