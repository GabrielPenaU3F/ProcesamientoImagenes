package presentation.presenter;

import core.action.image.GetImageAction;
import domain.CustomImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.util.Optional;

public class ImageViewPresenter {

    private GetImageAction getImageAction;

    public ImageViewPresenter(GetImageAction getImageAction) {
        this.getImageAction = getImageAction;
    }

    private Optional<CustomImage> getCurrentImage() {
        return this.getImageAction.execute();
    }

    public Optional<Image> getFXImage() {
        return this.getCurrentImage().map(customImage -> {
            BufferedImage bufferedImage = customImage.getBufferedImage();
            return SwingFXUtils.toFXImage(bufferedImage, null);
        });
    }

    public Optional<Integer> getRGB(Integer pixelX, Integer pixelY) {
        return this.getCurrentImage().map(customImage -> customImage.getPixelValue(pixelX, pixelY));
    }

}
