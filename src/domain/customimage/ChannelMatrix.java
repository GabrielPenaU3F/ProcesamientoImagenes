package domain.customimage;

public class ChannelMatrix {

    private int[][] redChannel;
    private int[][] greenChannel;
    private int[][] blueChannel;

    public ChannelMatrix(int width, int height) {
        this.redChannel = new int[width][height];
        this.greenChannel = new int[width][height];
        this.blueChannel = new int[width][height];
    }

    public ChannelMatrix(int[][] redChannel, int[][] greenChannel, int[][] blueChannel) {
        this.redChannel = redChannel;
        this.greenChannel = greenChannel;
        this.blueChannel = blueChannel;
    }

    public void setValue(int x, int y, RGB value) {
        this.redChannel[x][y] = value.getRed();
        this.greenChannel[x][y] = value.getGreen();
        this.blueChannel[x][y] = value.getBlue();
    }

    public int[][] getRedChannel() {
        return redChannel;
    }

    public int[][] getGreenChannel() {
        return greenChannel;
    }

    public int[][] getBlueChannel() {
        return blueChannel;
    }

    public int getWidth() {
        return this.redChannel.length;
    }

    public int getHeight() {
        return this.redChannel[0].length;
    }
}
