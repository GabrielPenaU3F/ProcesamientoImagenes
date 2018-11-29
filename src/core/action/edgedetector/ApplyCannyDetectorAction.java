package core.action.edgedetector;

import java.util.List;

import core.service.ImageOperationsService;
import core.service.MatrixService;
import domain.customimage.channel_matrix.RGBChannelMatrix;
import domain.customimage.CustomImage;
import domain.customimage.RGB;
import domain.mask.sobel.SobelXDerivativeMask;
import domain.mask.sobel.SobelYDerivativeMask;

public class ApplyCannyDetectorAction {

    private final ImageOperationsService imageOperationsService;
    private final MatrixService matrixService;

    public ApplyCannyDetectorAction(ImageOperationsService imageOperationsService, MatrixService matrixService) {
        this.imageOperationsService = imageOperationsService;
        this.matrixService = matrixService;
    }

    public CustomImage execute(CustomImage filteredImage, int t1, int t2) {

        RGBChannelMatrix sobelXDerivative = new SobelXDerivativeMask().apply(filteredImage);
        RGBChannelMatrix sobelYDerivative = new SobelYDerivativeMask().apply(filteredImage);

        int[][] gradientAngleMatrix = this.calculateGradientAngle(sobelXDerivative, sobelYDerivative);
        int[][] derivativesAbsoluteSumMatrix = this.imageOperationsService.calculateAbsoluteSum(sobelXDerivative, sobelYDerivative)
                                                                          .getRedChannel(); //Again, easily extended for 3 channels

        int[][] roughSingleEdgedMatrix = this.applyNonMaximumSuppression(derivativesAbsoluteSumMatrix, gradientAngleMatrix);

        int[][] finalEdgedMatrix = this.applyHysteresisThresholding(roughSingleEdgedMatrix, t1, t2);

        return new CustomImage(
                this.imageOperationsService.toValidImageMatrix(new RGBChannelMatrix(finalEdgedMatrix, finalEdgedMatrix, finalEdgedMatrix)),
                filteredImage.getFormatString());

    }

    private int[][] applyHysteresisThresholding(int[][] roughSingleEdgedMatrix, int t1, int t2) {

        int[][] edgedMatrix = roughSingleEdgedMatrix;

        for (int x = 0; x < edgedMatrix.length; x++) {
            for (int y = 0; y < edgedMatrix[x].length; y++) {

                if (roughSingleEdgedMatrix[x][y] > t2) {
                    edgedMatrix[x][y] = 255;
                } else if (roughSingleEdgedMatrix[x][y] < t1) {
                    edgedMatrix[x][y] = 0;
                }
            }
        }

        for (int x = 0; x < edgedMatrix.length; x++) {
            for (int y = 0; y < edgedMatrix[x].length; y++) {

                if (roughSingleEdgedMatrix[x][y] >= t1 && roughSingleEdgedMatrix[x][y] <= t2) {
                    edgedMatrix[x][y] = this.markEdgeIfItsConnectedToAnotherEdge(edgedMatrix, x, y);
                }

            }
        }
        return edgedMatrix;
    }

    private int markEdgeIfItsConnectedToAnotherEdge(int[][] roughSingleEdgedMatrix, int x, int y) {

        List<RGB> neighbors = this.matrixService.obtainNeighbors(roughSingleEdgedMatrix, x, y);

        for (RGB neighbor : neighbors) {
            if (neighbor.getRed() == 255) {
                return 255;
            }
        }
        return 0;

    }

    private int[][] applyNonMaximumSuppression(int[][] derivativesAbsoluteSumMatrix, int[][] gradientAngleMatrix) {

        int[][] roughSingleEdgedMatrix = new int[derivativesAbsoluteSumMatrix.length][derivativesAbsoluteSumMatrix[0].length];

        for (int x = 1; x < derivativesAbsoluteSumMatrix.length - 1; x++) {
            for (int y = 1; y < derivativesAbsoluteSumMatrix[x].length - 1; y++) {

                roughSingleEdgedMatrix[x][y] = this.evaluateEdge(derivativesAbsoluteSumMatrix, x, y, gradientAngleMatrix[x][y]);

            }
        }
        return roughSingleEdgedMatrix;

    }

    private int evaluateEdge(int[][] derivativesAbsoluteSumMatrix, int x, int y, int angle) {

        if (derivativesAbsoluteSumMatrix[x][y] != 0) {

            switch (angle) {

                case 0:
                    return suppressWestAndEastNotMaximums(derivativesAbsoluteSumMatrix, x, y);
                case 45:
                    return suppressNortheastAndSouthwestNotMaximums(derivativesAbsoluteSumMatrix, x, y);
                case 90:
                    return suppressNorthAndSouthNotMaximums(derivativesAbsoluteSumMatrix, x, y);
                case 135:
                    return suppressNorthwestAndSoutheastNotMaximums(derivativesAbsoluteSumMatrix, x, y);
                default:
                    return 0;
            }

        }
        return 0;

    }

    private int suppressNorthwestAndSoutheastNotMaximums(int[][] derivativesAbsoluteSumMatrix, int x, int y) {
        if ((derivativesAbsoluteSumMatrix[x][y] > derivativesAbsoluteSumMatrix[x - 1][y - 1]) && (derivativesAbsoluteSumMatrix[x][y]
                > derivativesAbsoluteSumMatrix[x + 1][y + 1])) {
            return derivativesAbsoluteSumMatrix[x][y];
        } else {
            return 0;
        }
    }

    private int suppressNorthAndSouthNotMaximums(int[][] derivativesAbsoluteSumMatrix, int x, int y) {
        if ((derivativesAbsoluteSumMatrix[x][y] > derivativesAbsoluteSumMatrix[x][y - 1]) && (derivativesAbsoluteSumMatrix[x][y]
                > derivativesAbsoluteSumMatrix[x][y + 1])) {
            return derivativesAbsoluteSumMatrix[x][y];
        } else {
            return 0;
        }
    }

    private int suppressNortheastAndSouthwestNotMaximums(int[][] derivativesAbsoluteSumMatrix, int x, int y) {
        if ((derivativesAbsoluteSumMatrix[x][y] > derivativesAbsoluteSumMatrix[x + 1][y - 1]) && (derivativesAbsoluteSumMatrix[x][y]
                > derivativesAbsoluteSumMatrix[x - 1][y + 1])) {
            return derivativesAbsoluteSumMatrix[x][y];
        } else {
            return 0;
        }
    }

    private int suppressWestAndEastNotMaximums(int[][] derivativesAbsoluteSumMatrix, int x, int y) {
        if ((derivativesAbsoluteSumMatrix[x][y] > derivativesAbsoluteSumMatrix[x - 1][y]) && (derivativesAbsoluteSumMatrix[x][y]
                > derivativesAbsoluteSumMatrix[x + 1][y])) {
            return derivativesAbsoluteSumMatrix[x][y];
        } else {
            return 0;
        }
    }

    private int[][] calculateGradientAngle(RGBChannelMatrix sobelXDerivative, RGBChannelMatrix sobelYDerivative) {

        //This can be easily extended to 3 channels

        int[][] xDerivativeRedChannel = sobelXDerivative.getRedChannel();
        int[][] yDerivativeRedChannel = sobelYDerivative.getRedChannel();

        int[][] gradientAngleMatrix = new int[sobelXDerivative.getWidth()][sobelXDerivative.getHeight()];

        for (int x = 0; x < gradientAngleMatrix.length; x++) {
            for (int y = 0; y < gradientAngleMatrix[x].length; y++) {

                if (xDerivativeRedChannel[x][y] == 0) {
                    gradientAngleMatrix[x][y] = 90;
                } else {

                    double realAngle = Math.toDegrees(Math.atan((double) yDerivativeRedChannel[x][y] / xDerivativeRedChannel[x][y]));
                    gradientAngleMatrix[x][y] = this.chooseImageAngle(realAngle);
                }

            }

        }
        return gradientAngleMatrix;

    }

    private int chooseImageAngle(double realAngle) {

        //If it's in the fourth quadrant, its value is corrected.
        realAngle = this.applyQuadrantCorrection(realAngle);

        if (realAngle < 22.5 || realAngle >= 157.5) {
            return 0;
        } else if (realAngle >= 22.5 && realAngle < 67.5) {
            return 45;
        } else if (realAngle >= 67.5 && realAngle < 112.5) {
            return 90;
        } else if (realAngle >= 112.5 && realAngle < 157.5) {
            return 135;
        } else {
            throw new RuntimeException(
                    "Unconsistent angle"); //This should never happen, since realAngle is contained within the first or second quadrant.
        }

    }

    private double applyQuadrantCorrection(double realAngle) {
        if (realAngle < 0) {
            realAngle += 180;
        }
        return realAngle;
    }

}
