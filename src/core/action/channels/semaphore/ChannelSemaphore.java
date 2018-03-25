package core.action.channels.semaphore;

import domain.Channel;

public class ChannelSemaphore {

    private static Channel value;

    public static Channel getValue() {
        return value;
    }

    public static void setValue(Channel newValue) {
        value = newValue;
    }
}
