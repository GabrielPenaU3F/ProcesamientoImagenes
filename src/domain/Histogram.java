package domain;

public class Histogram {

    private final double[] values;
    private final Integer totalPixels;
    private final Double minValue;

    public Histogram(double[] values, Integer totalPixels, Double minValue) {
        this.values = values;
        this.totalPixels = totalPixels;
        this.minValue = minValue;
    }

    public double[] getValues() {
        return values;
    }

    public Integer getTotalPixels() {
        return totalPixels;
    }

    public Double getMinValue() {
        return minValue;
    }
}
