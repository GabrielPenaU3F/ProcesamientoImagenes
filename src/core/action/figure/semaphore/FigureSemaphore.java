package core.action.figure.semaphore;


import domain.Figure;

public class FigureSemaphore {

    private static Figure value;

    public static Figure getValue() {
        return value;
    }

    public static void setValue(Figure newValue) {
        value = newValue;
    }
}
