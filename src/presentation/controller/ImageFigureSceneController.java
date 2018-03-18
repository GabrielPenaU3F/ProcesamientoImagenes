package presentation.controller;

import core.provider.PresenterProvider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import presentation.presenter.ImageFigurePresenter;
import presentation.util.InsertValuePopup;

public class ImageFigureSceneController {

    private final ImageFigurePresenter imageFigurePresenter;

    @FXML
    public ImageView imageView;

    public ImageFigureSceneController() {
        this.imageFigurePresenter = PresenterProvider.provideImageFigurePresenter();
    }

    @FXML
    public void initialize() {
        imageView.setPickOnBounds(true);
        imageView.setImage(imageFigurePresenter.createImageWithFigure(200, 200));
    }

    @FXML
    public void saveImageFigure(ActionEvent e) {
        imageFigurePresenter.saveImage(imageView.getImage(),
                InsertValuePopup.show("Insertar nombre", "defaultName").get());
    }
}
