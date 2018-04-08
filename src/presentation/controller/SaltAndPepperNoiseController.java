package presentation.controller;

import core.provider.PresenterProvider;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import presentation.presenter.SaltAndPepperNoisePresenter;

public class SaltAndPepperNoiseController {

    private final SaltAndPepperNoisePresenter saltAndPepperNoisePresenter;

    @FXML
    public TextField p0Field;
    @FXML
    public TextField p1Field;
    @FXML
    public TextField percentField;
    @FXML
    public Label p0ValidationLabel;
    @FXML
    public Label p1ValidationLabel;
    @FXML
    public Label percentValidationLabel;

    public SaltAndPepperNoiseController() {
        this.saltAndPepperNoisePresenter = PresenterProvider.provideSaltAndPepperNoisePresenter(this);
    }

    @FXML
    public void initialize() {
        this.saltAndPepperNoisePresenter.onInitializeView();
    }

    @FXML
    public void applyNoise() {
        this.saltAndPepperNoisePresenter.onApplyNoise();
    }

    public void closeWindow() {
        Stage stage = (Stage) this.p0Field.getScene().getWindow();
        stage.close();
    }
}
