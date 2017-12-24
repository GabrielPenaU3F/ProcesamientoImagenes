package core.action;

import core.repository.ImageRepository;

public class SaveImageAction {

    private ImageRepository imageRepository;

    public SaveImageAction(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void execute(String key, String value) {
        //TODO: FileChooserService open a dialog and choose the image
        imageRepository.put(key, value);
    }
}
