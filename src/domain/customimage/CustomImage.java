package domain.customimage;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.PixelReader;

import java.awt.image.BufferedImage;

public class CustomImage {

    private final PixelReader reader;
    private final BufferedImage bufferedImage;
    private final Format format;

    public CustomImage(BufferedImage bufferedImage, String formatString) {
        this.bufferedImage = bufferedImage;
        this.format = new Format(formatString);
        this.reader = SwingFXUtils.toFXImage(bufferedImage, null).getPixelReader();
    }

    public String getFormatString() {
        return format.getFormatString();
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public Integer getPixelValue(Integer x, Integer y) {
        return (int) averageValue(x, y);
    }

    private double averageValue(Integer x, Integer y) {
        return (this.getRChannelValue(x, y) +
                this.getGChannelValue(x, y) +
                this.getBChannelValue(x, y))
                / 3;
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
}
