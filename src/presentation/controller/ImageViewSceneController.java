package presentation.controller;

import core.provider.PresenterProvider;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import presentation.presenter.ImageViewPresenter;

public class ImageViewSceneController {

    @FXML
    public ImageView imageView;
    @FXML
    public Pane imageViewPane;
    @FXML
    public TextField pixelX;
    @FXML
    public TextField pixelY;
    @FXML
    public TextField pixelValue;
    @FXML
    public TextField pixelRValue;
    @FXML
    public TextField pixelGValue;
    @FXML
    public TextField pixelBValue;

    private ImageViewPresenter imageViewPresenter;

    public ImageViewSceneController() {
        this.imageViewPresenter = PresenterProvider.provideImageViewPresenter();
    }

    @FXML
    //JavaFX invoke this method after constructor
    public void initialize() {
        this.imageViewPresenter.getFXImage().ifPresent(image -> {
            imageView.setPickOnBounds(true);
            imageView.setOnMouseClicked(this::onPixelClick);
            imageView.setImage(image);
        });
    }

    private void onPixelClick(MouseEvent e) {
        Integer mouseX = (int) e.getX();
        Integer mouseY = (int) e.getY();
        pixelX.setText(mouseX.toString());
        pixelY.setText(mouseY.toString());
        imageViewPresenter.getRGB(mouseX, mouseY).ifPresent(rgb -> pixelValue.setText(rgb.toString()));
    }

}
