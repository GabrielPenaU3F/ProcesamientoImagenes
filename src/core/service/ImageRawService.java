package core.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ImageRawService {

    public BufferedImage load(File file, int width, int height) {

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            int counter = 0;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int argb = 0;
                    argb += -16777216; // 255 alpha
                    int blue = ((int) bytes[counter] & 0xff);
                    int green = ((int) bytes[counter] & 0xff) << 8;
                    int red = ((int) bytes[counter] & 0xff) << 16;
                    int color = argb + red + green + blue;
                    image.setRGB(j, i, color);
                    counter++;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
}
