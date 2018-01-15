package domain;

import java.awt.image.BufferedImage;

public class Image extends BufferedImage{

    private Integer height;
    private Integer width;
    private Integer[][] greyMatrix;
    private Format format;


    public Image(int width, int height, Format format) {
        super(width, height, 1);
        this.format = format;
        this.height = height;
        this.width = width;
        this.greyMatrix = new Integer[width][height];
    }



}
