package presentation.controller;

import core.provider.PresenterProvider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import presentation.presenter.ImageViewPresenter;
import presentation.util.InsertValuePopup;

public class ImageViewSceneController {

    private static final String EMPTY = "";

    @FXML
    public ImageView imageView;
    @FXML
    public ImageView modifiedImageView;
    @FXML
    public TextField pixelX;
    @FXML
    public TextField pixelY;
    @FXML
    public TextField pixelValue;

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
        calculatePixelValue();
    }

    @FXML
    public void calculatePixelValue() {

        if (this.validatePixelCoordinates()) {

            int pixelX = Integer.parseInt(this.pixelX.getText());
            int pixelY = Integer.parseInt(this.pixelY.getText());

            imageViewPresenter.getRGB(pixelX, pixelY)
                    .ifPresent(rgb -> {
                        pixelValue.setText(rgb.toString());
                    });

        } else {
            pixelValue.setText("Error");
        }
    }

    @FXML
    private void modifyPixelValue(ActionEvent e) {

        if (this.validatePixelCoordinates()) {

            Integer pixelX = Integer.parseInt(this.pixelX.getText());
            Integer pixelY = Integer.parseInt(this.pixelY.getText());

            String newValue = InsertValuePopup.show("Insertar valor", "0").get();

            Image modifiedFXImage = imageViewPresenter.modifyPixelValue(pixelX, pixelY, newValue);

            modifiedImageView.setImage(modifiedFXImage);

        } else {
            pixelValue.setText("Seleccione pixel");
        }
    }

    private boolean validatePixelCoordinates() {
        return (!pixelX.getText().equals(EMPTY) && !pixelY.getText().equals(EMPTY));
    }

    @FXML
    public void saveModifiedImage(ActionEvent e) {
        modifiedImageView.setImage(null);
        imageViewPresenter.saveChanges()
                .ifPresent(image -> imageView.setImage(image));
    }
}
