package core.service;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class MatrixService {

    public int[][] toGrayMatrix(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        PixelReader pixelReader = image.getPixelReader();

        int[][] matrix = new int[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double gray = pixelReader.getColor(x, y).getRed() * 255;
                matrix[x][y] = (int) gray;
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
        for (int i = 0; i < (int) image.getWidth(); i++) {
            for (int j = 0; j < (int) image.getHeight(); j++) {
                Color color = Color.rgb(red[i][j], green[i][j], blue[i][j]);
                pixelWriter.setColor(i, j, color);
            }
        }

        return image;
    }
}
