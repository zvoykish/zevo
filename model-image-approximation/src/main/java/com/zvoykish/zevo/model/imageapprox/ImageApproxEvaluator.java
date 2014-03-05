package com.zvoykish.zevo.model.imageapprox;

import com.zvoykish.zevo.framework.EvoConfiguration;
import com.zvoykish.zevo.framework.entities.Individual;
import com.zvoykish.zevo.framework.genetics.Genotype;
import com.zvoykish.zevo.model.Evaluator;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: Zvoykish
 * Date: 3/4/14
 * Time: 22:33
 */
public class ImageApproxEvaluator extends Evaluator<CircleGene> {
    private Path imagesPath;
    private int width;
    private int height;
    private final ImageApproxExtraConfiguration extra;
    private HtmlRenderer renderer;
    private LinkedHashMap<Long, Long> cache = new LinkedHashMap<>(500, 0.8f, true);

    public ImageApproxEvaluator(EvoConfiguration configuration) {
        super(configuration);
        extra = (ImageApproxExtraConfiguration) configuration.getExtraConfiguration();
        width = extra.getWidth();
        height = extra.getHeight();

        renderer = new HtmlRenderer(extra);
        try {
            imagesPath = Paths.get("images", UUID.randomUUID().toString());
            if (!imagesPath.toFile().exists()) {
                imagesPath = Files.createDirectories(imagesPath);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
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
            Velocity.mergeTemplate("circles.vm", "utf-8", context, writer);
            writer.flush();

            Path tempFile = Files.createTempFile(imagesPath, String.valueOf(id), ".html");
            Files.write(tempFile, os.toByteArray());
            try {
                writer.close();
            }
            catch (Exception ignored) {
            }

            File pngFile = renderer.htmlToPng(tempFile.toFile());
            BufferedImage bufferedImage = ImageIO.read(pngFile);

//            Files.delete(tempFile);
//            Files.delete(Paths.get(pngFile.toURI()));
            Color[] pixels = ImageApproxUtils.getImagePixels(bufferedImage, width, height);
            Color[] expectedPixels = extra.getPixels();
            long diff = expectedPixels.length * 255;
            for (int i = 0; i < expectedPixels.length; i++) {
                Color p = pixels[i];
                Color e = expectedPixels[i];
                diff -= Math.abs(p.getRed() - e.getRed());
                diff -= Math.abs(p.getGreen() - e.getGreen());
                diff -= Math.abs(p.getBlue() - e.getBlue());
            }

            System.out.println(pngFile.getName() + ": " + diff);
            cache.put(id, diff);
            return diff;
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
