package domain.mask.filter;

import domain.mask.Mask;

public class MeanMask extends Mask {

    public MeanMask(int size) {
        super(Type.MEAN, size);

        this.factor = 1 / Math.pow(size, 2);
        this.matrix = createMatrix(size);
    }

    @Override
    protected double[][] createMatrix(int size) {

        double[][] matrix = new double[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = this.factor;
            }
        }

        return matrix;
    }


}
