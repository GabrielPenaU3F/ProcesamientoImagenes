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
            imageView.setOnMouseClicked(this::onPixelClickInOriginalImage);
            imageView.setImage(image);
        });
    }

    private void onPixelClickInOriginalImage(MouseEvent e) {
        Integer mouseX = (int) e.getX();
        Integer mouseY = (int) e.getY();
        pixelX.setText(mouseX.toString());
        pixelY.setText(mouseY.toString());
        calculatePixelValueFromOriginalImage();
    }

    private void onPixelClickInModifiedImage(MouseEvent e) {
        Integer mouseX = (int) e.getX();
        Integer mouseY = (int) e.getY();
        pixelX.setText(mouseX.toString());
        pixelY.setText(mouseY.toString());
        calculatePixelValueFromModifiedImage();
    }

    @FXML
    public void calculatePixelValue() {

        if (imageViewPresenter.checkIfModifying()) this.calculatePixelValueFromModifiedImage();
        else this.calculatePixelValueFromOriginalImage();
        //If you are modifying, by default it'll calculate the values from the modified image

    }

    public void calculatePixelValueFromOriginalImage() {

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

    public void calculatePixelValueFromModifiedImage() {

        if (this.validatePixelCoordinates()) {

            int pixelX = Integer.parseInt(this.pixelX.getText());
            int pixelY = Integer.parseInt(this.pixelY.getText());

            Integer rgb = imageViewPresenter.getModifiedRGB(pixelX, pixelY);
            pixelValue.setText(rgb.toString());

        } else {
            pixelValue.setText("Error");
        }
    }

    @FXML
    public void modifyPixelValue(ActionEvent e) {

        if (this.validatePixelCoordinates()) {

            Integer pixelX = Integer.parseInt(this.pixelX.getText());
            Integer pixelY = Integer.parseInt(this.pixelY.getText());

            String newValue = InsertValuePopup.show("Insertar valor", "0").get();

            imageViewPresenter.modifyPixelValue(pixelX, pixelY, Double.parseDouble(newValue));
            modifiedImageView.setPickOnBounds(true);
            //Now you can also get pixel values from the modified image.
            modifiedImageView.setOnMouseClicked(this::onPixelClickInModifiedImage);
            modifiedImageView.setImage(imageViewPresenter.getFXModifiedFXImage());

        } else {
            pixelValue.setText("Seleccione pixel");
        }
    }

    @FXML
    public void saveChanges(ActionEvent e) {

        modifiedImageView.setImage(null);
        imageViewPresenter.saveChanges();
        this.imageViewPresenter.getFXImage().ifPresent(image -> imageView.setImage(image));
        //TODO: This should add the modified image to the observable image list on the other window. I don't know how to do that

    }

    private boolean validatePixelCoordinates() {
        return (!pixelX.getText().equals(EMPTY) && !pixelY.getText().equals(EMPTY));
    }
}
