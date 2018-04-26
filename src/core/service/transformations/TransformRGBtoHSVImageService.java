package core.service.transformations;

import domain.customimage.CustomImage;
import domain.hsvimage.HSVImage;
import domain.hsvimage.HSVPixel;

public class TransformRGBtoHSVImageService {

    public HSVImage createHSVfromRGB(CustomImage image) {

        int width = image.getWidth();
        int height = image.getHeight();
        HSVImage hsvImage = new HSVImage(width, height);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                int r = image.getRChannelValue(i, j);
                int g = image.getGChannelValue(i, j);
                int b = image.getBChannelValue(i, j);
                hsvImage.setPixel(i, j, createHSVPixelFromRGB(r, g, b));

            }
        }

        return hsvImage;
    }

    //This transformation was based on this model: https://es.wikipedia.org/wiki/Modelo_de_color_HSV
    private HSVPixel createHSVPixelFromRGB(int redRGB, int greenRGB, int blueRGB) {

        double red = (double) redRGB / 255;
        double green = (double) greenRGB / 255;
        double blue = (double) blueRGB / 255;
        double max = Math.max(Math.max(red, green), blue);
        double min = Math.min(Math.min(red, green), blue);

        double hue = calculateHue(red, green, blue, max, min);
        double saturation = calculateSaturation(max, min);
        double value = max;

        return new HSVPixel(hue, saturation, value);
    }

    private double calculateSaturation(double max, double min) {

        double saturation = 0;
        if (max == 0) saturation = 0;
        else saturation = 1 - min / max;
        return saturation;
    }

    private double calculateHue(double red, double green, double blue, double max, double min) {

        double hue = 0;
        double diff = max - min;

        if (max == min) {
            hue = -1;
        } else {
            if (max == red && green >= blue) {
                hue = 0 + 60 * ((green - blue) / diff);
            } else if (max == red && green < blue) {
                hue = 360 + 60 * ((green - blue) / diff);
            } else if (max == green) {
                hue = 120 + 60 * ((blue - red) / diff);
            } else if (max == blue) {
                hue = 240 + 60 * ((red - green) / diff);
            }
        }
        return hue;
    }
}
