
package presentation.controller;

import core.provider.PresenterProvider;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import presentation.presenter.ColorGradientPresenter;

public class ColorGradientSceneController {

    private final ColorGradientPresenter colorGradientPresenter;

    @FXML
    public ImageView imageView;

    public ColorGradientSceneController() {
        this.colorGradientPresenter = PresenterProvider.provideColorGradientPresenter();
    }

    @FXML
    public void initialize() {
        Image colorGradient = this.colorGradientPresenter.createColorGradient();
        imageView.setPickOnBounds(true);
        imageView.setImage(colorGradient);
    }

}
