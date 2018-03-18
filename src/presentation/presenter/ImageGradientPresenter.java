package presentation.presenter;

import core.action.gradient.CreateImageWithGradientAction;
import javafx.scene.image.Image;

public class ImageGradientPresenter {

    private CreateImageWithGradientAction createImageWithGradientAction;

    public ImageGradientPresenter(CreateImageWithGradientAction createImageWithGradientAction) {
        this.createImageWithGradientAction = createImageWithGradientAction;
    }

    public Image createGradient() {
        return this.createImageWithGradientAction.execute();
    }
}
