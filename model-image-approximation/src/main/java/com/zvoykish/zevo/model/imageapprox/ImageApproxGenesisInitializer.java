package com.zvoykish.zevo.model.imageapprox;

import com.zvoykish.zevo.framework.EvoConfiguration;
import com.zvoykish.zevo.framework.genetics.Genotype;
import com.zvoykish.zevo.framework.genetics.GenotypeImpl;
import com.zvoykish.zevo.model.GenesisInitializer;
import com.zvoykish.zevo.utils.Logger;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Zvoykish
 * Date: 3/4/14
 * Time: 22:01
 */
public class ImageApproxGenesisInitializer extends GenesisInitializer<CircleGene> {
    public static final Charset CHARSET = Charset.forName("UTF-8");
    private Iterator<Genotype<CircleGene>> iterator;
    private static final String CIRCLE_REGEX = "draw.circle\\((.*)\\).opacity\\((.*)\\).attr\\(\\{fill: '#(.*)'\\}\\).move\\((.*), (.*)\\);";
    private static final Pattern PATTERN = Pattern.compile(CIRCLE_REGEX);

    public ImageApproxGenesisInitializer(EvoConfiguration configuration) {
        super(configuration);
        ImageApproxExtraConfiguration extra = (ImageApproxExtraConfiguration) configuration.getExtraConfiguration();
        List<Genotype<CircleGene>> resumed = new ArrayList<>();
        String resumePath = extra.getResumePath();
        if (StringUtils.isNotEmpty(resumePath)) {
            resumed = resumeGeneration(resumePath);
        }

        iterator = resumed.iterator();
    }

    @Override
    public Genotype<CircleGene> getNewGenotype() {
        if (iterator.hasNext()) {
            return iterator.next();
        }

        List<CircleGene> genes = new ArrayList<>();
        ImageApproxExtraConfiguration extra = (ImageApproxExtraConfiguration) configuration.getExtraConfiguration();
        int width = extra.getWidth();
        int height = extra.getHeight();
        for (int i = 0; i < extra.getNumOfCircles(); i++) {
            int x = ((int) (Math.random() * (width + 10))) - 5;
            int y = ((int) (Math.random() * (height + 10))) - 5;
            double radius = Math.random() * (Math.max(width, height));
            double opacity = Math.min((Math.random() * 1.1), 1.0);
            genes.add(new CircleGene(x, y, radius, getRandomColor(), getRandomColor(), getRandomColor(), opacity));
        }
        return new GenotypeImpl<>(genes);
    }

    private List<Genotype<CircleGene>> resumeGeneration(String resumePath) {
        List<Genotype<CircleGene>> resumed = new ArrayList<>();
        Logger logger = Logger.getInstance();
        logger.log("Resuming earlier run, generation path: " + resumePath);
        try {
            Path path = Paths.get(resumePath);
            File[] files = path.toFile().listFiles();
            Arrays.sort(files);
            for (File file : files) {
                Path filePath = Paths.get(file.getAbsolutePath());
                List<CircleGene> genes = new ArrayList<>();
                List<String> lines = Files.readAllLines(filePath, CHARSET);
                int state = 0;
                for (String line : lines) {
                    if (line.contains("SVG('drawing')")) {
                        state = 1;
                    }
                    else if (state > 0 && line.contains("</script>")) {
                        break;
                    }
                    else {
                        if (state == 1) {
                            Matcher matcher = PATTERN.matcher(line);
                            if (matcher.find()) {
                                double x = Double.valueOf(matcher.group(4));
                                double y = Double.valueOf(matcher.group(5));
                                double radius = Double.valueOf(matcher.group(1));
                                String rgbString = matcher.group(3);
                                int r = (int) Long.parseLong(rgbString.substring(0, 2), 16);
                                int g = (int) Long.parseLong(rgbString.substring(2, 4), 16);
                                int b = (int) Long.parseLong(rgbString.substring(4, 6), 16);
                                double opacity = Double.valueOf(matcher.group(2));
                                genes.add(new CircleGene(x, y, radius, r, g, b, opacity));
                            }
                        }
                    }
                }
                resumed.add(new GenotypeImpl<>(genes));
            }

            return resumed;
        }
        catch (IOException e) {
            e.printStackTrace();
            e.printStackTrace(logger.getWriter());
            throw new RuntimeException("Failed initializing " + getClass().getName(), e);
        }
    }

    private int getRandomColor() {
        return ((int) (Math.random() * 256));
    }
}
