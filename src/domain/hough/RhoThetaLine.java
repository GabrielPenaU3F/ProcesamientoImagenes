package domain.hough;

import java.util.Objects;

public class RhoThetaLine {

    private double rho;
    private double theta;

    public RhoThetaLine(double rho, double theta) {
        this.rho = rho;
        this.theta = theta;
    }

    public double getRho() {return this.rho;}

    public double getTheta() {return this.theta;}

    @Override
    public boolean equals(Object o) {
        RhoThetaLine rhoTheta = (RhoThetaLine)o;
        return ((rhoTheta.getRho() == this.rho) && (rhoTheta.getTheta() == this.theta));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.rho, this.theta);
    }
}
