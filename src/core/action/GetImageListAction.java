package core.action;

import core.repository.ImageRepository;

import java.util.List;

public class GetImageListAction {

    private ImageRepository imageRepository;

    public GetImageListAction(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public List<String> execute() {
        return imageRepository.getImages();
    }
}
