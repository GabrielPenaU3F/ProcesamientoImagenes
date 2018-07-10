package domain.customimage.channel_matrix;

import domain.customimage.LAB;

public class LABChannelMatrix extends ChannelMatrix {

    public LABChannelMatrix(int width, int height) {
        super(width, height);
    }

    public LABChannelMatrix(double[][] LChannel, double[][] aChannel, double[][] bChannel) {
        super(LChannel, aChannel, bChannel);
    }

    public void setValue(int x, int y, LAB value) {
        this.channel1[x][y] = value.getL();
        this.channel2[x][y] = value.getA();
        this.channel3[x][y] = value.getB();
    }

    public double[][] getLChannel() {
        return super.getChannel1();
    }

    public double[][] getAChannel() {
        return super.getChannel2();
    }

    public double[][] getBChannel() {
        return super.getChannel3();
    }

}
