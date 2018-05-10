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
        return (int) (4 * standardDeviation + 1);
    }

    @Override
    protected double[][] createMatrix(int size) {

        double[][] matrix = new double[size][size];

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {

                double xSquare = Math.pow(x - size / 2, 2);
                double ySquare = Math.pow(y - size / 2, 2);
                double standardDeviationSquare = Math.pow(standardDeviation, 2);
                double standardDeviationCube = Math.pow(standardDeviation, 3);

                double firstTerm = -1.0 / (standardDeviationCube * Math.sqrt(2.0 * Math.PI));
                double secondTerm = 2 - ((xSquare + ySquare) / standardDeviationSquare);
                double thirdTerm = Math.exp(-(xSquare + ySquare) / (standardDeviationSquare * 2.0));

                matrix[x][y] = firstTerm * secondTerm * thirdTerm;
            }
        }

        return matrix;
    }
}
