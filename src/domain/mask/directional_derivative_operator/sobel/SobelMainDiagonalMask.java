package domain.mask.directional_derivative_operator.sobel;

import domain.mask.Mask;

public class SobelMainDiagonalMask extends Mask {

    public SobelMainDiagonalMask() {
        super(Type.DERIVATE_DIRECTIONAL_OPERATOR_KIRSH, AVAILABLE_SIZE);

        this.matrix = createMatrix(AVAILABLE_SIZE);
    }

    @Override
    protected double[][] createMatrix(int size) {
        return new double[][]{
                {0, -1, -2},
                {1,  0, -1},
                {2,  1,  0}
        };
    }

    @Override
    public double getValue(int x, int y) {
        return matrix[x][y];
    }
}