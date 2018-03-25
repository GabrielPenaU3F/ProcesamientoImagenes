package core.service;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;

public class ImageGradientService {

    public Image createGreyGradient() {
        BufferedImage greyGradient = new BufferedImage(510, 510, BufferedImage.TYPE_INT_RGB);
        WritableImage fxImage = SwingFXUtils.toFXImage(greyGradient, null);
        PixelWriter writer = fxImage.getPixelWriter();

        int greyValue = 255;
        for (int i = 0; i < greyGradient.getWidth(); i++) {
            for (int j = 0; j < greyGradient.getHeight(); j++) {
                writer.setColor(i, j, Color.rgb(greyValue, greyValue, greyValue));
            }
            if (i%2 == 0) greyValue--; //When i is even, I decrement the greyValue; this way, every grey band has a 2-pixel width
        }
        return fxImage;
    }

    public Image createColorGradient() {
        BufferedImage colorGradient = new BufferedImage(510, 510, BufferedImage.TYPE_INT_RGB);
        WritableImage fxImage = SwingFXUtils.toFXImage(colorGradient, null);
        PixelWriter writer = fxImage.getPixelWriter();

        for (int i = 0; i < colorGradient.getWidth(); i++) {
            for (int j = 0; j < colorGradient.getHeight(); j++) {
                writer.setColor(i, j, Color.rgb(255 - j / 2, i / 2, j / 2));
            }
        }
        return fxImage;
    }
}
