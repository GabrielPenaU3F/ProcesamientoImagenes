package core.action.modifiedimage;

import core.repository.ImageRepository;
import domain.CustomImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import java.util.Optional;

public class GetModifiedImageAction {

    private ImageRepository imageRepository;

    public GetModifiedImageAction(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Optional<Image> execute() {
        return imageRepository.getCurrentModifiedImage()
                .map(customImage -> customImage.getBufferedImage())
                .map(bufferedImage -> SwingFXUtils.toFXImage(bufferedImage, null));
    }
}
