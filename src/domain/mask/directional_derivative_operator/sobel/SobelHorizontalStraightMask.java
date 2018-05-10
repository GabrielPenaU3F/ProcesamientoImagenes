package domain.mask.directional_derivative_operator.sobel;

import domain.mask.Mask;

public class SobelHorizontalStraightMask extends Mask {

    public SobelHorizontalStraightMask() {
        super(Type.DERIVATE_DIRECTIONAL_OPERATOR_KIRSH, AVAILABLE_SIZE);

        this.matrix = createMatrix(AVAILABLE_SIZE);
    }

    @Override
    protected double[][] createMatrix(int size) {
        return new double[][]{
                { 1,  2,  1},
                { 0,  0,  0},
                {-1, -2, -1}
        };
    }

    @Override
    public double getValue(int x, int y) {
        return matrix[x][y];
    }
}
