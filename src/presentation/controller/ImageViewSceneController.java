package presentation.controller;

import core.provider.PresenterProvider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import presentation.presenter.ImageViewPresenter;

public class ImageViewSceneController {

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
    @FXML
    public TextField pixelRValue;
    @FXML
    public TextField pixelGValue;
    @FXML
    public TextField pixelBValue;
    @FXML
    public TextField newPixelValue;
    @FXML
    public Label insertPixelValueLabel;
    @FXML
    public RadioButton originalRadio;
    @FXML
    public RadioButton modifiedRadio;

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
    }

    @FXML
    private void calculatePixelValue(ActionEvent e) {

        if (this.originalRadio.isSelected()) {

            if (this.validatePixelCoordinates()) {
                imageViewPresenter.getRGB(Integer.parseInt(pixelX.getText()), Integer.parseInt(pixelY.getText())).ifPresent(rgb -> pixelValue.setText(rgb.toString()));
            }

        } else if (this.modifiedRadio.isSelected()) {

            if (this.validateModifiedImage()) {
                if (this.validatePixelCoordinates()) {
                    pixelValue.setText(imageViewPresenter.getModifiedRGB(Integer.parseInt(pixelX.getText()), Integer.parseInt(pixelY.getText())).toString());
                }
            } else {
                insertPixelValueLabel.setText("Imagen erronea");
            }

        }
    }

    @FXML
    private void modifyPixelValue(ActionEvent e) {

        if (this.validatePixelCoordinates()) {

            Integer pixelX = Integer.parseInt(this.pixelX.getText());
            Integer pixelY = Integer.parseInt(this.pixelY.getText());

            if (this.validateNewValue()) {

                Double value = Double.parseDouble(newPixelValue.getText());
                this.imageViewPresenter.modifyPixelValue(pixelX, pixelY, value);

                Image modifiedFXImage = this.imageViewPresenter.getModifiedFXImage();
                modifiedImageView.setPickOnBounds(true);
                modifiedImageView.setOnMouseClicked(this::onPixelClick);
                modifiedImageView.setImage(modifiedFXImage);

            } else {
                insertPixelValueLabel.setText("Ingrese un valor");
            }

        } else {
            insertPixelValueLabel.setText("Seleccione pixel");
        }


    }

    private boolean validatePixelCoordinates() {
        return (!pixelX.getText().equals("") && !pixelY.getText().equals(""));
    }

    private boolean validateNewValue() {
        return !newPixelValue.getText().equals("");
    }

    //Verifica que haya cargada una imagen modificada
    private boolean validateModifiedImage() {
        return this.modifiedImageView.getImage() != null;
    }

}
