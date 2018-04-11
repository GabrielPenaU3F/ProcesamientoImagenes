package presentation.controller;

import core.provider.PresenterProvider;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import presentation.presenter.random_generators.RayleighScenePresenter;

public class RayleighSceneController {

    private final RayleighScenePresenter rayleighScenePresenter;

    @FXML
    public TextField psiTextField;

    public RayleighSceneController() {
        this.rayleighScenePresenter = PresenterProvider.provideRayleighScenePresenter(this);
    }

    @FXML
    public void generate() {
        this.rayleighScenePresenter.onGenerate();
    }

    public void closeWindow() {
        Stage stage = (Stage) this.psiTextField.getScene().getWindow();
        stage.close();
    }
}
