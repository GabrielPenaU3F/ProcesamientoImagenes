package domain;

public class ImageInform {

    private final Double averageBandR;
    private final Double averageBandG;
    private final Double averageBandB;
    private final Double averageBandGray;
    private final Long totalPixel;

    public ImageInform(Double averageBandR, Double averageBandG, Double averageBandB, Double averageBandGray, Long totalPixel) {
        this.averageBandR = averageBandR;
        this.averageBandG = averageBandG;
        this.averageBandB = averageBandB;
        this.averageBandGray = averageBandGray;
        this.totalPixel = totalPixel;
    }

    public Double getAverageBandR() {
        return averageBandR;
    }

    public Double getAverageBandG() {
        return averageBandG;
    }

    public Double getAverageBandB() {
        return averageBandB;
    }

    public Double getAverageBandGray() {
        return averageBandGray;
    }

    public Long getTotalPixel() {
        return totalPixel;
    }
}
