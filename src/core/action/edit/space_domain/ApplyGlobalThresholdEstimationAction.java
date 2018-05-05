package core.action.edit.space_domain;

import core.service.MatrixService;
import domain.automaticthreshold.GlobalThresholdImage;
import domain.automaticthreshold.ThresholdGroups;
import domain.customimage.CustomImage;
import domain.customimage.Pixel;
import javafx.scene.image.Image;



public class ApplyGlobalThresholdEstimationAction {

    private MatrixService matrixService;
    private int iterations;
    private int threshold;


    public ApplyGlobalThresholdEstimationAction(MatrixService matrixService){
        this.matrixService = matrixService;
        this.iterations = 0;
        this.threshold = 0;
    }

    public GlobalThresholdImage execute(CustomImage customImage, int initialThreshold, int deltaT){
        int[][] imageMatrix = this.matrixService.toGrayMatrix(customImage.toFXImage());
        this.threshold = initialThreshold;
        int[][] transformedImage = new int[imageMatrix.length][imageMatrix[0].length];
        int currentDeltaT = 99999;
        this.iterations = 0;

        while (currentDeltaT > deltaT){
            transformedImage = this.applyThreshold(imageMatrix, threshold);
            ThresholdGroups thresholdGroups = this.calculateGroups(transformedImage);
            int m1 = this.calculateM1(imageMatrix, thresholdGroups);
            int m2 = this.calculateM2(imageMatrix, thresholdGroups);
            int oldThreshold = threshold;
            threshold = this.updateThreshold(m1, m2);
            currentDeltaT = Math.abs(oldThreshold - threshold);
            this.iterations++;
        }
        GlobalThresholdImage globalThresholdImage = new GlobalThresholdImage();
        globalThresholdImage.setImage(this.matrixService.toImage(transformedImage, transformedImage, transformedImage));
        globalThresholdImage.setIterations(this.iterations);
        globalThresholdImage.setThreshold(this.threshold);
        return globalThresholdImage;
    }

    public int getIterations(){
        return this.iterations;
    }

    public int getThreshold(){
        return this.threshold;
    }

    private int[][] applyThreshold(int[][] image, int threshold){
        int width = image.length;
        int height = image[0].length;
        int[][] transformedMatrix = new int[width][height];

        for (int i=0; i < width; i++) {
            for(int j=0; j < height; j++) {
                if (image[i][j] >= threshold) {
                    transformedMatrix[i][j] = 255;
                } else {
                    transformedMatrix[i][j] = 0;
                }
            }
        }
        return transformedMatrix;
    }


    private ThresholdGroups calculateGroups(int[][] image){
        int width = image.length;
        int height = image[0].length;
        ThresholdGroups thresholdGroups = new ThresholdGroups();

        for (int i=0; i < width; i++) {
            for(int j=0; j < height; j++) {
                if (image[i][j] == 0) {
                    //I don't need the color. Only the position
                    Pixel pixelG1 = new Pixel(i, j, null);
                    thresholdGroups.addG1Pixel(pixelG1);
                } else { //255
                    //I don't need the color. Only the position
                    Pixel pixelG2 = new Pixel(i, j, null);
                    thresholdGroups.addG2Pixel(pixelG2);
                }
            }
        }
        return thresholdGroups;
    }

    private int calculateM1(int[][] image, ThresholdGroups thresholdGroups){
        Double m1 = 0.0;
        for (Pixel pixel : thresholdGroups.getG1Pixels())
            m1 += ((Double.valueOf((double)image[pixel.getX()][pixel.getY()])) / thresholdGroups.getG1());
        return m1.intValue();
    }

    private int calculateM2(int[][] image, ThresholdGroups thresholdGroups){
        Double m2 = 0.0;
        for (Pixel pixel : thresholdGroups.getG2Pixels())
            m2 += ((Double.valueOf((double)image[pixel.getX()][pixel.getY()])) / thresholdGroups.getG2());
        return m2.intValue();
    }

    private int updateThreshold(int m1, int m2){
        return ((m1 + m2) / 2);
    }

}
