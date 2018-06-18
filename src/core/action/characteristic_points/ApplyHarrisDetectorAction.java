package core.action.characteristic_points;

import java.util.ArrayList;
import java.util.List;

import core.service.MatrixService;
import domain.XYPoint;
import domain.customimage.CustomImage;
import domain.mask.filter.GaussianMask;
import domain.mask.sobel.SobelXDerivativeMask;
import domain.mask.sobel.SobelYDerivativeMask;

public class ApplyHarrisDetectorAction {

    private final MatrixService matrixService;
    private final double k = 0.04;

    public ApplyHarrisDetectorAction(MatrixService matrixService) {
        this.matrixService = matrixService;
    }

    public List<XYPoint> execute(CustomImage image, double tolerance) {

        int[][] xDeriv = new SobelXDerivativeMask().apply(image).getRedChannel();
        int[][] yDeriv = new SobelYDerivativeMask().apply(image).getRedChannel();

        double[][] xSquare = this.applyGaussianFilter(this.matrixService.calculateSquare(xDeriv));
        double[][] ySquare = this.applyGaussianFilter(this.matrixService.calculateSquare(yDeriv));
        double[][] xyCrossProduct = this.applyGaussianFilter(this.matrixService.multiplyPointToPoint(xDeriv, yDeriv));

        double[][] possibleCorners = new double[xDeriv.length][xDeriv[0].length];
        for (int i = 0; i < possibleCorners.length; i++) {
            for (int j = 0; j < possibleCorners[i].length; j++) {
                possibleCorners[i][j] = this.calculateC1(xSquare[i][j], ySquare[i][j], xyCrossProduct[i][j]);
            }
        }

        double maximum = this.matrixService.findMaxValue(possibleCorners);

        return this.filterFakeCorners(possibleCorners, maximum, tolerance);
    }

    private List<XYPoint> filterFakeCorners(double[][] possibleCorners, double maximum, double tolerance) {

        List<XYPoint> corners = new ArrayList<>();

        for (int i = 0; i < possibleCorners.length; i++) {
            for (int j = 0; j < possibleCorners[i].length; j++) {

                if (possibleCorners[i][j] >= maximum - maximum * tolerance / 100) {
                    corners.add(new XYPoint(i, j));
                }
            }
        }
        return corners;
    }

    private double calculateC1(double xDeriv, double yDeriv, double xyCrossProduct) {
        double determinant = xDeriv * yDeriv - Math.pow(xyCrossProduct, 2);
        double trace = xDeriv + yDeriv;
        return determinant - k * Math.pow(trace, 2);
    }

    /*
    private double computeR(double[][] M) {
        double determinant = this.matrixService.calculateDeterminant(M);
        double trace = this.matrixService.calculateTrace(M);
        return determinant - k*Math.pow(trace,2);
    }

    private double[][] createMMatrix(double[][] xSquare, double[][] ySquare, double[][] xyCrossProduct) {

        double[][] M = new double[xyCrossProduct.length][xyCrossProduct[0].length];

        for (int i=0; i < xyCrossProduct.length; i++) {
            for (int j=0; j < xyCrossProduct[i].length; j++) {

                M[0][0] += xSquare[i][j];
                M[0][1] += xyCrossProduct[i][j];
                M[1][0] += xyCrossProduct[i][j];
                M[1][1] += ySquare[i][j];

            }
        }
        return M;

    }*/

    private double[][] applyGaussianFilter(int[][] matrix) {
        return new GaussianMask(2).apply(matrix);
    }

}
