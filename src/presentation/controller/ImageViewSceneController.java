package presentation.controller;

import core.provider.PresenterProvider;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import presentation.presenter.ImageViewPresenter;

public class ImageViewSceneController {

    @FXML
    public ImageView imageView;
    private ImageViewPresenter imageViewPresenter;

    public ImageViewSceneController() {
        this.imageViewPresenter = PresenterProvider.provideImageViewPresenter();
    }

    @FXML
    //JavaFX invoke this method after constructor
    public void initialize() {
        imageView.setImage(this.imageViewPresenter.getCurrentImage());
    }
}
