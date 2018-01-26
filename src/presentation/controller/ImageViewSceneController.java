package presentation.controller;

import core.provider.PresenterProvider;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import presentation.presenter.ImageViewPresenter;

import java.awt.image.BufferedImage;

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

        Image image = this.imageViewPresenter.getFXImage();
        imageView.setImage(image);
    }
}
