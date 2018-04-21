package domain.filter;

public class GuassianMask extends Mask {

    private final double standardDesviation;
    private final double[][] matrix;

    public GuassianMask(double standardDesviation) {
        super(Type.GAUSSIAN, createSize(standardDesviation));

        this.standardDesviation = standardDesviation;
        this.matrix = createMatrix();
    }

    private double[][] createMatrix() {

        int sizeMask = getSize();
        double[][] matrizMask = new double[sizeMask][sizeMask];

        for (int i = 0; i < sizeMask; i++) {
            for (int j = 0; j < sizeMask; j++) {

                double xSquare = Math.pow(i - sizeMask / 2, 2);
                double ySquare = Math.pow(j - sizeMask / 2, 2);
                double standardDesviationSquare = Math.pow(standardDesviation, 2) * 2.0;

                double exp = Math.exp(-(xSquare + ySquare) / standardDesviationSquare);

                matrizMask[i][j] = (1.0 / (standardDesviationSquare * Math.PI)) * exp;
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

    private static int createSize(double standardDesviation) {
        return (int) (2 * standardDesviation + 1);
    }
}
