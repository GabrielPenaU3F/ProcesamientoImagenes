package core.action;

import core.repository.ImageRepository;

public class GetImageAction {

    ImageRepository imageRepository;

    public GetImageAction(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public String execute(String key) {
        return imageRepository.get(key);
    }
}
