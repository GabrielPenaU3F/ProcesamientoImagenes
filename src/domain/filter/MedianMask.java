package domain.filter;

public class MedianMask extends Mask {

    public MedianMask(int size) {
        super(Mask.Type.MEDIAN, size);
    }

    public double getValue(int x, int y) {
        throw new ActionNotAvailable(getType());
    }
}