package core.service.generation;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.shape.Circle;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;

public class ImageFigureService {

    public Image createImageWithCircle(int width, int height) {
        WritableImage writableImage = new WritableImage(width, height);
        fillWithBlack(width, height, writableImage);
        createCircle(width, height, writableImage);
        return writableImage;
    }

    public Image createImageWithQuadrate(int width, int height) {
        WritableImage writableImage = new WritableImage(width, height);
        fillWithBlack(width, height, writableImage);
        createQuadrate(width, height, writableImage);
        return writableImage;
    }

    private void fillWithBlack(int width, int height, WritableImage image) {
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                image.getPixelWriter().setColor(column, row, BLACK);
            }
        }
    }

    private void createCircle(int width, int height, WritableImage image) {
        int centerColumn = width / 2;
        int centerRow = height / 2;
        Circle circle = new Circle(centerRow, centerColumn, height / 6);
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                if (circle.contains(row, column)) {
                    image.getPixelWriter().setColor(column, row, WHITE);
                }
            }
        }
    }

    private void createQuadrate(int width, int height, WritableImage image) {
        for (int row = height / 3; row <= height * 2 / 3; row++) {
            for (int column = width / 3; column <= width * 2 / 3; column++) {
                image.getPixelWriter().setColor(column, row, WHITE);
            }
        }
    }
}
