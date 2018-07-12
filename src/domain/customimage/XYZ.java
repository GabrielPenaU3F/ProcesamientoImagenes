package domain.customimage;

public class XYZ extends TriChannelValue {

    public XYZ(double X, double Y, double Z) {
        super(X,Y,Z);
    }

    public double getX() {
        return super.getChannel1Value();
    }

    public double getY() { return super.getChannel2Value(); }

    public double getZ() {
        return super.getChannel3Value();
    }
}
