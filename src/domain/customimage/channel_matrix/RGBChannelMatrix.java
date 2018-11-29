package domain.customimage.channel_matrix;

import core.service.MatrixService;
import domain.customimage.RGB;

public class RGBChannelMatrix extends ChannelMatrix {

    public RGBChannelMatrix(int width, int height) {
        super(width, height);
    }

    public RGBChannelMatrix(int[][] redChannel, int[][] greenChannel, int[][] blueChannel) {
        super(MatrixService.convertToDouble(redChannel), MatrixService.convertToDouble(greenChannel), MatrixService.convertToDouble(blueChannel));
    }

    public RGBChannelMatrix(double[][] redChannel, double[][] greenChannel, double[][] blueChannel) {
        super(redChannel, greenChannel, blueChannel);
    }

    public RGB setValue(int x, int y, RGB value) {
        this.channel1[x][y] = value.getRed();
        this.channel2[x][y] = value.getGreen();
        this.channel3[x][y] = value.getBlue();

        return value;
    }

    public int[][] getRedChannel() {
        return MatrixService.convertToInt(super.getChannel1());
    }

    public int[][] getGreenChannel() {
        return MatrixService.convertToInt(super.getChannel2());
    }

    public int[][] getBlueChannel() {
        return MatrixService.convertToInt(super.getChannel3());
    }

    public Channel getRedAsChannel() {
        return new Channel(super.getChannel1());
    }

    public Channel getGreenAsChannel() {
        return new Channel(super.getChannel2());
    }

    public Channel getBlueAsChannel() {
        return new Channel(super.getChannel3());
    }
}
