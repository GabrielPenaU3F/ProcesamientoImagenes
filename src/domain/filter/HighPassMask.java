package domain.filter;

public class HighPassMask extends Mask {

    private final double[][] matrix;

    public HighPassMask(int size) {
        super(Type.HIGH_PASS, size);

        this.matrix = new double[size][size];
        double coefficient = (-1) / Math.pow(size, 2);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = coefficient;
            }
        }
        //la posicion de la fila y columna del nucleo es la misma, porque es cuadrado
        int core_position = size / 2;
        this.matrix[core_position][core_position] = (Math.pow(size, 2) - 1) / (Math.pow(size, 2));
    }

    @Override
    public double getValue(int x, int y) {
        return this.matrix[x][y];
    }

    @Override
    public double getValue(int index) {
        throw new ActionNotAvailableException(getType());
    }
}
