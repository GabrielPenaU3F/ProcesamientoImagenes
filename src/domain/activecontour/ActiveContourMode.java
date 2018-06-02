package domain.activecontour;

public class ActiveContourMode {

    private static boolean single;

    public static boolean isSingle() {
        return single;
    }

    public static void single() {
        single = true;
    }

    public static void sequence() {
        single = false;
    }
}
