package com.zvoykish.zevo.model.imageapprox;

import com.zvoykish.zevo.framework.EvoExtraConfiguration;
import com.zvoykish.zevo.utils.Logger;
import org.jdom.Element;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Zvoykish
 * Date: 3/4/14
 * Time: 22:17
 */
public class ImageApproxExtraConfiguration extends EvoExtraConfiguration {
    private String svgJsLocation;
    private String phantomBin;
    private int numOfCircles;
    private CircleColor[] pixels;
    private int width;
    private int height;
    private BufferedImage image;
    private String htmlOutputPath;
    private String pngOutputPath;

    private String resumePath;

    @Override
    protected void parseModelExtraElement(Element modelExtraElement) {
        htmlOutputPath = modelExtraElement.getChildTextTrim("HtmlOutputPath");
        pngOutputPath = modelExtraElement.getChildTextTrim("PngOutputPath");
        svgJsLocation = modelExtraElement.getChildTextTrim("SVGJsLocation");
        phantomBin = modelExtraElement.getChildTextTrim("PhantomBin");
        numOfCircles = Integer.valueOf(modelExtraElement.getChildTextTrim("NumOfCircles"));
        String imageFileName = modelExtraElement.getChildTextTrim("ImageFile");
        try {
            InputStream imageStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(imageFileName);
            image = ImageIO.read(imageStream);
            width = image.getWidth();
            height = image.getHeight();
            pixels = ImageApproxUtils.getImagePixels(image);
        }
        catch (IOException e) {
            Logger logger = Logger.getInstance();
            logger.log("Failed reading image: " + imageFileName);
            e.printStackTrace();
            e.printStackTrace(logger.getWriter());
        }

        resumePath = modelExtraElement.getChildTextTrim("ResumePath");
    }

    public String getPhantomBin() {
        return phantomBin;
    }

    public int getNumOfCircles() {
        return numOfCircles;
    }

    public CircleColor[] getPixels() {
        return pixels;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getSvgJsLocation() {
        return svgJsLocation;
    }

    public String getHtmlOutputPath() {
        return htmlOutputPath;
    }

    public String getPngOutputPath() {
        return pngOutputPath;
    }

    public String getResumePath() {
        return resumePath;
    }
}
