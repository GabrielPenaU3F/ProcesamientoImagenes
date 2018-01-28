package presentation.presenter;

import core.action.GetImageAction;
import domain.CustomImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;

public class ImageViewPresenter {

    private GetImageAction getImageAction;

    public ImageViewPresenter(GetImageAction getImageAction) {
        this.getImageAction = getImageAction;
    }

    private CustomImage getCurrentImage() {
        return this.getImageAction.execute();
    }

    public Image getFXImage() {
        BufferedImage bufferedImage = this.getCurrentImage().getBufferedImage();
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        return image;
    }

    public Integer getRGB(Integer pixelX, Integer pixelY) {
        return this.getCurrentImage().getRGB(pixelX, pixelY);
    }

}
