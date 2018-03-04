package core.action;

import core.repository.ImageRepository;
import domain.CustomImage;

public class PutModifiedImageAction {

    private ImageRepository imageRepository;

    public PutModifiedImageAction(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void execute(CustomImage customImage) {
        imageRepository.putCurrentModifiedImage(customImage);
    }
}
