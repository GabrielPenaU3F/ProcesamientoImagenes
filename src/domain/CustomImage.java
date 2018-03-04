package domain;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.awt.image.BufferedImage;

public class CustomImage {

    private PixelReader reader;
    private BufferedImage bufferedImage;
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

    public void modifyPixel(Integer pixelX, Integer pixelY, Double value) {

        WritableImage writableImage = SwingFXUtils.toFXImage(bufferedImage, null);

        int width = (int) writableImage.getWidth();
        int height = (int) writableImage.getHeight();
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixelWriter.setArgb(x, y, reader.getArgb(x, y));
            }
        }

        pixelWriter.setArgb(pixelX, pixelY, value.intValue());
        bufferedImage = SwingFXUtils.fromFXImage(writableImage, null);
    }

    public PixelReader getPixelReader() {
        return reader;
    }
}
