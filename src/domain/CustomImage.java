package domain;

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
        return this.format.getFormatString();
    }

    public BufferedImage getBufferedImage() {
        return this.bufferedImage;
    }

    public Integer getPixelValue(Integer x, Integer y) {
        return (int) ((
                this.getValueChannelR(x, y) * 255 +
                this.getValueChannelG(x, y) * 255 +
                this.getValueChannelB(x, y) * 255)
                / 3
        );
    }

    private Double getValueChannelR(int x, int y) {
        return reader.getColor(x, y).getRed();
    }

    private Double getValueChannelG(int x, int y) {
        return reader.getColor(x, y).getGreen();
    }

    private Double getValueChannelB(int x, int y) {
        return reader.getColor(x, y).getBlue();
    }
}
