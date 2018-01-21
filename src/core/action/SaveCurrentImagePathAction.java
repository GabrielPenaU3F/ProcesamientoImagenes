package core.action;

import core.repository.ImageRepository;

public class SaveCurrentImagePathAction {

    private ImageRepository imageRepository;

    public SaveCurrentImagePathAction(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void execute(String currentImagePath) {
        this.imageRepository.put(currentImagePath);
    }
}
