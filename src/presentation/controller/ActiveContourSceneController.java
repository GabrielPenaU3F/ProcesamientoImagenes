package presentation.controller;

import core.action.edgedetector.activecontour.Corners;
import core.provider.PresenterProvider;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import presentation.presenter.ActiveContourPresenter;
import presentation.view.CustomImageView;

public class ActiveContourSceneController {

    @FXML
    public Group groupImageView;
    @FXML
    public ImageView imageView;
    @FXML
    public Button getBackgroundButton;
    @FXML
    public Button getObjectButton;
    @FXML
    public Button startButton;
    @FXML
    public Button applyButton;
    @FXML
    public TextField steps;

    private CustomImageView customImageView;

    private final ActiveContourPresenter activeContourPresenter;

    public ActiveContourSceneController() {
        this.activeContourPresenter = PresenterProvider.provideActiveContourPresenter(this);
    }

    @FXML
    public void initialize() {
        this.activeContourPresenter.initialize();
        this.applyButton.setDisable(true);
    }

    @FXML
    public void onStart() {
        this.activeContourPresenter.onStart();
        this.startButton.setDisable(true);
        this.applyButton.setDisable(false);
    }

    @FXML
    public void onApply() {
        this.activeContourPresenter.onApply();
    }

    @FXML
    public void onGetObject() {
        this.activeContourPresenter.onGetInsidePressed();
        this.getObjectButton.setDisable(true);
    }

    @FXML
    public void onGetBackground() {
        this.activeContourPresenter.onGetOutsidePressed();
        this.getBackgroundButton.setDisable(true);
    }

    @FXML
    public void onResetContours() {
        this.activeContourPresenter.onInitializeContours();
        this.getObjectButton.setDisable(false);
        this.getBackgroundButton.setDisable(false);
        this.steps.setText("0");
    }

    public void setInitializeCustomImageView(CustomImageView customImageView) {
        this.customImageView = customImageView;
    }

    public Corners getCorners() {
        return this.customImageView.getCorners();
    }

    public int getSteps() {
        return Integer.parseInt(steps.getText());
    }

    public void setImage(Image image) {
        this.customImageView.setImage(image);
    }

    public Image getPartialImage() {
        return this.customImageView.cutPartialImage();
    }
}
