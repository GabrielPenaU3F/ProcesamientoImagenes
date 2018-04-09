package core.action.edit.space_domain;

import core.repository.ImageRepository;
import core.service.ModifyImageService;
import domain.customimage.CustomImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.awt.image.BufferedImage;
import java.util.Optional;

public class ApplyThresholdAction {

    private final ModifyImageService modifyImageService;
    private final ImageRepository imageRepository;

    public ApplyThresholdAction(ImageRepository imageRepository, ModifyImageService modifyImageService) {
        this.imageRepository = imageRepository;
        this.modifyImageService = modifyImageService;
    }

    public Image execute(String thresholdString) {

        int threshold = Integer.parseInt(thresholdString);
        Optional<CustomImage> imageOptional = this.imageRepository.getImage();
        if(!imageOptional.isPresent()) return SwingFXUtils.toFXImage(new BufferedImage(500,500, BufferedImage.TYPE_INT_RGB), null);

        CustomImage customImage = imageOptional.get();
        WritableImage image = new WritableImage(customImage.getWidth(), customImage.getHeight());
        PixelWriter pixelWriter = image.getPixelWriter();

        for (int i=0; i < image.getWidth(); i++) {
            for(int j=0; j < image.getHeight(); j++) {
                if (customImage.getAverageValue(i,j) >= threshold) {
                    this.modifyImageService.modifySinglePixel(i, j, 255, pixelWriter);
                } else {
                    this.modifyImageService.modifySinglePixel(i, j, 0, pixelWriter);
                }
            }
        }

        return image;

    }
}
