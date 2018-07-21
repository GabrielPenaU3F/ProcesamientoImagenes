package domain.customimage;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import core.provider.ServiceProvider;
import core.service.MatrixService;
import domain.customimage.channel_matrix.RGBChannelMatrix;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class CustomImage {

    public static final CustomImage EMPTY = new CustomImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), Format.PNG);
    private static final int INDEX_OUT_OF_BOUND = -1;
    private final PixelReader reader;
    private final BufferedImage bufferedImage;
    private final Format format;
    private final MatrixService matrixService;
    private int[][] redMatrix;
    private int[][] greenMatrix;
    private int[][] blueMatrix;
    private List<Pixel> pixelList;

    public CustomImage(RGBChannelMatrix RGBChannelMatrix, String formatString) {
        this(channelMatrixToFXImage(RGBChannelMatrix.getRedChannel(), RGBChannelMatrix.getGreenChannel(), RGBChannelMatrix.getBlueChannel()), formatString);
    }

    public CustomImage(Image image, String formatString) {
        this(SwingFXUtils.fromFXImage(image, null), formatString); //See the other constructor
    }

    public CustomImage(BufferedImage bufferedImage, String formatString) {
        this.bufferedImage = bufferedImage;
        this.format = new Format(formatString);
        WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);
        this.reader = image.getPixelReader();
        this.pixelList = getListOfPixels();
        this.matrixService = ServiceProvider.provideMatrixService();

        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        this.redMatrix = matrixService.toChannelMatrix((x, y) -> reader.getColor(x, y).getRed(), width, height);
        this.greenMatrix = matrixService.toChannelMatrix((x, y) -> reader.getColor(x, y).getGreen(), width, height);
        this.blueMatrix = matrixService.toChannelMatrix((x, y) -> reader.getColor(x, y).getBlue(), width, height);
    }

    private static Image channelMatrixToFXImage(int[][] red, int[][] green, int[][] blue) {
        int width = red.length;
        int height = red[0].length;
        WritableImage image = new WritableImage(width, height);
        PixelWriter pixelWriter = image.getPixelWriter();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int truncatedRed = truncate(red[i][j]);
                int truncatedGreen = truncate(green[i][j]);
                int truncatedBlue = truncate(blue[i][j]);
                Color color = Color.rgb(truncatedRed, truncatedGreen, truncatedBlue);
                pixelWriter.setColor(i, j, color);
            }
        }

        return image;
    }

    private static int truncate(int truncated) {
        return truncated > 255 ? 255 : truncated;
    }

    public int[][] getRedMatrix() {
        return this.matrixService.copy(redMatrix);
    }

    public int[][] getGreenMatrix() {
        return this.matrixService.copy(greenMatrix);
    }

    public int[][] getBlueMatrix() {
        return this.matrixService.copy(blueMatrix);
    }

    public int getPixelQuantity() {
        return this.pixelList.size();
    }

    private List<Pixel> getListOfPixels() {
        List<Pixel> total = new ArrayList<>();

        for (int column = 0; column < getWidth(); column++) {
            for (int row = 0; row < getHeight(); row++) {
                total.add(new Pixel(column, row, getPixelReader().getColor(column, row)));
            }
        }

        return total;
    }

    public List<Pixel> pickNRandomPixels(int n) {
        List<Pixel> copy = new LinkedList<>(pixelList);
        Collections.shuffle(copy);
        return copy.subList(0, n);
    }

    public String getFormatString() {
        return format.getFormatString();
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public int getAverageValue(Integer x, Integer y) {
        RGB pixelValue = getPixelValue(x, y);
        return Math.round((pixelValue.getRed() +
                pixelValue.getGreen() +
                pixelValue.getBlue()) / 3);
    }

    public RGB getPixelValue(Integer x, Integer y) {
        try {
            return new RGB(this.getRChannelValue(x, y), this.getGChannelValue(x, y), this.getBChannelValue(x, y));
        } catch (IndexOutOfBoundsException e) {
            return new RGB(INDEX_OUT_OF_BOUND, INDEX_OUT_OF_BOUND, INDEX_OUT_OF_BOUND);
        }
    }

    public int getRChannelValue(int x, int y) {
        return (int) (reader.getColor(x, y).getRed() * 255);
    }

    public int getGChannelValue(int x, int y) {
        return (int) (reader.getColor(x, y).getGreen() * 255);
    }

    public int getBChannelValue(int x, int y) {
        return (int) (reader.getColor(x, y).getBlue() * 255);
    }

    public Integer getHeight() {
        return this.bufferedImage.getHeight();
    }

    public Integer getWidth() {
        return this.bufferedImage.getWidth();
    }

    public PixelReader getPixelReader() {
        return reader;
    }

    public Image toFXImage() {
        return this.matrixService.toImage(this.getRedMatrix(), this.getGreenMatrix(), this.getBlueMatrix());
    }

    public boolean isPositionValid(int width, int height, int i, int j) {
        // Ignore the portion of the mask outside the image.
        return j >= 0 && j < height && i >= 0 && i < width;
    }

    public Color getColor(int x, int y) {
        return Color.rgb(getRChannelValue(x, y), getGChannelValue(x, y), getBChannelValue(x, y));
    }

    public RGBChannelMatrix getRgbChannelMatrix() {
        return new RGBChannelMatrix(this.redMatrix, this.greenMatrix, this.blueMatrix);
    }

    public enum SystemType {
        RGB, LAB
    }
}
