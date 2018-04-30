package domain.mask;

import domain.customimage.RGB;
import domain.customimage.CustomImage;
import domain.customimage.ChannelMatrix;

public abstract class Mask {

    public static final int AVAILABLE_SIZE = 3;

    protected final Type type;
    protected final int size;
    protected double[][] matrix;
    protected double factor;

    public Mask(Type type, int size) {
        this.type = type;
        this.size = size;
    }

    public Type getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public double getValue(int x, int y) {
        return this.matrix[x][y];
    }

    protected abstract double[][] createMatrix(int size);

    /* This is a default mask implementation it returns the convolution between an image and the concrete mask */
    public ChannelMatrix apply(CustomImage image) {
        Integer width = image.getWidth();
        Integer height = image.getHeight();
        ChannelMatrix channelMatrix = new ChannelMatrix(width, height);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                channelMatrix.setValue(x, y, applyMaskToPixel(image, x, y));
            }
        }

        return channelMatrix;
    }

    /* Basic convolution segment algorithm */
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

                    red += (255 * image.getPixelReader().getColor(i, j).getRed()) * value;
                    green += (255 * image.getPixelReader().getColor(i, j).getGreen()) * value;
                    blue += (255 * image.getPixelReader().getColor(i, j).getBlue()) * value;
                }
                //Ignoring the invalid positions, is equal to do a zero-padding. We're averaging zeros
            }
        }

        return new RGB(red, green, blue);
    }

    public enum Type {
        MEAN, WEIGHTED_MEDIAN, GAUSSIAN, PREWITT, MEDIAN, SOBEL, DERIVATE_DIRECTIONAL_OPERATOR_STANDARD, HIGH_PASS
    }
}
