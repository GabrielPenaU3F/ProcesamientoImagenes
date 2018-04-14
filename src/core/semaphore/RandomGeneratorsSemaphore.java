package core.semaphore;

import domain.RandomElement;

public class RandomGeneratorsSemaphore {

    private static RandomElement value;

    public static void setValue(RandomElement newValue) { value = newValue;}

    public static RandomElement getValue() { return value; }

}
