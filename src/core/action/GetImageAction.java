package core.action;

import core.repository.ImageRepository;
import javafx.scene.image.Image;

public class GetImageAction {

    ImageRepository imageRepository;

    public GetImageAction(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image execute() {
        return imageRepository.get(imageRepository.getCurrentImagePath());
    }
}
