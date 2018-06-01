package domain.activecontour;

import domain.customimage.CustomImage;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ContourCustomImage {

    private final CustomImage customImage;
    private final ActiveContour activeContour;

    public ContourCustomImage(CustomImage customImage, ActiveContour activeContour) {
        this.customImage = customImage;
        this.activeContour = activeContour;
    }

    public CustomImage getCustomImage() {
        return customImage;
    }

    public ActiveContour getActiveContour() {
        return activeContour;
    }

    public Image drawActiveContour() {
        WritableImage imageWithContour = new WritableImage(customImage.getWidth(), customImage.getHeight());
        PixelWriter pixelWriter = imageWithContour.getPixelWriter();

        for (int i = 0; i < customImage.getWidth(); i++) {
            for (int j = 0; j < customImage.getHeight(); j++) {
                pixelWriter.setColor(i, j, customImage.getColor(i, j));
            }
        }

        activeContour.getlIn().forEach(xyPoint -> pixelWriter.setColor(xyPoint.getX(), xyPoint.getY(), Color.RED));
        activeContour.getlOut().forEach(xyPoint -> pixelWriter.setColor(xyPoint.getX(), xyPoint.getY(), Color.BLUE));

        return new CustomImage(imageWithContour, customImage.getFormatString()).toFXImage();
    }
}
