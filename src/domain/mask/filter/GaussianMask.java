package domain.mask.filter;

import core.provider.ServiceProvider;
import core.service.MatrixService;
import domain.customimage.CustomImage;
import domain.customimage.RGB;
import domain.mask.Mask;

public class GaussianMask extends Mask {

    protected final double standardDeviation;
    private MatrixService matrixService;

    public GaussianMask(double standardDeviation) {
        super(Type.GAUSSIAN, createSize(standardDeviation));

        this.standardDeviation = standardDeviation;
        this.matrix = createMatrix(getSize());
        this.factor = createFactor();

        this.matrixService = ServiceProvider.provideMatrixService();
    }

    public GaussianMask(double standardDeviation, int size, Type type) {
        super(type, size);
        this.standardDeviation = standardDeviation;
    }

    private static int createSize(double standardDeviation) {
        return (int) (2 * standardDeviation + 1);
    }

    protected double createFactor() {
        double divider = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                divider += this.matrix[i][j];
            }
        }

        return 1 / divider;
    }

    @Override
    protected double[][] createMatrix(int size) {

        double[][] matrix = new double[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                double xSquare = Math.pow(i - size / 2, 2);
                double ySquare = Math.pow(j - size / 2, 2);
                double standardDeviationSquare = Math.pow(standardDeviation, 2);

                double exp = Math.exp(-(xSquare + ySquare) / (standardDeviationSquare*2.0));

                matrix[i][j] = (1.0 / (standardDeviation * Math.sqrt(2.0 * Math.PI))) * exp;
            }
        }

        return matrix;
    }

    @Override
    public RGB applyMaskToPixel(CustomImage image, int x, int y) {

        int red = 0;
        int green = 0;
        int blue = 0;

        int width = image.getWidth();
        int height = image.getHeight();

        for (int j = y - (size / 2); j <= y + (size / 2); j++) {
            for (int i = x - (size / 2); i <= x + (size / 2); i++) {

                if (image.isPositionValid(width, height, i, j)) {

                    int column = j + (size / 2) - y;
                    int row = i + (size / 2) - x;
                    double value = this.matrix[row][column];

                    red += 255 * image.getPixelReader().getColor(i, j).getRed() * value * factor;
                    green += 255 * image.getPixelReader().getColor(i, j).getGreen() * value * factor;
                    blue += 255 * image.getPixelReader().getColor(i, j).getBlue() * value * factor;
                }
            }
        }

        return new RGB(red, green, blue);
    }

    //Apply mask to a simple matrix
    public double[][] apply(double[][] targetMatrix) {

        Integer width = targetMatrix.length;
        Integer height = targetMatrix[0].length;
        double[][] newMatrix = new double[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                newMatrix[x][y] = applyMaskToPixel(targetMatrix, x, y);
            }
        }

        return newMatrix;
    }

    public double[][] apply(int[][] targetMatrix) {

        Integer width = targetMatrix.length;
        Integer height = targetMatrix[0].length;
        double[][] newMatrix = new double[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                newMatrix[x][y] = applyMaskToPixel(targetMatrix, x, y);
            }
        }

        return newMatrix;
    }

    //Basic convolution algorithm with a simple matrix
    public double applyMaskToPixel(double[][] targetMatrix, int x, int y) {

        int width = targetMatrix.length;
        int height = targetMatrix[0].length;

        int newValue = 0;

        for (int j = y - (size / 2); j <= y + (size / 2); j++) {
            for (int i = x - (size / 2); i <= x + (size / 2); i++) {

                if (this.matrixService.isPositionValid(width, height, i, j)) {

                    int column = j + (size / 2) - y;
                    int row = i + (size / 2) - x;
                    double value = this.matrix[row][column];

                    newValue += targetMatrix[i][j] * value * factor;

                }
            }
        }

        return newValue;
    }

    public double applyMaskToPixel(int[][] targetMatrix, int x, int y) {

        int width = targetMatrix.length;
        int height = targetMatrix[0].length;

        int newValue = 0;

        for (int j = y - (size / 2); j <= y + (size / 2); j++) {
            for (int i = x - (size / 2); i <= x + (size / 2); i++) {

                if (this.matrixService.isPositionValid(width, height, i, j)) {

                    int column = j + (size / 2) - y;
                    int row = i + (size / 2) - x;
                    double value = this.matrix[row][column];

                    newValue += targetMatrix[i][j] * value * factor;

                }
            }
        }

        return newValue;
    }

}
