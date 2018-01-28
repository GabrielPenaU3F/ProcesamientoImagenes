package core.action.currentimage;

import core.repository.ImageRepository;

public class SetCurrentImagePathAction {

    private ImageRepository imageRepository;

    public SetCurrentImagePathAction(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void execute(String currentImagePath) {
        this.imageRepository.put(currentImagePath);
    }
}
