package domain.filter;

import java.util.ArrayList;
import java.util.List;

public class MedianMask extends Mask {

    private final List<Integer> weights;

    public MedianMask(int size) {
        super(Type.MEDIAN, size);
        this.weights = weights(size);
    }

    public MedianMask(int size, List<Integer> weights) {
        super(Mask.Type.MEDIAN, size);
        this.weights = weights;
    }

    private List<Integer> weights(int size) {
        List<Integer> normalWeights = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                normalWeights.add(1);
            }
        }
        return normalWeights;
    }

    @Override
    public double getValue(int x, int y) {
        throw new ActionNotAvailableException(getType());
    }

    public double getValue(int index) {
        return weights.get(index);
    }
}