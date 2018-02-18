package core.action;

import core.repository.ImageRepository;
import domain.CustomImage;

public class ModifyPixelAction {

    public static final String COPY_PATH = "%s(%s)";
    ImageRepository imageRepository;

    public ModifyPixelAction(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public CustomImage execute(Integer pixelX, Integer pixelY, Double value) {

        String path = imageRepository.getCurrentImagePath().get();
        CustomImage modifiedImage = imageRepository.get(path);
        modifiedImage.modifyPixel(pixelX, pixelY, value);
        imageRepository.put(String.format(COPY_PATH, path, imageRepository.size()), modifiedImage);

        return modifiedImage;
    }

}
