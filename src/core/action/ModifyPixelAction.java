package core.action;

import core.repository.ImageRepository;
import domain.CustomImage;

public class ModifyPixelAction {

    ImageRepository imageRepository;

    public ModifyPixelAction(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void execute(Integer pixelX, Integer pixelY, Double valor) {

        CustomImage modifiedImage = this.imageRepository.get(this.imageRepository.getCurrentImagePath().get());
        modifiedImage.modifyPixel(pixelX, pixelY, valor);
        this.imageRepository.putModifiedImage(modifiedImage);
    }

}
