package presentation.controller;

import core.provider.PresenterProvider;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import presentation.presenter.space_domain_operations.GammaScenePresenter;


public class GammaSceneController {

    private final GammaScenePresenter gammaScenePresenter;
    @FXML
    public TextField gammaTextField;

    public GammaSceneController() {
        this.gammaScenePresenter = PresenterProvider.provideGammaScenePresenter(this);
    }

    @FXML
    public void applyFunction() {
        this.gammaScenePresenter.onApplyFunction();
    }

    public void closeWindow() {
        Stage stage = (Stage) this.gammaTextField.getScene().getWindow();
        stage.close();
    }
}
