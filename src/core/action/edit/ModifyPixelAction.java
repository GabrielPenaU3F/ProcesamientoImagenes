package core.action.edit;

import core.repository.ImageRepository;
import core.service.ModifyImageService;
import domain.customimage.CustomImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class ModifyPixelAction {

    private final ImageRepository imageRepository;
    private ModifyImageService modifyImageService;
    private String path;

    public ModifyPixelAction(ImageRepository imageRepository, ModifyImageService modifyImageService) {
        this.imageRepository = imageRepository;
        this.modifyImageService = modifyImageService;
    }

    public Image execute(Integer pixelX, Integer pixelY, String value) {

        CustomImage image;

        imageRepository.getCurrentImage().ifPresent(path -> this.path = path);

        if (!imageRepository.getCurrentModifiedImage().isPresent()) {
            image = imageRepository.get(path);
        } else {
            image = imageRepository.getCurrentModifiedImage().get();
        }

        CustomImage modifiedImage = modifyImageService.modify(image, pixelX, pixelY, Integer.parseInt(value));
        imageRepository.putCurrentModifiedImage(modifiedImage);

        return SwingFXUtils.toFXImage(modifiedImage.getBufferedImage(), null);
    }

}
