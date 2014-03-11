package com.zvoykish.zevo.model.imageapprox;

import com.zvoykish.zevo.framework.GenerationFactory;
import com.zvoykish.zevo.utils.Logger;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Zvoykish
 * Date: 3/5/14
 * Time: 01:31
 */
public class HtmlRenderer {
    private ImageApproxExtraConfiguration configuration;
    private final LinkedHashMap<Integer, Boolean> folderExistanceCache = new LinkedHashMap<>(500, 0.8f, true);

    public HtmlRenderer(ImageApproxExtraConfiguration configuration) {
        this.configuration = configuration;
    }

    public File htmlToPng(File file, Path baseOutputPath, String genStr, String indStr) throws IOException {
        String input = file.getAbsolutePath();
        String outputPathStr = baseOutputPath.toString();
        Path path = Paths.get(outputPathStr, genStr);
        Integer generationNumber = GenerationFactory.getGenerationNumber();
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

        Path fullOutputPath = Paths.get(outputPathStr, genStr, indStr + ".png");
        File outputFile = fullOutputPath.toFile();
        String output = outputFile.getAbsolutePath();

        String options = configuration.getWidth() + "px*" + configuration.getHeight() + "px";
        String cmd = configuration.getPhantomBin() + " rasterize.js " + input + ' ' + output + " " + options;
        Process process = Runtime.getRuntime().exec(cmd);
        try {
            process.waitFor();
            if (!outputFile.exists()) {
                Logger logger = Logger.getInstance();
                logger.log("Failed finding PNG output: " + output);
                logger.log("Process output:");
                dumpStream(process.getInputStream(), logger);
                logger.log("Process error output:");
                dumpStream(process.getErrorStream(), logger);
                throw new RuntimeException("Failed creating output file: " + output);
            }
            return outputFile;
        }
        catch (InterruptedException e) {
            throw new IOException(e);
        }
    }

    private void dumpStream(InputStream inputStream, Logger logger) throws IOException {
        logger.log("---START");
        InputStreamReader isr = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(isr);
        String line = "";
        while (line != null) {
            line = br.readLine();
            logger.log(line);
        }
        logger.log("---END");
        try {
            br.close();
        }
        catch (Exception ignored) {
        }
    }
}
