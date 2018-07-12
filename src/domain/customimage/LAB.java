package domain.customimage;

public class LAB extends TriChannelValue {

    public LAB(double L, double a, double b) {
        super(L,a,b);
    }

    public double getL() {
        return super.getChannel1Value();
    }

    public double getA() {
        return super.getChannel2Value();
    }

    public double getB() {
        return super.getChannel3Value();
    }
}
