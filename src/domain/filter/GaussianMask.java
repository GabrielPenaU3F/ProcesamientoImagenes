package domain.filter;

public class GaussianMask extends Mask {

    private final double standardDeviation;
    private final double[][] matrix;

    public GaussianMask(double standardDeviation) {
        super(Type.GAUSSIAN, createSize(standardDeviation));

        this.standardDeviation = standardDeviation;
        this.matrix = createMatrix();
    }

    private double[][] createMatrix() {

        int sizeMask = getSize();
        double[][] matrizMask = new double[sizeMask][sizeMask];

        for (int i = 0; i < sizeMask; i++) {
            for (int j = 0; j < sizeMask; j++) {

                double xSquare = Math.pow(i - sizeMask / 2, 2);
                double ySquare = Math.pow(j - sizeMask / 2, 2);
                double standardDeviationSquare = Math.pow(standardDeviation, 2) * 2.0;

                double exp = Math.exp(-(xSquare + ySquare) / standardDeviationSquare);

                matrizMask[i][j] = (1.0 / (standardDeviationSquare * Math.PI)) * exp;
            }
        }

        return matrizMask;
    }

    @Override
    public double getValue(int x, int y) {
        return matrix[x][y];
    }

    @Override
    public double getValue(int index) {
        throw new ActionNotAvailableException(getType());
    }

    private static int createSize(double standardDeviation) {
        return (int) (2 * standardDeviation + 1);
    }
}
