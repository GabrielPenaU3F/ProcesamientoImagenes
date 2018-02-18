package core.action.image;

import core.repository.ImageRepository;
import domain.CustomImage;

import java.util.Optional;

public class GetModifiedImageAction {

    ImageRepository imageRepository;

    public GetModifiedImageAction(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public CustomImage execute() {
        return imageRepository.getCurrentModifiedImage();
    }

}
