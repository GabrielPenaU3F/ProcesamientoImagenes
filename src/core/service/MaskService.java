package core.service;

import domain.customimage.CustomImage;
import domain.filter.Mask;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class MaskService {

    //Here, x and y are the coordinates of the point where the mask's center is currently standing
    public void applyMeanMask(CustomImage image, WritableImage filteredImage, Mask mask, int x, int y) {

        double red = 0;
        double green = 0;
        double blue = 0;

        int sizeMask = mask.getSize();
        int width = image.getWidth();
        int height = image.getHeight();

        double value = mask.getValue(0, 0);

        for (int j = y - (sizeMask / 2); j <= y + (sizeMask / 2); j++) {
            for (int i = x - (sizeMask / 2); i <= x + (sizeMask / 2); i++) {

                if (isPositionValid(width, height, i, j)) {
                    Color color = image.getPixelReader().getColor(i, j);

                    red += 255 * color.getRed() * value;
                    green += 255 * color.getGreen() * value;
                    blue += 255 * color.getBlue() * value;
                }
                //Ignoring the invalid positions, is equal to do a zero-padding. We're averaging zeros
            }
        }

        Color color = Color.rgb((int) red, (int) green, (int) blue);
        filteredImage.getPixelWriter().setColor(x, y, color);
    }

    public void applyMedianMask(CustomImage image, WritableImage filteredImage, Mask mask, int x, int y) {

        int sizeMask = mask.getSize();
        int width = image.getWidth();
        int height = image.getHeight();

        List<Double> pixelsInMask = new ArrayList<>();

        int index = 0;
        for (int j = y - (sizeMask / 2); j <= y + (sizeMask / 2); j++) {
            for (int i = x - (sizeMask / 2); i <= x + (sizeMask / 2); i++) {

                if (isPositionValid(width, height, i, j)) {
                    Color color = image.getPixelReader().getColor(i, j);
                    // apply weights
                    for (int n = 0; n < mask.getValue(index); n++) {
                        pixelsInMask.add(255 * color.getRed());
                    }
                }
                index++;
            }
        }

        pixelsInMask.sort(Double::compareTo);

        int medianSize = pixelsInMask.size() / 2;
        double value = pixelsInMask.get(medianSize);

        if (medianSize % 2 == 0) {
            value = (pixelsInMask.get(medianSize - 1) + pixelsInMask.get(medianSize)) / 2;
        }

        Color color = Color.rgb((int) value, (int) value, (int) value);
        filteredImage.getPixelWriter().setColor(x, y, color);
    }

    public void applyGaussianMask(CustomImage image, WritableImage filteredImage, Mask mask, int x, int y) {

        int sizeMask = mask.getSize();
        int width = image.getWidth();
        int height = image.getHeight();

        double red = 0;
        double green = 0;
        double blue = 0;

        double divider = 0;
        for (int i = 0; i < sizeMask; i++) {
            for (int j = 0; j < sizeMask; j++) {
                divider += mask.getValue(i, j);
            }
        }

        for (int j = y - (sizeMask / 2); j <= y + (sizeMask / 2); j++) {
            for (int i = x - (sizeMask / 2); i <= x + (sizeMask / 2); i++) {

                int column = j + (sizeMask / 2) - y;
                int row = i + (sizeMask / 2) - x;
                double value = mask.getValue(column, row);

                if (isPositionValid(width, height, i, j)) {
                    Color color = image.getPixelReader().getColor(i, j);

                    red += 255 * color.getRed() * value / divider;
                    green += 255 * color.getGreen() * value / divider;
                    blue += 255 * color.getBlue() * value / divider;
                }
            }
        }

        Color color = Color.rgb((int) red, (int) green, (int) blue);
        filteredImage.getPixelWriter().setColor(x, y, color);
    }

    public void applyHighPassMask(CustomImage image, int[][] filteredImageMatrix, Mask mask, int x, int y) {

        int newGray = 0;

        int maskSize = mask.getSize();
        int width = image.getWidth();
        int height = image.getHeight();

        double maskValue = mask.getValue(0, 0);

        for (int j = y - (maskSize / 2); j <= y + (maskSize / 2); j++) {
            for (int i = x - (maskSize / 2); i <= x + (maskSize / 2); i++) {

                if (isPositionValid(width, height, i, j)) {
                    int column = j + (maskSize/2) - y;
                    int row = i + (maskSize/2) - x;
                    maskValue = mask.getValue(row,column);
                    int gray = image.getAverageValue(i,j);
                    newGray += gray * maskValue;
                }
            }
        }
        filteredImageMatrix[x][y] = newGray;
    }

    private boolean isPositionValid(int width, int height, int i, int j) {
        // Ignore the portion of the mask outside the image.
        return j >= 0 && j < height && i >= 0 && i < width;
    }
}
