package core.action;

import core.repository.ImageRepository;

public class SetCurrentImagePathOnRepoAction {

    private ImageRepository imageRepository;

    public SetCurrentImagePathOnRepoAction(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void execute(String currentImagePath) {
        this.imageRepository.put(currentImagePath);
    }
}
