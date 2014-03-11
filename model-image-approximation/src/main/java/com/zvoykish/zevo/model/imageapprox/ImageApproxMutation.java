package com.zvoykish.zevo.model.imageapprox;

import com.zvoykish.zevo.framework.EvoConfiguration;
import com.zvoykish.zevo.framework.GenotypeFactory;
import com.zvoykish.zevo.framework.IndividualFactory;
import com.zvoykish.zevo.framework.entities.Individual;
import com.zvoykish.zevo.framework.genetics.Genotype;
import com.zvoykish.zevo.framework.operators.Mutation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Zvoykish
 * Date: 3/4/14
 * Time: 22:18
 */
public class ImageApproxMutation extends Mutation<CircleGene> {
    public ImageApproxMutation(EvoConfiguration configuration) {
        super(configuration);
    }

    @Override
    public Individual<CircleGene> doMutation(Individual<CircleGene> individual) {
        Genotype<CircleGene> genotype = individual.getGenotype();
        List<CircleGene> newGenes = new ArrayList<>();
        for (CircleGene gene : genotype.getGenes()) {
            if (gene == null) {
                throw new RuntimeException("Trying to mutate a null gene!");
            }

            if (Math.random() < getProbabilityForMutation()) {
                CircleGene newGene;
                switch ((int) (Math.random() * 7)) {
                    case 0:
                        newGene = gene.buildWithX(gene.getX() + (Math.random() * 10) - 5);
                        break;
                    case 1:
                        newGene = gene.buildWithY(gene.getY() + (Math.random() * 10) - 5);
                        break;
                    case 2:
                        newGene = gene.buildWithRadius(gene.getRadius() + (Math.random() * 10) - 5);
                        break;
                    case 3:
                        newGene = gene.buildWithR(gene.getR() + ((int) ((Math.random() * 10) - 5)));
                        break;
                    case 4:
                        newGene = gene.buildWithG(gene.getG() + ((int) ((Math.random() * 10) - 5)));
                        break;
                    case 5:
                        newGene = gene.buildWithB(gene.getB() + ((int) ((Math.random() * 10) - 5)));
                        break;
                    case 6:
                        double opacityOffset = (Math.random() * 0.02) - 0.01;
                        double newOpacity = gene.getOpacity() + opacityOffset;
                        newGene = gene.buildWithOpacity(Math.max(Math.min(newOpacity, 1.0), 0.0));
                        break;
                    default:
                        throw new RuntimeException("WTF?");
                }
                newGenes.add(newGene);
            }
            else {
                newGenes.add(gene);
            }
        }

        Genotype<CircleGene> newGenotype = GenotypeFactory.createNewGenotype(newGenes);
        return IndividualFactory.createNewIndividual(newGenotype);

    }
}
