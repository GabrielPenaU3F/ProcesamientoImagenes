package core.action.threshold;

import core.service.ApplyThresholdService;
import core.service.MatrixService;
import domain.automaticthreshold.GlobalThresholdResult;
import domain.automaticthreshold.GlobalThresholdGroups;
import domain.customimage.CustomImage;
import domain.customimage.Pixel;
import javafx.scene.image.Image;

public class ApplyGlobalThresholdEstimationAction {

    private MatrixService matrixService;
    private ApplyThresholdService applyThresholdService;
    private int iterations;
    private int threshold;


    public ApplyGlobalThresholdEstimationAction(MatrixService matrixService, ApplyThresholdService applyThresholdService){
        this.matrixService = matrixService;
        this.applyThresholdService = applyThresholdService;
        this.iterations = 0;
        this.threshold = 0;
    }

    public GlobalThresholdResult execute(CustomImage customImage, int initialThreshold, int deltaT){
        int[][] imageMatrix = this.matrixService.toGrayMatrix(customImage.toFXImage());
        this.threshold = initialThreshold;
        int[][] transformedImage = new int[imageMatrix.length][imageMatrix[0].length];
        int currentDeltaT = 99999;
        this.iterations = 0;

        while (currentDeltaT > deltaT){
            transformedImage = this.applyThresholdService.applyThreshold(imageMatrix, threshold);
            GlobalThresholdGroups globalThresholdGroups = this.calculateGroups(transformedImage);
            int m1 = this.calculateM1(imageMatrix, globalThresholdGroups);
            int m2 = this.calculateM2(imageMatrix, globalThresholdGroups);
            int oldThreshold = threshold;
            threshold = this.updateThreshold(m1, m2);
            currentDeltaT = Math.abs(oldThreshold - threshold);
            this.iterations++;
        }

        Image image = this.matrixService.toImage(transformedImage, transformedImage, transformedImage);
        return new GlobalThresholdResult(image, this.iterations, this.threshold);
    }


    private GlobalThresholdGroups calculateGroups(int[][] image){
        int width = image.length;
        int height = image[0].length;
        GlobalThresholdGroups globalThresholdGroups = new GlobalThresholdGroups();

        for (int i=0; i < width; i++) {
            for(int j=0; j < height; j++) {
                if (image[i][j] == 0) {
                    //I don't need the color. Only the position
                    Pixel pixelG1 = new Pixel(i, j, null);
                    globalThresholdGroups.addG1Pixel(pixelG1);
                } else { //255
                    //I don't need the color. Only the position
                    Pixel pixelG2 = new Pixel(i, j, null);
                    globalThresholdGroups.addG2Pixel(pixelG2);
                }
            }
        }
        return globalThresholdGroups;
    }

    private int calculateM1(int[][] image, GlobalThresholdGroups globalThresholdGroups){
        Double m1 = 0.0;
        for (Pixel pixel : globalThresholdGroups.getG1Pixels())
            m1 += ((Double.valueOf((double)image[pixel.getX()][pixel.getY()])) / globalThresholdGroups.getG1());
        return m1.intValue();
    }

    private int calculateM2(int[][] image, GlobalThresholdGroups globalThresholdGroups){
        Double m2 = 0.0;
        for (Pixel pixel : globalThresholdGroups.getG2Pixels())
            m2 += ((Double.valueOf((double)image[pixel.getX()][pixel.getY()])) / globalThresholdGroups.getG2());
        return m2.intValue();
    }

    private int updateThreshold(int m1, int m2){
        return ((m1 + m2) / 2);
    }

}
