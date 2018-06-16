package domain.hough;

import java.util.Objects;

public class XYRCircle {

    private int xCenter;
    private int yCenter;
    private int radius;

    public XYRCircle(int xCenter, int yCenter, int radius) {
        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.radius = radius;
    }

    public int getxCenter() { return this.xCenter;}
    public int getyCenter() { return this.yCenter;}
    public int getRadius() { return this.radius;}

    @Override
    public boolean equals(Object o) {
        XYRCircle xyrCircle = (XYRCircle) o;
        return ((xyrCircle.getxCenter() == this.xCenter) && (xyrCircle.getyCenter() == this.yCenter) && (xyrCircle.getRadius() == this.radius));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.xCenter, this.yCenter, this.radius);
    }

}
