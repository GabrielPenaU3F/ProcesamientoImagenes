package domain.automaticthreshold;


public class ImageLimitValues {

    private int minLimit;
    private int maxLimit;

    public ImageLimitValues(int minLimit, int maxLimit){
        this.minLimit = minLimit;
        this.maxLimit = maxLimit;
    }

    public int getMinLimit() {
        return minLimit;
    }

    public void setMinLimit(int minLimit) {
        this.minLimit = minLimit;
    }

    public int getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(int maxLimit) {
        this.maxLimit = maxLimit;
    }
}
