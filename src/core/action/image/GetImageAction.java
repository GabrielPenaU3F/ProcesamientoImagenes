package core.action.image;

import core.repository.ImageRepository;
import domain.customimage.CustomImage;

import java.util.Optional;

public class GetImageAction {

    ImageRepository imageRepository;

    public GetImageAction(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Optional<CustomImage> execute() {
        return imageRepository.getImage();
    }
}
