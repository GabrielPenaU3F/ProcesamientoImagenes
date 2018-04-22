package domain.filter;

public abstract class Mask {

    private final Type type;
    private final int size;

    public Mask(Type type, int size) {
        this.type = type;
        this.size = size;
    }

    public Type getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public abstract double getValue(int x, int y);

    public abstract double getValue(int index);

    public enum Type {
        MEAN, WEIGHTED_MEAN, GAUSSIAN, PREWITT, MEDIAN, HIGH_PASS
    }

    public class ActionNotAvailableException extends RuntimeException {
        public ActionNotAvailableException(Type type) {
            super("Action Not Available by Type " + type);
        }
    }
}
