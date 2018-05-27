package domain.activecontour;

public class SelectionSquare {

    private final int firstRow;
    private final int secondRow;
    private final int firstColumn;
    private final int secondColumn;

    public SelectionSquare(int firstRow, int secondRow, int firstColumn, int secondColumn) {
        this.firstRow = firstRow;
        this.secondRow = secondRow;
        this.firstColumn = firstColumn;
        this.secondColumn = secondColumn;
    }

    public int getFirstRow() {
        return firstRow;
    }

    public int getSecondRow() {
        return secondRow;
    }

    public int getFirstColumn() {
        return firstColumn;
    }

    public int getSecondColumn() {
        return secondColumn;
    }

    public boolean isValid() {
        return firstRow > 0 && secondRow > 0 && firstColumn > 0 && secondColumn > 0;
    }
}
