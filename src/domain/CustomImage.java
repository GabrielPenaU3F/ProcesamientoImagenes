package domain;

import java.awt.image.BufferedImage;

public class CustomImage {

    private BufferedImage bufferedImage;
    private Format format;
    private Integer width;
    private Integer height;
    private Integer[][] rgbMatrix;


    public CustomImage(BufferedImage bufferedImage, String formatString) {
        this.bufferedImage = bufferedImage;
        this.format = new Format(formatString);
        this.height = bufferedImage.getHeight();
        this.width = bufferedImage.getWidth();
        //You'll always reach this line with width and height properly setted.
        this.rgbMatrix = this.calculateRGBMatrix();
    }

    private Integer[][] calculateRGBMatrix() {

        Integer[][] rgbMatrix = new Integer[this.width][this.height];

        for (int i=0; i < this.width; i++) {
            for (int j=0; j < this.height; j++) {

                rgbMatrix[i][j] = bufferedImage.getRGB(i, j);

            }
        }
        return rgbMatrix;

    }

    public String getFormatString() {
        return this.format.getFormatString();
    }

    public BufferedImage getBufferedImage() {
        return this.bufferedImage;
    }

    public Integer getRGB(Integer pixelX, Integer pixelY) {
        return rgbMatrix[pixelX][pixelY];
    }
}
