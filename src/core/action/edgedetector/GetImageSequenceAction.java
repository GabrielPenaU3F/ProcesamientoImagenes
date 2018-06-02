package core.action.edgedetector;

import java.util.List;
import java.util.Optional;

import core.repository.ImageRepository;
import domain.customimage.CustomImage;

public class GetImageSequenceAction {

    private final ImageRepository imageRepository;

    public GetImageSequenceAction(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Optional<List<CustomImage>> execute() {
        return imageRepository.getImageSequence();
    }
}
