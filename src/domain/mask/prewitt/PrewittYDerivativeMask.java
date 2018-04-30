package domain.mask.prewitt;

import domain.mask.Mask;

public class PrewittYDerivativeMask extends Mask {

    private static final int AVAILABLE_SIZE = 3;

    public PrewittYDerivativeMask() {
        super(Type.PREWITT, AVAILABLE_SIZE);
        this.matrix = createMatrix(AVAILABLE_SIZE);
    }

    @Override
    protected double[][] createMatrix(int size) {
        return new double[][]{
                {-1, 0, 1},
                {-1, 0, 1},
                {-1, 0, 1}
        };
    }

    @Override
    public double getValue(int x, int y) {
        return matrix[x][y];
    }
}
