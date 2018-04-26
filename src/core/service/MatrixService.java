package core.service;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.function.BiFunction;

public class MatrixService {

    public int[][] toChannelMatrix(Image image, BiFunction<Integer, Integer, Double> channel) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        int[][] matrix = new int[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                matrix[x][y] = (int) (channel.apply(x, y) * 255);
            }
        }
        return matrix;
    }

    //Only for gray images
    public int[][] toGrayMatrix(Image image) {
        PixelReader reader = image.getPixelReader();
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        int[][] matrix = new int[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int grayMean = (int)(reader.getColor(x,y).getRed()*255 + reader.getColor(x,y).getBlue()*255 + reader.getColor(x,y).getGreen()*255)/3;
                matrix[x][y] = grayMean;
            }
        }
        return matrix;
    }

    public Image toGrayImage(int[][] matrix) {
        return toImage(matrix, matrix, matrix);
    }

    public Image toImage(int[][] red, int[][] green, int[][] blue) {
        int width = red.length;
        int height = red[0].length;
        WritableImage image = new WritableImage(width, height);
        PixelWriter pixelWriter = image.getPixelWriter();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color color = Color.rgb(red[i][j], green[i][j], blue[i][j]);
                pixelWriter.setColor(i, j, color);
            }
        }

        return image;
    }
}
