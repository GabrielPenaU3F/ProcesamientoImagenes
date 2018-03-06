package presentation.presenter;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;

public class GreyGradientPresenter {

    public Image createGreyGradient() {

        BufferedImage greyGradient = new BufferedImage(510, 300, BufferedImage.TYPE_INT_RGB);
        WritableImage fxImage = SwingFXUtils.toFXImage(greyGradient, null);
        PixelWriter writer = fxImage.getPixelWriter();

        int greyValue = 255;
        for (int i=0; i < greyGradient.getWidth(); i+=2) {
            Color grey = Color.rgb(greyValue, greyValue, greyValue);
            for (int j=0; j < greyGradient.getHeight(); j++) {
                writer.setColor(i,j,grey);
            }
            greyValue--;

        }
        return fxImage;

    }
}
