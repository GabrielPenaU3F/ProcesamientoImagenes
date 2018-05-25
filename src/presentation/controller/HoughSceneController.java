package presentation.controller;

import core.provider.PresenterProvider;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import presentation.presenter.HoughPresenter;

public class HoughSceneController {

    private final HoughPresenter houghPresenter;
    @FXML
    public RadioButton lineRadioButton;
    @FXML
    public RadioButton circleRadioButton;

    @FXML
    public TextField lineThetaTextField;
    @FXML
    public TextField lineRhoTextField;
    @FXML
    public TextField toleranceTextField;

    public HoughSceneController() {
        this.houghPresenter = PresenterProvider.provideHoughPresenter(this);
    }

    @FXML
    public void initialize() {
        this.disableCircleInput();
        this.disableLineInput();
    }

    private void enableLineInput() {
        this.lineRhoTextField.setDisable(false);
        this.lineThetaTextField.setDisable(false);
    }

    private void disableLineInput() {
        this.lineRhoTextField.setDisable(true);
        this.lineThetaTextField.setDisable(true);
    }

    private void enableCircleInput() {

    }

    private void disableCircleInput() {

    }

    @FXML
    public void onLineSelection() {
        this.enableLineInput();
        this.disableCircleInput();
    }

    @FXML
    public void onCircleSelection() {
        this.enableCircleInput();
        this.disableLineInput();
    }

    @FXML
    public void onApply() {
        this.houghPresenter.onApply();
    }

    public void closeWindow() {
        Stage stage = (Stage) this.lineThetaTextField.getScene().getWindow();
        stage.close();
    }
}
