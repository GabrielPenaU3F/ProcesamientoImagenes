package domain;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;

public class CustomImage {

    private final PixelReader reader;
    private BufferedImage bufferedImage;
    private Format format;
    private Integer width;
    private Integer height;
    private Double red = 0.0;
    private Double green = 0.0;
    private Double blue = 0.0;
    private Double gray = 0.0;
    private Integer totalPixel = 0;

    public CustomImage(BufferedImage bufferedImage, String formatString) {
        this.bufferedImage = bufferedImage;
        this.format = new Format(formatString);
        this.height = bufferedImage.getHeight();
        this.width = bufferedImage.getWidth();

        this.reader = SwingFXUtils.toFXImage(bufferedImage, null).getPixelReader();

        this.calculateParams();
    }

    private void calculateParams() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                Color color = reader.getColor(x, y);
                this.red += (color.getRed());
                this.green += (color.getGreen());
                this.blue += (color.getBlue());
                this.gray += ((color.getRed() + color.getGreen() + color.getBlue()) / 3);

                this.totalPixel++;
            }
        }
    }

    public String getFormatString() {
        return this.format.getFormatString();
    }

    public BufferedImage getBufferedImage() {
        return this.bufferedImage;
    }

    public Integer getPixelValue(Integer x, Integer y) {
        return (int) ((this.getValueChannelR(x, y) * 255 +
                this.getValueChannelG(x, y) * 255 +
                this.getValueChannelB(x, y) * 255)
                / 3
        );
    }

    public Double getValueChannelR(int x, int y) {
        return reader.getColor(x, y).getRed();
    }

    public Double getValueChannelG(int x, int y) {
        return reader.getColor(x, y).getGreen();
    }

    public Double getValueChannelB(int x, int y) {
        return reader.getColor(x, y).getBlue();
    }

    public Double getAverageGrey() {
        return this.gray / this.totalPixel;
    }

    public Double getTotalValueChannelR() {
        return this.red;
    }

    public Double getTotalValueChannelG() {
        return this.green;
    }

    public Double getTotalValueChannelB() {
        return this.blue;
    }

    public int getTotalPixel() {
        return this.totalPixel;
    }

}
