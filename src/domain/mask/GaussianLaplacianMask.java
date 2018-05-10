package domain.mask;

import domain.mask.filter.GaussianMask;

public class GaussianLaplacianMask extends GaussianMask {

    public GaussianLaplacianMask(double standardDeviation) {
        super(standardDeviation, createSize(standardDeviation), Type.GAUSSIAN_LAPLACIAN);
        this.matrix = this.createMatrix(getSize());
        this.factor = this.createFactor();
    }

    //The size is different then the original GaussianMask
    private static int createSize(double standardDeviation) {
        return (int) (2*standardDeviation + 1);
    }

    @Override
    protected double[][] createMatrix(int size) {

        double[][] matrix = new double[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                double xSquare = Math.pow(i - size / 2, 2);
                double ySquare = Math.pow(j - size / 2, 2);
                double standardDeviationSquare = Math.pow(standardDeviation, 2);
                double standardDeviationCube = Math.pow(standardDeviation, 3);


                double exp = Math.exp(-(xSquare + ySquare) / (standardDeviationSquare*2.0));
                matrix[i][j] = -(1.0 / (standardDeviationCube * Math.sqrt(2.0 * Math.PI))) * (2 - ((xSquare + ySquare)/standardDeviationSquare*2.0)) * exp;
            }
        }

        return matrix;
    }

}
