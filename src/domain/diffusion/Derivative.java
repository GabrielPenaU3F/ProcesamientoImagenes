package domain.diffusion;

public class Derivative {

    private final int value;
    private final float north;
    private final float sud;
    private final float east;
    private final float west;

    public Derivative(int[][] matrix, int x, int y) {
        value = matrix[x][y];
        north = calculateNorth(matrix, x, y);
        sud = calculateSud(matrix, x, y);
        east = calculateEast(matrix, x, y);
        west = calculateWest(matrix, x, y);
    }

    public int getValue() {
        return value;
    }

    public float getNorth() {
        return north;
    }

    public float getSud() {
        return sud;
    }

    public float getEast() {
        return east;
    }

    public float getWest() {
        return west;
    }

    private float calculateNorth(int[][] matrix, int x, int y) {
        int coordinate = y - 1;
        return onVertical(matrix, x, y, coordinate);
    }

    private float calculateSud(int[][] matrix, int x, int y) {
        int coordinate = y + 1;
        return onVertical(matrix, x, y, coordinate);
    }

    private float calculateEast(int[][] matrix, int x, int y) {
        int coordinate = x + 1;
        return onHorizontal(matrix, x, y, coordinate);
    }

    private float calculateWest(int[][] matrix, int x, int y) {
        int coordinate = x - 1;
        return onHorizontal(matrix, x, y, coordinate);
    }

    private float onVertical(int[][] matrix, int x, int y, int coordinate) {
        int value;
        int currentValue = matrix[x][y];
        if (coordinate < y && coordinate >= 0) {
            value = matrix[x][coordinate];
        } else {
            value = currentValue;
        }

        return value - currentValue;
    }

    private float onHorizontal(int[][] matrix, int x, int y, int coordinate) {
        int value;
        int currentValue = matrix[x][y];
        if (coordinate < x && coordinate >= 0) {
            value = matrix[coordinate][y];
        } else {
            value = currentValue;
        }

        return value - currentValue;
    }
}
