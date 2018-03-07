package presentation.presenter;

import core.action.image.SaveImageAction;
import domain.CustomImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.awt.image.BufferedImage;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;

public class ImageQuadratePresenter {

    private SaveImageAction saveImageAction;

    public ImageQuadratePresenter(SaveImageAction saveImageAction){
        this.saveImageAction = saveImageAction;
    }

    public Image createImageWithQuadrate(int width, int height) {
        WritableImage writableImage = new WritableImage(width, height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        fillWithBlack(width, height, pixelWriter);
        createQuadrate(width, height, pixelWriter);
        return writableImage;
    }

    private void fillWithBlack(int width, int height, PixelWriter pixelWriter) {
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                pixelWriter.setColor(column, row, BLACK);
            }
        }
    }

    private void createQuadrate(int width, int height, PixelWriter pixelWriter) {
        for (int row = height / 3; row <= height * 2 / 3; row++) {
            for (int column = width / 3; column <= width * 2 / 3; column++) {
                pixelWriter.setColor(column, row, WHITE);
            }
        }
    }

    public void saveImage(Image imageWithQuadrate, String fileName) {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(imageWithQuadrate, null);
        CustomImage customImage = new CustomImage(bufferedImage, "png");
        saveImageAction.execute(customImage, fileName);
    }
}
