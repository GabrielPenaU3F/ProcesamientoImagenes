package core.action.edgedetector;

import domain.customimage.ChannelMatrix;
import domain.customimage.CustomImage;
import domain.flags.LaplacianDetector;
import domain.mask.LaplacianMask;
import domain.mask.Mask;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import presentation.util.InsertValuePopup;

import java.util.Optional;

public class ApplyLaplacianDetectorAction {

    public CustomImage execute(CustomImage customImage, LaplacianDetector detector) {

        Mask mask = new LaplacianMask();
        ChannelMatrix laplacianMaskResult = mask.apply(customImage);
        return new CustomImage(this.markZeroCrossings(laplacianMaskResult, detector), customImage.getFormatString());

    }


    private ChannelMatrix markZeroCrossings(ChannelMatrix channelMatrix, LaplacianDetector detector) {

        int width = channelMatrix.getWidth();
        int height = channelMatrix.getHeight();

        int edgedRedMatrix[][] = new int[width][height];
        int edgedGreenMatrix[][] = new int[width][height];
        int edgedBlueMatrix[][] = new int[width][height];

        int slopeThershold = 0;
        if (detector.equals(LaplacianDetector.WITH_SLOPE_EVALUATION)) {
            slopeThershold = Integer.parseInt(InsertValuePopup.show("Insert slope thershold", "0").get());
        }

        //I must pase the new matrix via parameter because I need to mark the horizontal and vertical edges on the same matrix
        this.markHorizontalZeroCrossings(channelMatrix.getRedChannel(), edgedRedMatrix, detector, slopeThershold);
        this.markHorizontalZeroCrossings(channelMatrix.getGreenChannel(), edgedGreenMatrix, detector, slopeThershold);
        this.markHorizontalZeroCrossings(channelMatrix.getBlueChannel(), edgedBlueMatrix, detector, slopeThershold);

        this.markVerticalZeroCrossings(channelMatrix.getRedChannel(), edgedRedMatrix, detector, slopeThershold);
        this.markVerticalZeroCrossings(channelMatrix.getGreenChannel(), edgedGreenMatrix, detector, slopeThershold);
        this.markVerticalZeroCrossings(channelMatrix.getBlueChannel(), edgedBlueMatrix, detector, slopeThershold);

        return new ChannelMatrix(edgedRedMatrix, edgedGreenMatrix, edgedBlueMatrix);

    }

    private void markHorizontalZeroCrossings(int[][] matrix, int[][] newMatrix, LaplacianDetector detector, int slopeThershold) {

        for (int x=0; x < matrix.length -1; x++){
            for (int y=0; y < matrix[x].length; y++) {

                if (signsChangeHorizontally(matrix, x, y)) {
                    //The slope evaluation is always made, but in the case of the standard Laplacian detector, the slope is always 0
                    if(this.evaluateHorizontalSlope(matrix, x, y, slopeThershold)) newMatrix[x][y] = 255;
                }

            }
        }

    }

    private void markVerticalZeroCrossings(int[][] matrix, int[][] newMatrix, LaplacianDetector detector, int slopeThershold) {

        for (int x=0; x < matrix.length; x++){
            for (int y=0; y < matrix[x].length -1; y++) {

                if (signsChangeVertically(matrix, x, y)) {
                    //The slope evaluation is always made, but in the case of the standard Laplacian detector, the slope is always 0
                    if(this.evaluateVerticalSlope(matrix, x, y, slopeThershold)) newMatrix[x][y] = 255;
                }

            }
        }

    }

    private boolean signsChangeHorizontally(int[][] matrix, int x, int y) {
        return matrix[x+1][y]*matrix[x][y] < 0;
    }

    private boolean signsChangeVertically(int[][] matrix, int x, int y) {
        return matrix[x][y+1]*matrix[x][y] < 0;
    }

    private boolean evaluateHorizontalSlope(int[][] matrix, int x, int y, int slopeThershold) {

        double a = matrix[x][y];
        double b = matrix[x+1][y];

        return (Math.abs(a) + Math.abs(b)) >= slopeThershold;

    }

    private boolean evaluateVerticalSlope(int[][] matrix, int x, int y, int slopeThershold) {

        double a = matrix[x][y];
        double b = matrix[x][y+1];

        return (Math.abs(a) + Math.abs(b)) >= slopeThershold;

    }

}
