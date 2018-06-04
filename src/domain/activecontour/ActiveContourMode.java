package domain.activecontour;

public class ActiveContourMode {

    private static boolean mode;

    public static boolean isSingle() {
        return mode;
    }

    public static void single() {
        mode = true;
    }

    public static void sequence() {
        mode = false;
    }
}
