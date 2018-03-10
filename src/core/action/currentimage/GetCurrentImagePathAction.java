package core.action.currentimage;

import core.repository.ImageRepository;

import java.util.Optional;

public class GetCurrentImagePathAction {

    private ImageRepository imageRepository;

    public GetCurrentImagePathAction(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Optional<String> execute() {
        return this.imageRepository.getCurrentImage();
    }

}
