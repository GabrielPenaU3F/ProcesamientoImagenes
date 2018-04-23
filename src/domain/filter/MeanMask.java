package domain.filter;

public class MeanMask extends Mask {

    private final double coefficient;

    public MeanMask(int size) {
        super(Type.MEAN, size);

        coefficient = 1 / Math.pow(size, 2);
    }

    public double getValue(int index) {
        return coefficient;
    }
}
