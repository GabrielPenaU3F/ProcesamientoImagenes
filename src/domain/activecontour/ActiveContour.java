package domain.activecontour;

import domain.XYPoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

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
    private double[][] content;

    public ActiveContour(Integer width, Integer height, SelectionSquare selectionSquare, int backgroundGrayAverage, int objectGrayAverage) {
        this.width = width;
        this.height = height;
        this.backgroundGrayAverage = backgroundGrayAverage;
        this.objectGrayAverage = objectGrayAverage;

        int firstRow = selectionSquare.getFirstRow();
        int secondRow = selectionSquare.getSecondRow();
        int firstColumn = selectionSquare.getFirstColumn();
        int secondColumn = selectionSquare.getSecondColumn();

        this.lOut = addPoints(firstRow, secondRow, firstColumn, secondColumn);
        this.lIn = addPoints(firstRow + 1, secondRow - 1, firstColumn + 1, secondColumn - 1);
        this.content = initializeContent(firstRow + 2, secondRow - 2, firstColumn + 2, secondColumn - 2);
    }

    private ActiveContour(Integer width, Integer height, int backgroundGrayAverage, int objectGrayAverage,
            List<XYPoint> lOut, List<XYPoint> lIn, double[][] content) {
        this.width = width;
        this.height = height;
        this.backgroundGrayAverage = backgroundGrayAverage;
        this.objectGrayAverage = objectGrayAverage;
        this.lOut = lOut;
        this.lIn = lIn;
        this.content = content;
    }

    public static ActiveContour copy(ActiveContour activeContour) {
        return new ActiveContour(
                activeContour.getWidth(),
                activeContour.getHeight(),
                activeContour.getBackgroundGrayAverage(),
                activeContour.getObjectGrayAverage(),
                new ArrayList<>(activeContour.getlOut()),
                new ArrayList<>(activeContour.getlIn()),
                Arrays.copyOf(activeContour.getContent(), activeContour.getContent().length));
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

    public int getBackgroundGrayAverage() {
        return backgroundGrayAverage;
    }

    public int getObjectGrayAverage() {
        return objectGrayAverage;
    }

    private List<XYPoint> addPoints(int firstRow, int secondRow, int firstColumn, int secondColumn) {
        List<XYPoint> positions = new CopyOnWriteArrayList<>();

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

    private double[][] initializeContent(int firstRowObject, int secondRowObject, int firstColumnObject, int secondColumnObject) {
        double matrix[][] = new double[width][height];

        // Fill matrix with background value
        for (int row = 0; row < width; row++) {
            for (int column = 0; column < height; column++) {
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
                content[xyPoint.getX()][xyPoint.getY()] = OBJECT_VALUE;
            }
        }
    }

    public void moveInvalidLOutToBackground() {
        for (int i = 0; i < lOut.size(); i++) {
            XYPoint xyPoint = lOut.get(i);
            if (hasAllNeighborsWithValueHigherThanZero(xyPoint)) {
                lOut.remove(xyPoint);
                content[xyPoint.getX()][xyPoint.getY()] = BACKGROUND_VALUE;
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
        return hasValidPosition(row, column) && content[row][column] < 0;
    }

    private boolean hasValueHigherThanZero(int row, int column) {
        return hasValidPosition(row, column) && content[row][column] > 0;
    }

    public Set<XYPoint> getNeighbors(XYPoint xyPoint) {
        Set<XYPoint> neighbors = new HashSet<>();
        int row = xyPoint.getX();
        int column = xyPoint.getY();
        neighbors.add(new XYPoint(row - 1, column));
        neighbors.add(new XYPoint(row + 1, column));
        neighbors.add(new XYPoint(row, column - 1));
        neighbors.add(new XYPoint(row, column + 1));
        return neighbors;
    }

    public boolean hasValidPosition(int row, int column) {
        boolean rowIsValid = row < this.width && 0 <= row;
        boolean columnIsValid = column < this.height && 0 <= column;
        return rowIsValid && columnIsValid;
    }

    //fi(x) = 3
    public boolean belongToBackground(XYPoint xyPoint) {
        return hasValue(xyPoint, BACKGROUND_VALUE);
    }

    //fi(x) = -3
    public boolean belongToObject(XYPoint xyPoint) {
        return hasValue(xyPoint, OBJECT_VALUE);
    }

    private boolean hasValue(XYPoint xyPoint, int value) {
        return content[xyPoint.getX()][xyPoint.getY()] == value;
    }

    //Set fi(x) = -1
    public void updateFiValueForLInPoint(XYPoint xyPoint) {
        content[xyPoint.getX()][xyPoint.getY()] = L_IN_VALUE;
    }

    //Set fi(x) = 1
    public void updateFiValueForLOutPoint(XYPoint xyPoint) {
        content[xyPoint.getX()][xyPoint.getY()] = L_OUT_VALUE;
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

    public double[][] getContent() {
        return content;
    }

    public void setFiFunction(double[][] fiFunction) {
        this.content = fiFunction;
    }

    public void setFi(int x, int y, double newFiValue) {
        this.content[x][y] = newFiValue;
    }
}
