package domain;

import java.awt.image.BufferedImage;

public class CustomImage {

    private BufferedImage bufferedImage;
    private Format format;


    public CustomImage(BufferedImage bufferedImage, String formatString) {
        this.bufferedImage = bufferedImage;
        this.format = new Format(formatString);
    }

    public String getFormatString() {
        return this.format.getFormatString();
    }

    public BufferedImage getBufferedImage() {

        return this.bufferedImage;

    }



}
