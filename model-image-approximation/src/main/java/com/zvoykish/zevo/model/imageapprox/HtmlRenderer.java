package com.zvoykish.zevo.model.imageapprox;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Zvoykish
 * Date: 3/5/14
 * Time: 01:31
 */
public class HtmlRenderer {
    private ImageApproxExtraConfiguration configuration;

    public HtmlRenderer(ImageApproxExtraConfiguration configuration) {
        this.configuration = configuration;
    }

    public File htmlToPng(File file) throws IOException {
        String input = file.getAbsolutePath();
        String output = input + ".png";
        String options = configuration.getWidth() + "px*" + configuration.getHeight() + "px";
        String cmd = configuration.getPhantomBin() + " rasterize.js " + input + ' ' + output + " " + options;
        Process process = Runtime.getRuntime().exec(cmd);
        try {
            process.waitFor();
            File result = new File(output);
            if (!result.exists()) {
                throw new RuntimeException("Failed creating output file: " + output);
            }
            return result;
        }
        catch (InterruptedException e) {
            throw new IOException(e);
        }
    }
}
