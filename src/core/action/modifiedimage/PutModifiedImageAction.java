package core.action.modifiedimage;

import core.repository.ImageRepository;
import domain.customimage.CustomImage;

public class PutModifiedImageAction {

    private ImageRepository imageRepository;

    public PutModifiedImageAction(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void execute(CustomImage customImage) {
        imageRepository.saveModifiedImage(customImage);
    }
}
