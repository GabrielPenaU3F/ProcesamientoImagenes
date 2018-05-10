package domain.mask.directional_derivative_operator.standard;

import domain.mask.Mask;

public class StandardSecondaryDiagonalMask extends Mask {

    public StandardSecondaryDiagonalMask() {
        super(Type.DERIVATE_DIRECTIONAL_OPERATOR_STANDARD, AVAILABLE_SIZE);

        this.matrix = createMatrix(AVAILABLE_SIZE);
    }

    @Override
    protected double[][] createMatrix(int size) {
        return new double[][]{
                {1,  1,  1},
                {1, -2, -1},
                {1, -1, -1}
        };
    }

    @Override
    public double getValue(int x, int y) {
        return matrix[x][y];
    }
}