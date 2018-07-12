package domain.customimage;

public class RGB extends TriChannelValue {


    public RGB(int red, int green, int blue) {
        super(red, green, blue);
    }

    public int getRed() {
        return (int)super.getChannel1Value();
    }

    public int getGreen() { return (int)super.getChannel2Value(); }

    public int getBlue() { return (int)super.getChannel3Value(); }
}
