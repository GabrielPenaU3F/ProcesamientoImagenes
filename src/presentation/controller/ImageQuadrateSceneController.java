package presentation.controller;

import core.provider.PresenterProvider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import presentation.presenter.ImageQuadratePresenter;

public class ImageQuadrateSceneController {

    private final ImageQuadratePresenter imageQuadratePresenter;

    @FXML
    public ImageView imageView;

    public Image imageWithQuadrate;

    public ImageQuadrateSceneController() {
        this.imageQuadratePresenter = PresenterProvider.provideImageQuadratePresenter();
    }

    @FXML
    public void initialize() {
        this.imageWithQuadrate = this.imageQuadratePresenter.createImageWithQuadrate(200, 200);
        imageView.setPickOnBounds(true);
        imageView.setImage(this.imageWithQuadrate);
    }

    @FXML
    public void saveQuadrateImage(ActionEvent e) {
        this.imageQuadratePresenter.saveImage(this.imageWithQuadrate, "image_with_quadrate");
    }
}
