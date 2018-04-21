package core.service.statistics;

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

        sampleMean = (double)grayLevelsSum / totalPixels;
        return sampleMean;
    }

    public double calculateGrayLevelVariance(Image image) {

        double sampleVariance=0;
        double sampleMean = this.calculateGrayLevelMean(image);
        PixelReader reader = image.getPixelReader();
        double squareDistanceSum = 0;
        int totalPixels = 0;

        for (int i=0; i < image.getWidth(); i++) {
            for (int j=0; j < image.getHeight(); j++) {

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

    public int calculateMaxGrayLevel(Image image) {

        PixelReader reader = image.getPixelReader();
        int max = 0;
        for (int i=0; i < image.getWidth(); i++) {
            for (int j=0; j < image.getHeight(); j++) {

                int grayLevel = (int)(reader.getColor(i,j).getRed()*255);
                if (grayLevel > max) {
                    max = grayLevel;
                }

            }
        }
        return max;

    }

    public int calculateMinGrayLevel(Image image) {

        PixelReader reader = image.getPixelReader();
        int min = 255;
        for (int i=0; i < image.getWidth(); i++) {
            for (int j=0; j < image.getHeight(); j++) {

                int grayLevel = (int)(reader.getColor(i,j).getRed()*255);
                if (grayLevel < min) {
                    min = grayLevel;
                }

            }
        }
        return min;
    }

    public int calculateMinGrayLevel(int[][] channelValues){
        int actualMin = 0;
        for (int i = 0; i < channelValues.length; i++){
            for (int j = 0; j < channelValues[i].length; j++){
                if(channelValues[i][j] < actualMin){
                    actualMin = channelValues[i][j];
                }
            }
        }
        return actualMin;
    }

    public int calculateMaxGrayLevel(int[][] channel) {
        int max = 0;
        for (int i=0; i < channel.length; i++) {
            for (int j=0; j < channel[i].length; j++) {
                int grayLevel = channel[i][j];
                if (grayLevel > max) {
                    max = grayLevel;
                }
            }
        }
        return max;
    }

}
