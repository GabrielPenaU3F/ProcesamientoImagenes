package presentation.controller;

import core.provider.PresenterProvider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import presentation.presenter.ImageCirclePresenter;

public class ImageCircleSceneController {

    private final ImageCirclePresenter imageCirclePresenter;

    @FXML
    public ImageView imageView;

    public Image imageWithCircle;

    public ImageCircleSceneController() {
        this.imageCirclePresenter = PresenterProvider.provideImageCirclePresenter();
    }

    @FXML
    public void initialize() {
        this.imageWithCircle = this.imageCirclePresenter.createImageWithCircle(200, 200);
        imageView.setPickOnBounds(true);
        imageView.setImage(this.imageWithCircle);
    }

    @FXML
    public void saveCircleImage(ActionEvent e) {
        this.imageCirclePresenter.saveImage(this.imageWithCircle, "image_with_circle");
    }
}
