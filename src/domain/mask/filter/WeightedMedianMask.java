package domain.mask.filter;

public class WeightedMedianMask extends MedianMask {

    private static final int AVAILABLE_SIZE = 3;

    public WeightedMedianMask() {
        super(AVAILABLE_SIZE);
        this.matrix = createMatrix(AVAILABLE_SIZE);
    }

    @Override
    protected double[][] createMatrix(int size) {
        return new double[][]{
                {1, 2, 1},
                {2, 4, 2},
                {1, 2, 1}
        };
    }
}