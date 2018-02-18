package core.action;

import core.repository.ImageRepository;
import domain.CustomImage;

public class ModifyPixelAction {

    ImageRepository imageRepository;

    public ModifyPixelAction(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void execute(CustomImage image, Integer pixelX, Integer pixelY, Double value) {
        image.modifyPixel(pixelX, pixelY, value);
        imageRepository.setCurrentModifiedImage(image);
    }

}
