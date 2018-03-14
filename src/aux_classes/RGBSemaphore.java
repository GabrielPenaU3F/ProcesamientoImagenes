package aux_classes;

import domain.RGBChannel;

public class RGBSemaphore {

    private static RGBChannel value;

    public static RGBChannel getValue() {
        return value;
    }

    public static void setValue(RGBChannel newValue) {
        value = newValue;
    }

}
