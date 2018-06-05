package domain.mask.sobel;

import domain.mask.Mask;

public class SobelYDerivativeMask extends Mask {

    private static final int AVAILABLE_SIZE = 3;

    public SobelYDerivativeMask() {
        super(Type.SOBEL, AVAILABLE_SIZE);
        this.matrix = createMatrix(AVAILABLE_SIZE);
    }

    @Override
    protected double[][] createMatrix(int size) {
        return new double[][]{
                {-1, 0, 1},
                {-2, 0, 2},
                {-1, 0, 1}
        };
    }

    @Override
    public double getValue(int x, int y) {
        return matrix[x][y];
    }
}
