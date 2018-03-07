package presentation.controller;

import core.provider.PresenterProvider;
import io.reactivex.functions.Action;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import presentation.presenter.ImageViewPresenter;
import presentation.util.InsertValuePopup;
import presentation.view.CustomImageView;

public class ImageViewSceneController {

    private static final String EMPTY = "";

    @FXML
    public Group groupImageView;
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
    private CustomImageView customImageView;

    public ImageViewSceneController() {
        this.imageViewPresenter = PresenterProvider.provideImageViewPresenter();
    }

    @FXML
    //JavaFX invoke this method after constructor
    public void initialize() {
        this.imageViewPresenter.getFXImage().ifPresent(this::createCustomImageView);
    }

    private void createCustomImageView(Image image) {
        customImageView = new CustomImageView(this.groupImageView, this.imageView)
                .withImage(image)
                .withSetPickOnBounds(true)
                .withOnPixelClick(this::onPixelClick)
                .withSelectionMode();
    }

    private Action onPixelClick(Integer x, Integer y) {
        return () -> {
            pixelX.setText(x.toString());
            pixelY.setText(y.toString());
            calculatePixelValue();
        };
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
                .ifPresent(image -> customImageView.withImage(image));
    }

    @FXML
    public void cutPartialImage(ActionEvent e) {
        Image image = customImageView.cutPartialImage();
        modifiedImageView.setImage(image);
        imageViewPresenter.putModifiedImage(image);
    }
}
