package domain.mask.filter;

import domain.customimage.CustomImage;
import domain.customimage.RGB;
import domain.mask.Mask;

public class GaussianMask extends Mask {

    protected final double standardDeviation;

    public GaussianMask(double standardDeviation) {
        super(Type.GAUSSIAN, createSize(standardDeviation));

        this.standardDeviation = standardDeviation;
        this.matrix = createMatrix(getSize());
        this.factor = createFactor();
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
}
