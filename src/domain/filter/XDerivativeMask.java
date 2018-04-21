package domain.filter;

public class XDerivativeMask extends Mask {

    private static final int AVAILABLE_SIZE = 3;
    private final int[][] matrix;

    public XDerivativeMask() {
        super(Type.PREWITT, AVAILABLE_SIZE);

        this.matrix = createMatrix(AVAILABLE_SIZE);
    }

    private int[][] createMatrix(int size) {

        int[][] matrix = new int[size][size];

        int counter = -1;
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                matrix[i][j] = counter;
            }
            counter++;
        }

        return matrix;
    }

    @Override
    public double getValue(int x, int y) {
        return matrix[x][y];
    }

    @Override
    public double getValue(int index) {
        throw new ActionNotAvailableException(Type.PREWITT);
    }
}
