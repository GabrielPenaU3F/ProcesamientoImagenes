package presentation.controller;

import core.provider.PresenterProvider;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import presentation.presenter.ImageGradientPresenter;

public class ImageGradientSceneController {

    private final ImageGradientPresenter imageGradientPresenter;

    @FXML
    public ImageView imageView;

    public ImageGradientSceneController() {
        this.imageGradientPresenter = PresenterProvider.provideImageGradientPresenter();
    }

    @FXML
    public void initialize() {
        imageView.setPickOnBounds(true);
        imageView.setImage(this.imageGradientPresenter.createGradient());
    }
}
