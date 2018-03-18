package core.action.image;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import domain.ImageInform;

public class CreateImageInformAction {

    public ImageInform execute(Image image) {

        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        Double red = 0.0;
        Double blue = 0.0;
        Double green = 0.0;
        Double gray = 0.0;
        Long totalPixel = 0L;

        PixelReader reader = image.getPixelReader();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = reader.getColor(x, y);
                red += (color.getRed());
                green += (color.getGreen());
                blue += (color.getBlue());
                gray += ((color.getRed() + color.getGreen() + color.getBlue()) / 3);
                totalPixel++;
            }
        }

        Double avgRed = red / totalPixel * 255;
        Double avgGreen = green / totalPixel * 255;
        Double avgBlue = blue / totalPixel * 255;
        Double avgGrey = gray / totalPixel * 255;

        return new ImageInform(avgRed, avgGreen, avgBlue, avgGrey, totalPixel);
    }
}
