package domain.customimage.channel_matrix;

import domain.customimage.RGB;
import domain.customimage.TriChannelValue;

import java.util.ArrayList;
import java.util.List;

public class ChannelMatrix {

    protected double[][] channel1;
    protected double[][] channel2;
    protected double[][] channel3;

    public ChannelMatrix(int width, int height) {
        this.channel1 = new double[width][height];
        this.channel2 = new double[width][height];
        this.channel3 = new double[width][height];
    }

    public ChannelMatrix(double[][] channel1, double[][] channel2, double[][] channel3) {
        this.channel1 = channel1;
        this.channel2 = channel2;
        this.channel3 = channel3;
    }


    public double[][] getChannel1() {
        return channel1;
    }

    public double[][] getChannel2() {
        return channel2;
    }

    public double[][] getChannel3() {
        return channel3;
    }

    public int getWidth() {
        return channel1.length;
    }

    public int getHeight() {
        return channel1[0].length;
    }

    public List<double[][]> getChannels() {
        List<double[][]> channels = new ArrayList<>();
        channels.add(this.channel1);
        channels.add(this.channel2);
        channels.add(this.channel3);
        return channels;
    }

    public void setValue(int x, int y, TriChannelValue value) {

    }
}
