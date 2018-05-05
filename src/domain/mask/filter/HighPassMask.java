package domain.mask.filter;

import domain.customimage.CustomImage;
import domain.customimage.RGB;
import domain.mask.Mask;

public class HighPassMask extends Mask {

    public HighPassMask(int size) {
        super(Type.HIGH_PASS, size);

        this.factor = (-1) / Math.pow(size, 2);
        this.matrix = this.createMatrix(size);
    }

    @Override
    public double getValue(int x, int y) {
        return this.matrix[x][y];
    }

    @Override
    protected double[][] createMatrix(int size) {

        double matrix[][] = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = this.factor;
            }
        }
        //Since it's a square matrix, its core position is just half the size on both dimensions
        int core_position = size / 2;
        matrix[core_position][core_position] = (Math.pow(size, 2) - 1) / (Math.pow(size, 2));
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
                    int maskColumn = j + (size / 2) - y;
                    int maskRow = i + (size / 2) - x;
                    double value = this.matrix[maskRow][maskColumn];

                    red += image.getRChannelValue(i, j) * value;
                    green += image.getGChannelValue(i, j) * value;
                    blue += image.getBChannelValue(i, j) * value;
                }
            }
        }

        return new RGB(red, green, blue);
    }
}
