package domain.customimage.channel_matrix;

public abstract class ChannelMatrix {

    protected double[][] channel1;
    protected double[][] channel2;
    protected double[][] channel3;

    protected ChannelMatrix(int width, int height) {
        this.channel1 = new double[width][height];
        this.channel2 = new double[width][height];
        this.channel3 = new double[width][height];
    }

    protected ChannelMatrix(double[][] channel1, double[][] channel2, double[][] channel3) {
        this.channel1 = channel1;
        this.channel2 = channel2;
        this.channel3 = channel3;
    }


    protected double[][] getChannel1() {
        return channel1;
    }

    protected double[][] getChannel2() {
        return channel2;
    }

    protected double[][] getChannel3() {
        return channel3;
    }

    public int getWidth() {
        return channel1.length;
    }

    public int getHeight() {
        return channel1[0].length;
    }
}
