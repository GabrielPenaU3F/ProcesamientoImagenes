package core.service;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ImageRawService {

    public BufferedImage load(File file, int width, int height) {


        WritableImage image = new WritableImage(width, height);
        PixelWriter writer = image.getPixelWriter();
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            int counter = 0;
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {

                    int grey = ((int) bytes[counter] & 0xff);
                    Color color = Color.rgb(grey, grey, grey);
                    writer.setColor(j, i, color);
                    counter++;

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return SwingFXUtils.fromFXImage(image, null);
    }

    public void save(BufferedImage bufferedImage, String stringPath) {

        ByteArrayOutputStream s = new ByteArrayOutputStream();

        try {

            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            PixelReader reader = image.getPixelReader();
            int width = (int) image.getWidth();
            int height = (int) image.getHeight();
            byte[] bytes = new byte[width * height];
            int counter = 0;

            for (int i = 0; i < image.getWidth(); i++) {
                for (int j = 0; j < image.getHeight(); j++) {

                    int gray = (int) (reader.getColor(j, i).getRed() * 255);
                    byte byteGray = (byte) gray;
                    bytes[counter] = byteGray; //Since the image is grey, every channel contains the same value
                    counter++;

                }
            }

            Files.write(Paths.get(stringPath), bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
