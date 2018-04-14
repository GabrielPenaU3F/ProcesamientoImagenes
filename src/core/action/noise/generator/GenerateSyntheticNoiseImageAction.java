package core.action.noise.generator;

import core.service.ImageOperationsService;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class GenerateSyntheticNoiseImageAction {

    private final ImageOperationsService imageOperationsService;

    public GenerateSyntheticNoiseImageAction(ImageOperationsService imageOperationsService) {
        this.imageOperationsService = imageOperationsService;
    }

    public Image execute(int[][] randomNumberMatrix) {

        int[][] realPixelMatrix = this.imageOperationsService.adjustScale(this.imageOperationsService.displacePixelsValues((randomNumberMatrix))); //Set the numbers in the range 0-255

        WritableImage image = new WritableImage(100, 100);
        PixelWriter writer = image.getPixelWriter();

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                int grayLevel = realPixelMatrix[i][j];
                writer.setColor(i, j, Color.rgb(grayLevel, grayLevel, grayLevel));
            }
        }

        return image;
    }

}
