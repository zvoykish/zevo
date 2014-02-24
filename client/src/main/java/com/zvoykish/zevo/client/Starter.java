package com.zvoykish.zevo.client;

import com.zvoykish.zevo.client.listener.console.ConsoleEventsListener;
import com.zvoykish.zevo.client.listener.ui.UIEventsListener;
import com.zvoykish.zevo.framework.*;
import com.zvoykish.zevo.framework.entities.Individual;
import com.zvoykish.zevo.utils.Logger;
import com.zvoykish.zevo.utils.Pair;

import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 10/05/2010
 * Time: 21:01:45
 */
public class Starter {
    private String cfgFilename;
    private boolean isConsole;
    private Logger logger = Logger.getInstance();

    public Starter(String filename, boolean isConsole) {
        this.cfgFilename = filename;
        this.isConsole = isConsole;
    }

    public static void main(String[] args) {
        switch (args.length) {
            case 1:
                new Starter(args[0], false).start();
                break;
            case 2:
                new Starter(args[0], args[1].equalsIgnoreCase("-console")).start();
                break;
            default:
                System.out.println("Usage: Starter [configuration_file] <-console>");
                System.out.println("-console (optional): Run evolution in a console, instead of Swing UI");
                System.exit(-1);
        }

    }

    public void start() {
        EvoConfiguration configuration;
        EvoFunctions functions;
        try {
            configuration = EvoConfigurationFactory.readXMLConfiguration(cfgFilename);
            functions = new EvoFunctions();
            EvolutionListener listener = isConsole ? new ConsoleEventsListener(configuration) : new UIEventsListener(
                    configuration);
            Controller controller = ControllerFactory.getController(configuration, functions, listener);

            long startTime = new Date().getTime();
            long currentTime = new Date().getTime();
            int timeLimit = configuration.getTimeLimit();
            long totalRuntime = timeLimit > 0 ? 1000 * timeLimit : Long.MAX_VALUE;
            long generationsLimit = configuration.getGenerationsLimit();
            Individual bestIndividual = null;
            double bestEvaluation = -1;
            int generationsCount = 0;
            while (currentTime - startTime < totalRuntime &&
                    (generationsLimit == -1 || generationsCount <= generationsLimit))
            {
                logger.log("Advancing to next generation... (Time left: " +
                        ((startTime + totalRuntime - currentTime) / 1000) + " seconds)");
                Pair<Individual, Double> generationFinest = controller.advanceSingleGeneration();
                Double newBestEvaluation = generationFinest.getSecond();
                if (newBestEvaluation > bestEvaluation) {
                    bestIndividual = generationFinest.getFirst();
                    bestEvaluation = newBestEvaluation;
                }
                generationsCount++;
                currentTime = new Date().getTime();
            }

            // Summary notes
            logger.log("Evolutionary computation finished after " + generationsCount + " generations.");

            // Output best individual to file
            String filename = logger.getFilename();
            int dotIndex = filename.indexOf(".");
            filename = filename.substring(0, dotIndex) + "_Individual" + filename.substring(dotIndex);
            logger.log("Writing best individual with evaluation=" + bestEvaluation + " to " + filename);
            PrintWriter writer = new PrintWriter(filename);
            writer.println(bestIndividual);
            writer.close();

            if (bestIndividual != null) {
                writer = new PrintWriter("pretty_ind.txt");
                writer.print(bestIndividual.toPrettyString());
                writer.close();
            }

            listener.onFinish();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        finally {
            logger.close();
        }
    }
}
