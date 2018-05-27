package domain.mask.sobel;

import domain.mask.Mask;

public class SobelXDerivativeMask extends Mask {

    private static final int AVAILABLE_SIZE = 3;

    public SobelXDerivativeMask() {
        super(Type.SOBEL, AVAILABLE_SIZE);
        this.matrix = createMatrix(AVAILABLE_SIZE);
    }

    @Override
    protected double[][] createMatrix(int size) {
        return new double[][]{
                {-1, -2, -1},
                {0, 0, 0},
                {1, 2, 1}
        };
    }

    @Override
    public double getValue(int x, int y) {
        return matrix[x][y];
    }
}
