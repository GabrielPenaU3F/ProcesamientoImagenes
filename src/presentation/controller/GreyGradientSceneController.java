package presentation.controller;

import core.provider.PresenterProvider;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import presentation.presenter.GreyGradientPresenter;

public class GreyGradientSceneController {

    private final GreyGradientPresenter greyGradientPresenter;

    @FXML
    public ImageView imageView;

    public GreyGradientSceneController() {
        this.greyGradientPresenter = PresenterProvider.provideGreyGradientPresenter();
    }

    @FXML
    public void initialize() {
        Image greyGradient = this.greyGradientPresenter.createGreyGradient();
        imageView.setPickOnBounds(true);
        imageView.setImage(greyGradient);
    }
}
