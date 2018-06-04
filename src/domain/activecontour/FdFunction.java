package domain.activecontour;

public class FdFunction {

    public static boolean classicHigher(int imageAverageValue, int objectGrayAverage, int backgroundGrayAverage) {
        return Math.log(module(backgroundGrayAverage, imageAverageValue) / module(objectGrayAverage, imageAverageValue)) >= 0;
    }

    public static boolean classicLower(int imageAverageValue, int objectGrayAverage, int backgroundGrayAverage) {
        return Math.log(module(backgroundGrayAverage, imageAverageValue) / module(objectGrayAverage, imageAverageValue)) <= 0;
    }

    public static boolean epsilonLower(int imageAverageValue, int objectGrayAverage, double epsilon) {
        return module(objectGrayAverage, imageAverageValue) <= epsilon;
    }

    private static double module(int value, int imageValue) {
        return Math.abs(value - imageValue);
    }
}
