package domain.mask.directional_derivative_operator.prewitt;

import domain.mask.Mask;

public class PrewittMainDiagonalMask extends Mask {

    public PrewittMainDiagonalMask() {
        super(Type.DERIVATE_DIRECTIONAL_OPERATOR_KIRSH, AVAILABLE_SIZE);

        this.matrix = createMatrix(AVAILABLE_SIZE);
    }

    @Override
    protected double[][] createMatrix(int size) {
        return new double[][]{
                {0, -1, -1},
                {1,  0, -1},
                {1,  1,  0}
        };
    }

    @Override
    public double getValue(int x, int y) {
        return matrix[x][y];
    }
}