package core.action.gradient.semaphore;

import domain.Gradient;

public class GradientSemaphore {

    private static Gradient value;

    public static Gradient getValue() {
        return value;
    }

    public static void setValue(Gradient newValue) {
        value = newValue;
    }
}
