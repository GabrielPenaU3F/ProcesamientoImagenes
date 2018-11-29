package domain.customimage;

public class TriChannelValue {

    private double channel1Value;
    private double channel2Value;
    private double channel3Value;

    public TriChannelValue(double channel1Value, double channel2Value, double channel3Value) {
        this.channel1Value = channel1Value;
        this.channel2Value = channel2Value;
        this.channel3Value = channel3Value;
    }


    public double getChannel1Value() {
        return channel1Value;
    }

    public double getChannel2Value() {
        return channel2Value;
    }

    public double getChannel3Value() {
        return channel3Value;
    }
}
