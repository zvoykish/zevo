package com.zvoykish.zevo.model.imageapprox;

import com.zvoykish.zevo.framework.EvoExtraConfiguration;
import com.zvoykish.zevo.utils.Logger;
import org.apache.velocity.app.Velocity;
import org.jdom.Element;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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
    private Color[] pixels;
    private int width;
    private int height;
    private BufferedImage image;

    @Override
    protected void parseModelExtraElement(Element modelExtraElement) {
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
            Logger.getInstance().log("Failed reading image: " + imageFileName);
            e.printStackTrace();
        }

        Properties p = new Properties();
        p.put("resource.loader", "class");
        p.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(p);
    }

    public String getPhantomBin() {
        return phantomBin;
    }

    public int getNumOfCircles() {
        return numOfCircles;
    }

    public Color[] getPixels() {
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
}
