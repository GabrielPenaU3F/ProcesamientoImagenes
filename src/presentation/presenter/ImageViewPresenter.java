package presentation.presenter;

import core.action.GetImageAction;
import javafx.scene.image.Image;

public class ImageViewPresenter {

    private GetImageAction getImageAction;

    public ImageViewPresenter(GetImageAction getImageAction) {
        this.getImageAction = getImageAction;
    }

    public Image getCurrentImage() {
        return this.getImageAction.execute();
    }
}
