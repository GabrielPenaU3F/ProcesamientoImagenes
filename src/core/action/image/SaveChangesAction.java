package core.action.image;

import core.repository.ImageRepository;
import domain.CustomImage;

public class SaveChangesAction {

    public static final String COPY_PATH = "%s(%s)";
    ImageRepository imageRepository;

    public SaveChangesAction(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void execute() {
        CustomImage modifiedImage = imageRepository.getCurrentModifiedImage();
        imageRepository.setCurrentModifiedImage(null);//Remember that this variable has to be reset every time I save the changes, so the program knows if the user is currently modifying something or not
        String newPath = String.format(COPY_PATH, imageRepository.getCurrentImagePath(), imageRepository.size());
        imageRepository.put(newPath, modifiedImage);
        imageRepository.put(newPath);
    }
}
