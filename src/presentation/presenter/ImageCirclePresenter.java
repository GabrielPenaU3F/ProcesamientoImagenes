package presentation.presenter;

import core.action.image.SaveImageAction;
import domain.CustomImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.shape.Circle;

import java.awt.image.BufferedImage;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;


public class ImageCirclePresenter {

    private SaveImageAction saveImageAction;

    public ImageCirclePresenter(SaveImageAction saveImageAction){
        this.saveImageAction = saveImageAction;
    }

    public Image createImageWithCircle(int width, int height) {
        WritableImage writableImage = new WritableImage(width, height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        fillWithBlack(width, height, pixelWriter);
        createCircle(width, height, pixelWriter);
        return writableImage;
    }

    private void fillWithBlack(int width, int height, PixelWriter pixelWriter) {
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                pixelWriter.setColor(column, row, BLACK);
            }
        }
    }

    private void createCircle(int width, int height, PixelWriter pixelWriter) {
        int centerColumn = width / 2;
        int centerRow = height / 2;
        Circle circle = new Circle(centerRow, centerColumn, height / 6);
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                if (circle.contains(row, column)) {
                    pixelWriter.setColor(column, row, WHITE);
                }
            }
        }
    }

    public void saveImage(Image imageWithCircle, String fileName) {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(imageWithCircle, null);
        CustomImage customImage = new CustomImage(bufferedImage, "png");
        saveImageAction.execute(customImage, fileName);
    }
}
