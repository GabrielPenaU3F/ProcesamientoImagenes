package core.action;

import core.repository.ImageRepository;
import domain.CustomImage;

import java.awt.image.BufferedImage;

public class GetImageAction {

    ImageRepository imageRepository;

    public GetImageAction(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public CustomImage execute() {
        return imageRepository.get(imageRepository.getCurrentImagePath());
    }
}
