package core.action.edit.space_domain;

import core.repository.ImageRepository;
import domain.customimage.CustomImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.Optional;

public class GammaFunctionAction {


    private final ImageRepository imageRepository;

    public GammaFunctionAction(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image execute(double gamma) {

        Optional<CustomImage> optional = this.imageRepository.getImage();
        if (!optional.isPresent()) {
            return new WritableImage(300,300);
        }

        Image image = SwingFXUtils.toFXImage(optional.get().getBufferedImage(),null);
        PixelReader reader = image.getPixelReader();
        WritableImage writableImage = new WritableImage((int)image.getWidth(), (int)image.getHeight());
        PixelWriter writer = writableImage.getPixelWriter();

        double c = this.calculateC(gamma);
        for (int i=0; i < image.getWidth(); i++) {
            for (int j=0; j < image.getHeight(); j++) {

                int oldGrayLevel = (int) (reader.getColor(i, j).getRed() * 255);
                int newGrayLevel = (int) (c * (Math.pow(oldGrayLevel, gamma)));
                Color newColor = Color.rgb(newGrayLevel, newGrayLevel, newGrayLevel);
                writer.setColor(i, j, newColor);

            }
        }
        return writableImage;
    }

    private double calculateC(double gamma) {
        return 255/(Math.pow(255,gamma));
    }

}
