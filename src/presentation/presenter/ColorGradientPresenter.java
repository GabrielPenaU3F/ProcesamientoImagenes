package presentation.presenter;

import javafx.scene.paint.CycleMethod;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;

public class ColorGradientPresenter {

    public Image createColorGradient() {

        BufferedImage colorGradient = new BufferedImage(510, 510, BufferedImage.TYPE_INT_RGB);
        WritableImage fxImage = SwingFXUtils.toFXImage(colorGradient, null);
        PixelWriter writer = fxImage.getPixelWriter();
        Color color;

        for (int i=0; i < colorGradient.getWidth(); i++) {
            for (int j=0; j < colorGradient.getHeight(); j++) {
                color = Color.rgb(255 - j/2, i/2, j/2);
                writer.setColor(i, j, color);
            }

        }

        return fxImage;

    }

}
