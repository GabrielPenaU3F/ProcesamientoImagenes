package domain.diffusion;

public class Derivative {

    private final int value;
    private final float north;
    private final float south;
    private final float east;
    private final float west;

    public Derivative(int[][] matrix, int x, int y) {
        value = matrix[x][y];
        north = calculateNorth(matrix, x, y);
        south = calculateSouth(matrix, x, y);
        east = calculateEast(matrix, x, y);
        west = calculateWest(matrix, x, y);
    }

    public int getValue() {
        return value;
    }

    public float getNorth() {
        return north;
    }

    public float getSouth() {
        return south;
    }

    public float getEast() {
        return east;
    }

    public float getWest() {
        return west;
    }

    private float calculateNorth(int[][] matrix, int x, int y) {
        return onY(matrix, x, y, y - 1);
    }

    private float calculateSouth(int[][] matrix, int x, int y) {
        return onY(matrix, x, y, y + 1);
    }

    private float calculateWest(int[][] matrix, int x, int y) {
        return onX(matrix, x, y, x - 1);
    }

    private float calculateEast(int[][] matrix, int x, int y) {
        return onX(matrix, x, y, x + 1);
    }

    private float onX(int[][] matrix, int x, int y, int coordinate) {
        if (coordinate < 0 || coordinate >= matrix.length) {
            return 0;
        }

        return matrix[coordinate][y] - matrix[x][y];
    }

    private float onY(int[][] matrix, int x, int y, int coordinate) {
        if (coordinate < 0 || coordinate >= matrix[0].length) {
            return 0;
        }

        return matrix[x][coordinate] - matrix[x][y];
    }
}
