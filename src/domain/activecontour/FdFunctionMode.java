package domain.activecontour;

public class FdFunctionMode {
    private static boolean mode;

    public static void classic() {
        mode = true;
    }

    public static void withEpsilon() {
        mode = false;
    }

    public static boolean isClassic() {
        return mode;
    }
}
