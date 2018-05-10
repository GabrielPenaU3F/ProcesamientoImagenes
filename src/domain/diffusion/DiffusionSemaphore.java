package domain.diffusion;

public class DiffusionSemaphore {

    private static Diffusion.Type value;

    public static void setValue(Diffusion.Type newValue) {
        value = newValue;
    }

    public static Diffusion.Type getValue() {
        return value;
    }
}
