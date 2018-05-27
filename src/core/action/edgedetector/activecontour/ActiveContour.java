package core.action.edgedetector.activecontour;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ActiveContour {

    private static final int OBJECT_VALUE = -3;
    private static final int BACKGROUND_VALUE = 3;
    private static final int L_IN_VALUE = -1;
    private static final int L_OUT_VALUE = 1;

    private final Integer width;
    private final Integer height;
    private final int backgroundGrayAverage;
    private final int objectGrayAverage;
    private final List<XYPoint> lOut;
    private final List<XYPoint> lIn;
    private int[][] matrix;

    public ActiveContour(Integer width, Integer height, Corners corners, int backgroundGrayAverage, int objectGrayAverage) {
        this.width = width;
        this.height = height;
        this.backgroundGrayAverage = backgroundGrayAverage;
        this.objectGrayAverage = objectGrayAverage;

        int firstRow = corners.getFirstRow();
        int secondRow = corners.getSecondRow();
        int firstColumn = corners.getFirstColumn();
        int secondColumn = corners.getSecondColumn();

        this.lOut = addPoints(firstRow, secondRow, firstColumn, secondColumn);
        this.lIn = addPoints(firstRow + 1, secondRow - 1, firstColumn + 1, secondColumn - 1);
        this.matrix = initializeMatrix(firstColumn + 2, secondColumn - 2, firstColumn + 2, secondColumn - 2);
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public List<XYPoint> getlOut() {
        return lOut;
    }

    public List<XYPoint> getlIn() {
        return lIn;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public int getBackgroundGrayAverage() {
        return backgroundGrayAverage;
    }

    public int getObjectGrayAverage() {
        return objectGrayAverage;
    }

    private List<XYPoint> addPoints(int firstRow, int secondRow, int firstColumn, int secondColumn) {
        List<XYPoint> positions = new ArrayList<>();

        for (int i = firstRow; i <= secondRow; i++) {
            positions.add(new XYPoint(i, firstColumn));
            positions.add(new XYPoint(i, secondColumn));
        }

        for (int i = firstColumn; i <= secondColumn; i++) {
            positions.add(new XYPoint(firstRow, i));
            positions.add(new XYPoint(secondRow, i));
        }

        return positions;
    }

    private int[][] initializeMatrix(int firstRowObject, int secondRowObject, int firstColumnObject, int secondColumnObject) {
        int matrix[][] = new int[height][width];

        // Fill matrix with background value
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                matrix[row][column] = BACKGROUND_VALUE;
            }
        }

        // Set edges
        lIn.forEach(xyPoint -> matrix[xyPoint.getX()][xyPoint.getY()] = L_IN_VALUE);
        lOut.forEach(xyPoint -> matrix[xyPoint.getX()][xyPoint.getY()] = L_OUT_VALUE);

        // Set object
        for (int row = firstRowObject; row <= secondRowObject; row++) {
            for (int column = firstColumnObject; column <= secondColumnObject; column++) {
                matrix[row][column] = OBJECT_VALUE;
            }
        }
        return matrix;
    }

    public void moveInvalidLInToObject() {
        for (int i = 0; i < lIn.size(); i++) {
            XYPoint xyPoint = lIn.get(i);
            if (hasAllNeighborsWithValueLowerThanZero(xyPoint)) {
                lIn.remove(xyPoint);
                matrix[xyPoint.getX()][xyPoint.getY()] = OBJECT_VALUE;
            }
        }
    }

    public void moveInvalidLOutToBackground() {
        for (int i = 0; i < lOut.size(); i++) {
            XYPoint xyPoint = lOut.get(i);
            if (hasAllNeighborsWithValueHigherThanZero(xyPoint)) {
                lOut.remove(xyPoint);
                matrix[xyPoint.getX()][xyPoint.getY()] = BACKGROUND_VALUE;
            }
        }
    }

    private boolean hasAllNeighborsWithValueLowerThanZero(XYPoint xyPoint) {
        int row = xyPoint.getX();
        int column = xyPoint.getY();
        return hasValueLowerThanZero(row - 1, column) &&
                hasValueLowerThanZero(row + 1, column) &&
                hasValueLowerThanZero(row, column - 1) &&
                hasValueLowerThanZero(row, column + 1);
    }

    private boolean hasAllNeighborsWithValueHigherThanZero(XYPoint xyPoint) {
        int row = xyPoint.getX();
        int column = xyPoint.getY();
        return hasValueHigherThanZero(row - 1, column) &&
                hasValueHigherThanZero(row + 1, column) &&
                hasValueHigherThanZero(row, column - 1) &&
                hasValueHigherThanZero(row, column + 1);
    }

    private boolean hasValueLowerThanZero(int row, int column) {
        return hasValidPosition(row, column) && this.matrix[row][column] < 0;
    }

    private boolean hasValueHigherThanZero(int row, int column) {
        return hasValidPosition(row, column) && this.matrix[row][column] > 0;
    }

    private Set<XYPoint> getNeighborsWithValue(XYPoint xyPoint, int value) {
        Set<XYPoint> neighbors = new HashSet<>();
        int row = xyPoint.getX();
        int column = xyPoint.getY();
        addNeighborIfContainsValue(row - 1, column, value, neighbors);
        addNeighborIfContainsValue(row + 1, column, value, neighbors);
        addNeighborIfContainsValue(row, column - 1, value, neighbors);
        addNeighborIfContainsValue(row, column + 1, value, neighbors);
        return neighbors;
    }

    private void addNeighborIfContainsValue(int row, int column, int value, Set<XYPoint> neighbors) {
        if (hasValidPosition(row, column) && matrix[row][column] == value) {
            neighbors.add(new XYPoint(row, column));
        }
    }

    public boolean hasValidPosition(int row, int column) {
        boolean columnIsValid = column < this.width && 0 <= column;
        boolean rowIsValid = row < this.height && 0 <= row;
        return columnIsValid && rowIsValid;
    }

    public void addLInToMatrix(XYPoint xyPoint) {
        matrix[xyPoint.getX()][xyPoint.getY()] = L_IN_VALUE;
    }

    public void addLOutToMatrix(XYPoint xyPoint) {
        matrix[xyPoint.getX()][xyPoint.getY()] = L_OUT_VALUE;
    }

    public Set<XYPoint> getBackgroundNeighbors(XYPoint xyPoint) {
        return getNeighborsWithValue(xyPoint, BACKGROUND_VALUE);
    }

    public Set<XYPoint> getObjectNeighbors(XYPoint xyPoint) {
        return getNeighborsWithValue(xyPoint, OBJECT_VALUE);
    }

    public void addLIn(List<XYPoint> toAddToLIn) {
        lIn.addAll(toAddToLIn);
    }

    public void removeLIn(List<XYPoint> toRemoveFromLIn) {
        lIn.removeAll(toRemoveFromLIn);
    }

    public void addLOut(List<XYPoint> toAddToLOut) {
        lOut.addAll(toAddToLOut);
    }

    public void removeLOut(List<XYPoint> toRemoveFromLOut) {
        lOut.removeAll(toRemoveFromLOut);
    }
}
