package domain.Loaders;

import domain.Format;
import domain.Image;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class RAWLoader implements ImgLoader {

    public Image loadImg(String path){

        try {

            FileInputStream imgStream = this.openFile(path);
            Integer width = this.getImageWidth(path);
            Integer height = this.getImageHeight(path);
            Color color;

            Image img = new Image(width, height, Format.RAW);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    int pixel_value = imgStream.read();
                    color = new Color(pixel_value, pixel_value, pixel_value);
                    img.setRGB(j, i, color.getRGB()); //NO SE POR QUE ES (j,i) EN LUGAR DE (i,j). ESTA ASI EN EL TP DE LOS CHICOS
                }
            }
            imgStream.close();
            return img;

        } catch (IOException o) {

            throw new RuntimeException("IO Exception");

        }

    }

    @Override
    public Integer getImageWidth(String path) {

        try {

            FileInputStream imgStream = this.openFile(path);
            Integer width = 0;

            //TODO

            imgStream.close();

            return width;

        } catch (IOException o) {

            throw new RuntimeException("IO Exception");

        }
    }

    @Override
    public Integer getImageHeight(String path) {

        try {

            FileInputStream imgStream = this.openFile(path);
            Integer height = 0;

            //TODO

            imgStream.close();
            return height;

        } catch (IOException o) {

            throw new RuntimeException("IO Exception");

        }
    }

    private FileInputStream openFile(String name) {
        try {

            File img = new File(name);
            FileInputStream is = new FileInputStream(img);
            return is;

        } catch (FileNotFoundException o) {
            throw new RuntimeException("File not found exception");
        }
    }
}
