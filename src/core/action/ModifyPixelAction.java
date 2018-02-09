package core.action;

import core.repository.ImageRepository;
import domain.CustomImage;

public class ModifyPixelAction {

    ImageRepository imageRepository;

    public ModifyPixelAction(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public CustomImage execute(Integer pixelX, Integer pixelY, Double value) {

        CustomImage modifiedImage = imageRepository.get(imageRepository.getCurrentImagePath().get());
        modifiedImage.modifyPixel(pixelX, pixelY, value);
        imageRepository.putModifiedImage(modifiedImage);

        return modifiedImage;
    }

}
