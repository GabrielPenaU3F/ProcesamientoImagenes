package core.action;

import core.repository.ImageRepository;

public class GetCurrentImagePathAction {

    private ImageRepository imageRepository;

    public GetCurrentImagePathAction(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public String execute() {
        return this.imageRepository.getCurrentImagePath();
    }

}
