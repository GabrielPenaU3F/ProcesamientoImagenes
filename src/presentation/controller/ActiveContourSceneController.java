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
    public Button getOutsideButton;
    @FXML
    public Button getInsideButton;
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
    }

    @FXML
    public void onApply() {
        this.activeContourPresenter.onApply();
    }

    @FXML
    public void onGetInside() {
        this.activeContourPresenter.onGetInsidePressed();
        this.getInsideButton.setDisable(true);
    }

    @FXML
    public void onGetOutside() {
        this.activeContourPresenter.onGetOutsidePressed();
        this.getOutsideButton.setDisable(true);
    }

    @FXML
    public void onResetContours() {
        this.activeContourPresenter.onInitializeContours();
        this.getInsideButton.setDisable(false);
        this.getOutsideButton.setDisable(false);
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
