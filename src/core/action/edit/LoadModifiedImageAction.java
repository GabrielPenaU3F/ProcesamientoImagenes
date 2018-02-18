package core.action.edit;

import core.repository.ImageRepository;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.util.Optional;
import java.util.function.Supplier;

public class LoadModifiedImageAction {

    private ImageRepository imageRepository;

    public LoadModifiedImageAction(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Optional<Image> execute(Supplier<String> fileNameSupplier) {
        return imageRepository.getModifiedCurrentImagePath().map(customImage -> {
            imageRepository.put(fileNameSupplier.get(), customImage);
            return SwingFXUtils.toFXImage(customImage.getBufferedImage(), null);
        });
    }
}
