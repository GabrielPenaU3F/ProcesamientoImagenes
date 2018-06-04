package presentation.controller;

import core.provider.PresenterProvider;
import domain.activecontour.ActiveContourMode;
import domain.activecontour.SelectionSquare;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
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
    public Button nextButton;
    @FXML
    public TextField steps;
    @FXML
    public Label steps_label;

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
    public void onStart() {
        this.activeContourPresenter.onStart();
    }

    public void enableApplyButton() {
        this.applyButton.setDisable(false);
    }

    public void disableApplyButton() {
        this.applyButton.setDisable(true);
    }

    public void enableStartButton() {
        this.startButton.setDisable(false);
    }

    public void disableStartButton() {
        this.startButton.setDisable(true);
    }

    public void disableNextButton() {
        this.nextButton.setDisable(true);
    }

    public void enableNextButton() {
        this.nextButton.setDisable(false);
    }

    @FXML
    public void onApply() {
        this.activeContourPresenter.onApply();
    }

    @FXML
    public void onNext() {
        this.activeContourPresenter.onNext();
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
        this.startButton.setDisable(false);
        disableApplyButton();
        this.steps.setText("0");
        this.steps_label.setText("Insert steps");
    }

    @FXML
    public void onFinish() {
        this.activeContourPresenter.onFinish();
    }

    public void setInitializeCustomImageView(CustomImageView customImageView) {
        this.customImageView = customImageView;
    }

    public SelectionSquare getSelectionSquare() {
        return this.customImageView.getSelectionSquare();
    }

    public int getSteps() {
        String stepsText = steps.getText();
        if (stepsText.trim().equals("")) {
            this.stepsMustBeGreaterThanZero();
            return 0;
        }
        return Integer.parseInt(stepsText);
    }

    public void setImage(Image image) {
        this.customImageView.setImage(image);
    }

    public Image getPartialImage() {
        return this.customImageView.cutPartialImage();
    }

    public void stepsMustBeGreaterThanZero() {
        this.steps_label.setText("Steps > 0");
    }

    public void mustSelectArea() {
        this.steps_label.setText("Must select area");
    }

    public void closeWindow() {
        Stage stage = (Stage) this.startButton.getScene().getWindow();
        stage.close();
    }
}
