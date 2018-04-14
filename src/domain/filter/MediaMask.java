package domain.filter;

public class MediaMask extends Mask {

    private final double[][] matrix;
    private final double coefficient;

    public MediaMask(int size) {
        super(Type.MEDIA, size);

        coefficient = 1 / Math.pow(size, 2);

        matrix = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = coefficient;
            }
        }
    }

    public double getValue(int x, int y) {
        return coefficient;
    }
}
