package domain.activecontour;

public class FdFunction {

    public static boolean lowerThanZero(int imageAverageValue, int backgroundGrayAverage, int objectGrayAverage, double epsilon) {
        return Math.log(module(backgroundGrayAverage, imageAverageValue) / module(objectGrayAverage, imageAverageValue)) < epsilon;
    }

    public static boolean greaterThanEpsilon(int imageAverageValue, int objectGrayAverage, double epsilon) {
        return module(objectGrayAverage, imageAverageValue) > epsilon;
    }

    private static double module(int value, int imageValue) {
        return Math.abs(value - imageValue);
    }
}