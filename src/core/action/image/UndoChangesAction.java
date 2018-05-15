package core.action.image;

import core.repository.ImageRepository;
import domain.customimage.CustomImage;

public class UndoChangesAction {

    private final ImageRepository imageRepository;

    public UndoChangesAction(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public CustomImage execute() {
        CustomImage originalImage = imageRepository.getOriginalImageBackup();
        /*Creo una imagen nueva para evitar problemas con las referencias
        Ya que la buffered image nunca la modificamos, no hay problema con utilizarla
        */
        return imageRepository.saveImage(new CustomImage(originalImage.getBufferedImage(), originalImage.getFormatString()));
    }

}
