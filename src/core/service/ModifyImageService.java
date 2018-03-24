package core.service;

import domain.customimage.CustomImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ModifyImageService {

    public CustomImage modify(CustomImage image, Integer pixelX, Integer pixelY, Integer value) {

        int width = image.getWidth();
        int height = image.getHeight();

        PixelReader pixelReader = image.getPixelReader();

        WritableImage writableImage = new WritableImage(width, height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color argb = pixelReader.getColor(x, y);
                pixelWriter.setColor(x, y, argb);
            }
        }

        Color modifiedColor = Color.rgb(value,value,value); //For gray images
        pixelWriter.setColor(pixelX,pixelY,modifiedColor);
        return new CustomImage(SwingFXUtils.fromFXImage(writableImage, null), image.getFormatString());
    }

}
