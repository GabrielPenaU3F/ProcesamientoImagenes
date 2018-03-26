package core.service;

import domain.customimage.CustomImage;

public class HistogramService {

    public double[] create(CustomImage customImage) {

        int totalPixels = 0;
        double[] arrayHistogram = new double[257];

        for (int i = 0; i < customImage.getWidth(); i++) {
            for (int j = 0; j < customImage.getHeight(); j++) {
                arrayHistogram[customImage.getAverageValue(i, j)] += 1;
                totalPixels++;
            }
        }

        for (int i = 0; i < 256; i++) {
            arrayHistogram[i] = arrayHistogram[i] / totalPixels;
        }

        return arrayHistogram;
    }
}
