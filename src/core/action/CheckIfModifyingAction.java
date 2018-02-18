package core.action;

import core.repository.ImageRepository;

import java.util.List;

public class CheckIfModifyingAction {

    private ImageRepository imageRepository;

    public CheckIfModifyingAction(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public boolean execute() {

        if (this.imageRepository.getCurrentModifiedImage() != null) return true;
        else return false;

    }
}
