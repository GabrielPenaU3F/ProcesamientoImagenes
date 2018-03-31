package core.action.edit.space_domain;

import core.repository.ImageRepository;
import core.service.ModifyImageService;
import domain.customimage.CustomImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.awt.image.BufferedImage;

public class ApplyContrastAction {


    private final ImageRepository imageRepository;
    private final ModifyImageService modifyImageService;

    public ApplyContrastAction(ImageRepository imageRepository, ModifyImageService modifyImageService) {
        this.imageRepository = imageRepository;
        this.modifyImageService = modifyImageService;
    }

    public Image execute(int s1, int s2, int r1, int r2) {

        if (!this.imageRepository.getImage().isPresent()) {
            return new WritableImage(300,300);
        } else {

            CustomImage customImage = this.imageRepository.getImage().get();
            WritableImage image = new WritableImage(customImage.getWidth(), customImage.getHeight());
            PixelWriter writer = image.getPixelWriter();

            //Linear transformation parameters: s = m1*r ; s = m2*r + b
            double m1 = (s1 / r1);
            double m2 = ((255 - s2) / (255 - r2));
            double b = 255 - (m2 * 255);

            for (int i = 0; i < customImage.getWidth(); i++) {
                for (int j = 0; j < customImage.getHeight(); j++) {

                    if (customImage.getPixelValue(i, j) < r1) {
                        this.modifyImageService.modifySinglePixel(i, j, (int) (m1 * customImage.getPixelValue(i, j)), writer);
                    } else if (customImage.getPixelValue(i, j) > r2) {
                        this.modifyImageService.modifySinglePixel(i, j, (int) (m2 * customImage.getPixelValue(i, j) + b), writer);
                    } else { //Middle zone
                        this.modifyImageService.modifySinglePixel(i, j, customImage.getPixelValue(i, j), writer);
                    }

                }
            }
            return image;
        }
    }
}
