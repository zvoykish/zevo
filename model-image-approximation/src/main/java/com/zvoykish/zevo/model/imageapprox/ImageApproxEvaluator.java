package com.zvoykish.zevo.model.imageapprox;

import com.zvoykish.zevo.framework.EvoConfiguration;
import com.zvoykish.zevo.framework.GenerationFactory;
import com.zvoykish.zevo.framework.entities.Individual;
import com.zvoykish.zevo.framework.genetics.Genotype;
import com.zvoykish.zevo.model.Evaluator;
import com.zvoykish.zevo.utils.Logger;
import com.zvoykish.zevo.utils.ZevoUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Zvoykish
 * Date: 3/4/14
 * Time: 22:33
 */
public class ImageApproxEvaluator extends Evaluator<CircleGene> {
    private final VelocityEngine velocityEngine;
    private final Logger logger;
    private Path htmlPath;
    private Path imagesPath;
    private int width;
    private int height;
    private final ImageApproxExtraConfiguration extra;
    private HtmlRenderer renderer;
    private LinkedHashMap<Long, Double> cache = new LinkedHashMap<>(500, 0.8f, true);
    private final LinkedHashMap<Integer, Boolean> folderExistanceCache = new LinkedHashMap<>(500, 0.8f, true);

    public ImageApproxEvaluator(EvoConfiguration configuration) {
        super(configuration);
        logger = Logger.getInstance();
        extra = (ImageApproxExtraConfiguration) configuration.getExtraConfiguration();
        width = extra.getWidth();
        height = extra.getHeight();

        renderer = new HtmlRenderer(extra);
        try {
            String now = String.valueOf(System.currentTimeMillis());
            htmlPath = Paths.get(extra.getHtmlOutputPath(), now);
            if (!htmlPath.toFile().exists()) {
                htmlPath = Files.createDirectories(htmlPath);
            }
            imagesPath = Paths.get(extra.getPngOutputPath(), now);
            if (!imagesPath.toFile().exists()) {
                imagesPath = Files.createDirectories(imagesPath);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            e.printStackTrace(logger.getWriter());
            throw new RuntimeException(e);
        }

        Properties p = new Properties();
        p.put("resource.loader", "class");
        p.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        p.put("parser.pool.size", ZevoUtils.getWorkerCount(configuration));
        velocityEngine = new VelocityEngine(p);
    }

    @Override
    public double evaluate(Individual<CircleGene> individual) {
        long id = individual.getId();
        if (cache.containsKey(id)) {
            return cache.get(id);
        }

        try {
            VelocityContext context = new VelocityContext();
            context.put("svgjs", extra.getSvgJsLocation());
            context.put("width", width);
            context.put("height", height);
            context.put("widthpx", width + "px");
            context.put("heightpx", height + "px");
            Genotype<CircleGene> genotype = individual.getGenotype();
            Circle[] circles = new Circle[genotype.length()];
            for (int i = 0; i < genotype.length(); i++) {
                circles[i] = new Circle(genotype.getGene(i));
            }
            context.put("circles", circles);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(os);
            velocityEngine.mergeTemplate("circles.vm", "utf-8", context, writer);
            writer.flush();

            Integer generationNumber = GenerationFactory.getGenerationNumber();
            String gen = "G" + padded(generationNumber, 7);
            String ind = "I" + padded(id, 3);
            String htmlPathStr = htmlPath.toString();
            Path path = Paths.get(htmlPathStr, gen);
            if (!folderExistanceCache.containsKey(generationNumber)) {
                synchronized (folderExistanceCache) {
                    if (!(new File(path.toString()).exists())) {
                        try {
                            Files.createDirectory(path);
                            folderExistanceCache.put(generationNumber, true);
                        }
                        catch (FileAlreadyExistsException ignored) {
                        }
                    }
                }
            }

            Path tempFile = Files.createFile(Paths.get(htmlPathStr, gen, ind + ".html"));
            Files.write(tempFile, os.toByteArray());
            try {
                writer.close();
            }
            catch (Exception ignored) {
            }

            File pngFile = renderer.htmlToPng(tempFile.toFile(), imagesPath, gen, ind);
            BufferedImage bufferedImage = ImageIO.read(pngFile);

//            Files.delete(tempFile);
//            Files.delete(Paths.get(pngFile.toURI()));
            CircleColor[] pixels = ImageApproxUtils.getImagePixels(bufferedImage, width, height);
            CircleColor[] expectedPixels = extra.getPixels();
            int maxEval = expectedPixels.length * 256 * 3;
            long diff = maxEval;
            for (int i = 0; i < expectedPixels.length; i++) {
                CircleColor p = pixels[i];
                CircleColor e = expectedPixels[i];
                diff -= Math.abs(p.getRed() - e.getRed());
                diff -= Math.abs(p.getGreen() - e.getGreen());
                diff -= Math.abs(p.getBlue() - e.getBlue());
            }

            double percentage = (double) diff / maxEval * 100;
            System.out.println(pngFile.getName() + ": " + diff + '/' + maxEval + " ~ " + percentage);

            cache.put(id, percentage);
            return percentage;
        }
        catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace(logger.getWriter());
            logger.log("Due to failure in evaluation - returning negative value to ignore individual: " + individual);
            return Double.MIN_VALUE;
        }
    }

    private String padded(long id, int digits) {
        String s = String.valueOf(id);
        StringBuilder sb = new StringBuilder();
        for (int i = s.length(); i < digits; i++) {
            sb.append('0');
        }
        return sb.append(s).toString();
    }
}
