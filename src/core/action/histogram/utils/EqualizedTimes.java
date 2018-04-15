package core.action.histogram.utils;

public class EqualizedTimes {

    private static int times;

    public static int getValue() {
        return times;
    }

    public static void once() {
        times = 1;
    }

    public static void twice() {
        times = 2;
    }
}
