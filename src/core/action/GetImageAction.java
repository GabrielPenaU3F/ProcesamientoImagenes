package core.action;

import core.repository.ImageRepository;

import java.awt.image.BufferedImage;

public class GetImageAction {

    ImageRepository imageRepository;

    public GetImageAction(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public BufferedImage execute() {
        return imageRepository.get(imageRepository.getCurrentImagePath());
    }
}
