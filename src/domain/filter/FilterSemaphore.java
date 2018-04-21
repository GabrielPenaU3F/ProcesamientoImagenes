package domain.filter;

import io.reactivex.functions.Action;

import java.util.function.Supplier;

public class FilterSemaphore {

    private static Mask.Type type;

    public static void setValue(Mask.Type newType) {
        type = newType;
    }

    public static Mask.Type getValue() {
        return type;
    }

    public static boolean is(Mask.Type value) {
        return type.equals(value);
    }
}
