package domain.mask;

import domain.customimage.CustomImage;
import domain.customimage.RGB;

public class SusanMask extends Mask {

    private static final int AVAILABLE_SIZE = 7;

    public SusanMask() {
        super(Type.SUSAN, AVAILABLE_SIZE);
        this.matrix = createMatrix(AVAILABLE_SIZE);
    }

    @Override
    protected double[][] createMatrix(int size) {
        return new double[][] {
                { 0, 0, 1, 1, 1, 0, 0 },
                { 0, 1, 1, 1, 1, 1, 0 },
                { 1, 1, 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1, 1, 1 },
                { 0, 1, 1, 1, 1, 1, 0 },
                { 0, 0, 1, 1, 1, 0, 0 },
        };
    }

    public RGB applyMaskToPixel(CustomImage image, int x, int y) {
        int width = image.getWidth();
        int height = image.getHeight();
        int similarPixelsQuantity = 0;
        double coreValue = (255 * image.getPixelReader().getColor(x, y).getRed());
        double red;

        for (int j = y - (size / 2); j <= y + (size / 2); j++) {
            for (int i = x - (size / 2); i <= x + (size / 2); i++) {

                if (image.isPositionValid(width, height, i, j)) {

                    int maskColumn = j + (size / 2) - y;
                    int maskRow = i + (size / 2) - x;
                    double value = this.matrix[maskRow][maskColumn];
                    if (value != 0.0) {
                        red = (255 * image.getPixelReader().getColor(i, j).getRed()) * value;
                        similarPixelsQuantity += this.evaluateSimilarity(coreValue, red);
                    }

                }
            }
        }

        double similarity = 1 - ((double) similarPixelsQuantity / 37);
        int pixelValue = this.evaluatePixelType(similarity);
        return new RGB(pixelValue, pixelValue, pixelValue);
    }

    private int evaluateSimilarity(double coreValue, double maskValue) {
        return Math.abs(coreValue - maskValue) <= 27 ? 1 : 0;
    }

    private int evaluatePixelType(double similarity) {
        if (0 <= similarity && similarity <= 0.20) {
            return 0; //ignored
        }

        if (0.4 <= similarity && similarity <= 0.6) {
            return 150; //edge
        }

        if (0.65 <= similarity && similarity <= 0.85) {
            return 255; //corner
        }

        return 0;
    }
}
