package core.action.filter.service;

import domain.customimage.CustomImage;
import domain.filter.Mask;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class MaskService {

    public void applyMediaMask(CustomImage image, WritableImage filteredImage, Mask mask, int x, int y) {

        double red = 0;
        double green = 0;
        double blue = 0;

        int maskSize = mask.getSize();
        int width = image.getWidth();
        int height = image.getHeight();

        double maskValue = mask.getValue(0, 0);

        for (int j = y - (maskSize / 2); j <= y + (maskSize / 2); j++) {
            for (int i = x - (maskSize / 2); i <= x + (maskSize / 2); i++) {

                if (isValidPosition(width, height, i, j)) {
                    Color color = image.getPixelReader().getColor(i, j);

                    red += 255 * color.getRed() * maskValue;
                    green += 255 * color.getGreen() * maskValue;
                    blue += 255 * color.getBlue() * maskValue;
                }
            }
        }

        Color color = Color.rgb((int) red, (int) green, (int) blue);
        filteredImage.getPixelWriter().setColor(x, y, color);
    }

    private boolean isValidPosition(int width, int height, int i, int j) {
        // Ignore portion of the mask is outside the image.
        return j >= 0 && j < height && i >= 0 && i < width;
    }

}
