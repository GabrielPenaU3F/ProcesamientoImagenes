package presentation.presenter;

import core.action.GetImageAction;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;

public class ImageViewPresenter {

    private GetImageAction getImageAction;

    public ImageViewPresenter(GetImageAction getImageAction) {
        this.getImageAction = getImageAction;
    }

    private BufferedImage getCurrentImage() {
        return this.getImageAction.execute();
    }

    public Image getFXImage() {
        BufferedImage bufferedImage = this.getCurrentImage();
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        return image;
    }
}
