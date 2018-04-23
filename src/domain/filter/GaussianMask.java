package domain.filter;

public class GaussianMask extends Mask {

    private final double standardDesviation;
    private final double[][] matrix;

    public GaussianMask(double standardDesviation) {
        super(Type.GAUSSIAN, createSize(standardDesviation));

        this.standardDesviation = standardDesviation;
        this.matrix = createMatrix();
    }

    private double[][] createMatrix() {

        int maskSize = getSize();
        double[][] maskMatrix = new double[maskSize][maskSize];

        for (int i = 0; i < maskSize; i++) {
            for (int j = 0; j < maskSize; j++) {

                double xSquare = Math.pow(i - maskSize / 2, 2);
                double ySquare = Math.pow(j - maskSize / 2, 2);
                double standardDesviationSquare = Math.pow(standardDesviation, 2) * 2.0;

                double exp = Math.exp(-(xSquare + ySquare) / standardDesviationSquare);

                maskMatrix[i][j] = (1.0 / (standardDesviationSquare * Math.PI)) * exp;
            }
        }

        return maskMatrix;
    }

    @Override
    public double getValue(int x, int y) {
        return matrix[x][y];
    }

    @Override
    public double getValue(int index) {
        throw new ActionNotAvailableException(getType());
    }

    private static int createSize(double standardDesviation) {
        return (int) (2 * standardDesviation + 1);
    }
}
