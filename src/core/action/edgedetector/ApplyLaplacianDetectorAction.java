package core.action.edgedetector;

import domain.customimage.ChannelMatrix;
import domain.customimage.CustomImage;
import domain.mask.Mask;

public class ApplyLaplacianDetectorAction {

    public CustomImage execute(CustomImage customImage, Mask mask, int slopeThershold) {
        ChannelMatrix maskResult = mask.apply(customImage);
        ChannelMatrix markZeroCrossings = this.markZeroCrossings(maskResult, slopeThershold);
        return new CustomImage(markZeroCrossings, customImage.getFormatString());
    }

    private ChannelMatrix markZeroCrossings(ChannelMatrix channelMatrix, int slopeThershold) {

        int edgedRedMatrix[][] = this.markHorizontalZeroCrossings(channelMatrix.getRedChannel(), slopeThershold);
        int edgedGreenMatrix[][] = this.markHorizontalZeroCrossings(channelMatrix.getGreenChannel(), slopeThershold);
        int edgedBlueMatrix[][] = this.markHorizontalZeroCrossings(channelMatrix.getBlueChannel(), slopeThershold);

        int resultantRedMatrix[][] = this.markVerticalZeroCrossings(channelMatrix.getRedChannel(), edgedRedMatrix, slopeThershold);
        int resultantGreenMatrix[][] = this.markVerticalZeroCrossings(channelMatrix.getGreenChannel(), edgedGreenMatrix, slopeThershold);
        int resultantBlueMatrix[][] = this.markVerticalZeroCrossings(channelMatrix.getBlueChannel(), edgedBlueMatrix, slopeThershold);

        return new ChannelMatrix(resultantRedMatrix, resultantGreenMatrix, resultantBlueMatrix);
    }

    private int[][] markHorizontalZeroCrossings(int[][] matrix, int slopeThershold) {

        int edgedMatrix[][] = new int[matrix.length][matrix[0].length];

        for (int x = 0; x < matrix.length - 1; x++) {
            for (int y = 0; y < matrix[x].length; y++) {

                if (signsChangeHorizontally(matrix, x, y)) {
                    //The slope evaluation is always made, but in the case of the standard Laplacian detector, the slope is always 0
                    if (this.evaluateHorizontalSlope(matrix, x, y, slopeThershold)) {
                        edgedMatrix[x][y] = 255;
                    }
                }
            }
        }

        return edgedMatrix;
    }

    private int[][] markVerticalZeroCrossings(int[][] matrix, int[][] horizontalZeroCrossingMatrix, int slopeThershold) {

        int[][] resultantMatrix = horizontalZeroCrossingMatrix;

        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[x].length - 1; y++) {

                if (signsChangeVertically(matrix, x, y)) {
                    //The slope evaluation is always made, but in the case of the standard Laplacian detector, the slope is always 0
                    if (this.evaluateVerticalSlope(matrix, x, y, slopeThershold)) {
                        resultantMatrix[x][y] = 255;
                    }
                }

            }
        }

        return resultantMatrix;
    }

    private boolean signsChangeHorizontally(int[][] matrix, int x, int y) {
        return matrix[x + 1][y] * matrix[x][y] < 0;
    }

    private boolean signsChangeVertically(int[][] matrix, int x, int y) {
        return matrix[x][y + 1] * matrix[x][y] < 0;
    }

    private boolean evaluateHorizontalSlope(int[][] matrix, int x, int y, int slopeThershold) {
        double a = matrix[x][y];
        double b = matrix[x + 1][y];

        return (Math.abs(a) + Math.abs(b)) >= slopeThershold;
    }

    private boolean evaluateVerticalSlope(int[][] matrix, int x, int y, int slopeThershold) {
        double a = matrix[x][y];
        double b = matrix[x][y + 1];

        return (Math.abs(a) + Math.abs(b)) >= slopeThershold;
    }

}
