package presentation.controller;

import core.provider.PresenterProvider;
import io.reactivex.functions.Action;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import presentation.presenter.MainPresenter;
import presentation.view.CustomImageView;

public class MainSceneController {

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

    public CustomImageView customImageView;

    private final MainPresenter mainPresenter;

    public MainSceneController() {
        this.mainPresenter = PresenterProvider.provideImageSelectionPresenter(this);
    }

    @FXML
    //JavaFX invoke this method after constructor
    public void initialize() {
        this.mainPresenter.initialize();
    }

    @FXML
    public void openImage(ActionEvent actionEvent) {
        this.mainPresenter.onOpenImage();
    }

    @FXML
    public void saveModifiedImage(ActionEvent actionEvent) {
        this.mainPresenter.onSaveImage();
    }

    @FXML
    public void showGreyGradient(ActionEvent event) {
        this.mainPresenter.onShowGreyGradient();
    }

    @FXML
    public void showColorGradient(ActionEvent event) {
        this.mainPresenter.onShowColorGradient();
    }

    @FXML
    public void showRGBImageRedChannel(ActionEvent event) {
        this.mainPresenter.onShowRGBImageRedChannel();
    }

    @FXML
    public void showRGBImageGreenChannel(ActionEvent event) {
        this.mainPresenter.onShowRGBImageGreenChannel();
    }

    @FXML
    public void showRGBImageBlueChannel(ActionEvent event) {
        this.mainPresenter.onShowRGBImageBlueChannel();
    }

    @FXML
    public void showImageWithQuadrate(ActionEvent actionEvent) {
        this.mainPresenter.onShowImageWithQuadrate();
    }

    @FXML
    public void showImageWithCircle(ActionEvent actionEvent) {
        this.mainPresenter.onShowImageWithCircle();
    }

    @FXML
    public void showHueHSVChannel(ActionEvent event) {
        this.mainPresenter.onShowHueHSVChannel();
    }

    @FXML
    public void showSaturationHSVChannel(ActionEvent event) {
        this.mainPresenter.onShowSaturationHSVChannel();
    }

    @FXML
    public void showValueHSVChannel(ActionEvent event) {
        this.mainPresenter.onShowValueHSVChannel();
    }

    @FXML
    public void calculatePixelValue() {
        this.mainPresenter.onCalculatePixelValue();
    }

    @FXML
    public void modifyPixelValue(ActionEvent actionEvent) {
        this.mainPresenter.onModifyPixelValue();
    }

    @FXML
    public void showReport(ActionEvent actionEvent) {
        this.mainPresenter.onShowReport();
    }

    @FXML
    public void cutPartialImage(ActionEvent actionEvent) {
        this.mainPresenter.onCutPartialImage();
    }

    @FXML
    public void close(ActionEvent actionEvent) {

    }
}
