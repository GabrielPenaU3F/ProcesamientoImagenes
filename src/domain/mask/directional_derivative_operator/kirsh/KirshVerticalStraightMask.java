package domain.mask.directional_derivative_operator.kirsh;

import domain.mask.Mask;

public class KirshVerticalStraightMask extends Mask {

    public KirshVerticalStraightMask() {
        super(Type.DERIVATE_DIRECTIONAL_OPERATOR_KIRSH, AVAILABLE_SIZE);

        this.matrix = createMatrix(AVAILABLE_SIZE);
    }

    @Override
    protected double[][] createMatrix(int size) {
        return new double[][]{
                {5, -3, -3},
                {5,  0, -3},
                {5, -3, -3}
        };
    }

    @Override
    public double getValue(int x, int y) {
        return matrix[x][y];
    }
}