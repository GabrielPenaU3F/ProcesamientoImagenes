package core.service;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

public class GrayLevelStatisticsService {

    public double calculateGrayLevelMean(Image image) {

        double sampleMean;
        PixelReader reader = image.getPixelReader();
        int grayLevelsSum = 0;
        int totalPixels = 0;

        for (int i=0; i < image.getWidth(); i++) {
            for (int j=0; j < image.getHeight(); j++) {
                grayLevelsSum += (int)(reader.getColor(i,j).getRed()*255); //Since it's a gray image, any channel fits the gray level
                totalPixels++;
            }
        }

        sampleMean = grayLevelsSum / totalPixels;
        return sampleMean;
    }

    public double calculateGrayLevelVariance(Image image) {

        double sampleVariance=0;
        double sampleMean = this.calculateGrayLevelMean(image);
        PixelReader reader = image.getPixelReader();
        double squareDistanceSum = 0;
        double totalPixels = 0;

        for (int i=0; i < image.getWidth(); i++) {
            for (int j=0; j < image.getWidth(); j++) {

                double distanceToMean = ((reader.getColor(i,j).getRed()*255) - sampleMean);
                double squareDistance = Math.pow(distanceToMean, 2.0);
                squareDistanceSum += squareDistance;
                totalPixels++;

            }
        }

        sampleVariance = (squareDistanceSum / (totalPixels - 1));
        return sampleVariance;
    }

    public double calculateGrayLevelStantardDeviation(Image image) {

        double sampleVariance = this.calculateGrayLevelVariance(image);
        double sampleStandardDeviation = Math.sqrt(sampleVariance);
        return sampleStandardDeviation;

    }

}
