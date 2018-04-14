package domain.customimage;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.PixelReader;

import java.awt.image.BufferedImage;
import java.util.*;

public class CustomImage {

    public static final CustomImage EMPTY = new CustomImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), Format.PNG);
    private static final Double INDEX_OUT_OF_BOUND = -1.0;
    private final PixelReader reader;
    private final BufferedImage bufferedImage;
    private final Format format;
    private List<Pixel> totalPixels;

    public CustomImage(BufferedImage bufferedImage, String formatString) {
        this.bufferedImage = bufferedImage;
        this.format = new Format(formatString);
        this.reader = SwingFXUtils.toFXImage(bufferedImage, null).getPixelReader();
        this.totalPixels = getTotalPixels();
    }

    private List<Pixel> getTotalPixels() {
        List<Pixel> total = new ArrayList<>();

        for (int column = 0; column < getWidth(); column++) {
            for (int row = 0; row < getHeight(); row++) {
                total.add(new Pixel(column, row, getPixelReader().getColor(column, row)));
            }
        }

        return total;
    }

    public List<Pixel> pickNRandomPixels(int n) {
        List<Pixel> copy = new LinkedList<>(totalPixels);
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
        return (int) (pixelValue.getR() +
                pixelValue.getG() +
                pixelValue.getB())
                / 3;
    }

    public RGB getPixelValue(Integer x, Integer y) {
        try {
            return new RGB(this.getRChannelValue(x, y), this.getGChannelValue(x, y), this.getBChannelValue(x, y));
        } catch (IndexOutOfBoundsException e) {
            return new RGB(INDEX_OUT_OF_BOUND, INDEX_OUT_OF_BOUND, INDEX_OUT_OF_BOUND);
        }
    }

    public Double getRChannelValue(int x, int y) {
        return reader.getColor(x, y).getRed() * 255;
    }

    public Double getGChannelValue(int x, int y) {
        return reader.getColor(x, y).getGreen() * 255;
    }

    public Double getBChannelValue(int x, int y) {
        return reader.getColor(x, y).getBlue() * 255;
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

    public List<Pixel> getPixels() {
        return totalPixels;
    }

    public class RGB {

        private final Double r;
        private final Double g;
        private final Double b;

        public RGB(Double R, Double G, Double B) {
            r = R;
            g = G;
            b = B;
        }

        public Double getR() {
            return r;
        }

        public Double getG() {
            return g;
        }

        public Double getB() {
            return b;
        }
    }
}
