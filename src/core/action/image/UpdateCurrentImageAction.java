package core.action.image;

import core.repository.ImageRepository;
import domain.customimage.CustomImage;


public class UpdateCurrentImageAction {

    private final ImageRepository imageRepository;

    public UpdateCurrentImageAction(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void execute(CustomImage customImage) {
        this.imageRepository.saveImage(customImage);
    }
}
