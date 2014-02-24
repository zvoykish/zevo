package com.zvoykish.zevo.client.listener.ui;

import com.zvoykish.zevo.client.listener.AbstractListener;
import com.zvoykish.zevo.framework.EvoConfiguration;
import com.zvoykish.zevo.framework.events.MassiveMutationEvent;
import com.zvoykish.zevo.framework.events.NewGenerationEvent;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 10/05/2010
 * Time: 22:04:22
 */
public class UIEventsListener extends AbstractListener {
    private MainPanel panel;

    public UIEventsListener(EvoConfiguration configuration) {
        super(configuration);
    }

    @Override
    public void onNewGeneration(NewGenerationEvent event) {
        super.onNewGeneration(event);
        panel.handle(event);
    }

    @Override
    public void onStart() {
        super.onStart();
        panel = new MainPanel();
        JFrame frame = new JFrame("Evolutionary Computation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        Dimension dimension = new Dimension(800, 600);
        frame.setPreferredSize(dimension);
        frame.setMinimumSize(dimension);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void onMassiveMutation(MassiveMutationEvent event) {
        // do nothing (?)
    }

    @Override
    public void onFinish() {
        panel.onFinish();
    }
}
