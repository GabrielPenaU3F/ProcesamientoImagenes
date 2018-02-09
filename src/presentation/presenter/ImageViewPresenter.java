package presentation.presenter;

import core.action.ModifyPixelAction;
import core.action.image.GetImageAction;
import core.action.image.GetModifiedImageAction;
import domain.CustomImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.util.Optional;

public class ImageViewPresenter {

    private GetImageAction getImageAction;
    private ModifyPixelAction modifyPixelAction;
    private GetModifiedImageAction getModifiedImageAction;

    public ImageViewPresenter(GetImageAction getImageAction, ModifyPixelAction modifyPixelAction1, GetModifiedImageAction getModifiedImageAction) {
        this.getImageAction = getImageAction;
        this.modifyPixelAction = modifyPixelAction1;
        this.getModifiedImageAction = getModifiedImageAction;
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

    private CustomImage getModifiedImage() {
        return getModifiedImageAction.execute();
    }

    public Integer getModifiedRGB(Integer pixelX, Integer pixelY) {
        return this.getModifiedImage().getPixelValue(pixelX, pixelY);
    }

    public Image modifyPixelValue(Integer pixelX, Integer pixelY, Double value) {
        return SwingFXUtils.toFXImage(modifyPixelAction.execute(pixelX, pixelY, value).getBufferedImage(), null);
    }
}
