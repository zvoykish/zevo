package com.zvoykish.zevo.model.imageapprox;

import com.zvoykish.zevo.framework.EvoConfiguration;
import com.zvoykish.zevo.framework.genetics.Genotype;
import com.zvoykish.zevo.framework.genetics.GenotypeImpl;
import com.zvoykish.zevo.model.GenesisInitializer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Zvoykish
 * Date: 3/4/14
 * Time: 22:01
 */
public class ImageApproxGenesisInitializer extends GenesisInitializer<CircleGene> {
    public ImageApproxGenesisInitializer(EvoConfiguration configuration) {
        super(configuration);
    }

    @Override
    public Genotype<CircleGene> getNewGenotype() {
        List<CircleGene> genes = new ArrayList<>();
        ImageApproxExtraConfiguration extra = (ImageApproxExtraConfiguration) configuration.getExtraConfiguration();
        int width = extra.getWidth();
        int height = extra.getHeight();
        for (int i = 0; i < extra.getNumOfCircles(); i++) {
            int x = ((int) (Math.random() * (width + 10))) - 5;
            int y = ((int) (Math.random() * (height + 10))) - 5;
            double radius = Math.random() * (Math.max(width, height));
            genes.add(new CircleGene(x, y, radius, getRandomColor(), getRandomColor(), getRandomColor(), 0.0));
        }
        return new GenotypeImpl<>(genes);
    }

    private int getRandomColor() {
        return ((int) (Math.random() * 256));
    }
}
