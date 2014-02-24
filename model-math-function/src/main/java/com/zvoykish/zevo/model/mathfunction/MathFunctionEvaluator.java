package com.zvoykish.zevo.model.mathfunction;

import com.zvoykish.zevo.framework.EvoConfiguration;
import com.zvoykish.zevo.framework.entities.Individual;
import com.zvoykish.zevo.model.Evaluator;
import com.zvoykish.zevo.model.genetics.BinaryGene;

import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 05/05/2010
 * Time: 00:20:12
 */
@SuppressWarnings("UnusedDeclaration")
public class MathFunctionEvaluator extends Evaluator<BinaryGene> {
    public MathFunctionEvaluator(EvoConfiguration configuration) {
        super(configuration);
    }

    @Override
    public double evaluate(Individual<BinaryGene> individual) {
        int number = 0;
        List<BinaryGene> geneList = individual.getGenotype().getGenes();
        Iterator<BinaryGene> geneIterator = geneList.iterator();
        int power = geneList.size() - 1;
        while (geneIterator.hasNext()) {
            BinaryGene gene = geneIterator.next();
            if (gene.getValue() == 1) {
                number += Math.pow(2, power);
            }
            power--;
        }
        return 1 + calcMathFunction(number);
    }

    private double calcMathFunction(double... args) {
//        return args[0] * args[0];
        return Math.pow(args[0], 3) - Math.pow(args[0], 2) + args[0] - 5;
    }
}
