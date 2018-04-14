package presentation.controller;

import core.provider.PresenterProvider;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import presentation.presenter.random_generators.ExponentialScenePresenter;

public class ExponentialSceneController {


    private final ExponentialScenePresenter exponentialScenePresenter;

    @FXML
    public TextField lambdaTextField;

    public ExponentialSceneController() {
        this.exponentialScenePresenter = PresenterProvider.provideExponentialScenePresenter(this);
    }

    @FXML
    public void generate() {
        this.exponentialScenePresenter.onGenerate();
    }

    public void closeWindow() {
        Stage stage = (Stage) this.lambdaTextField.getScene().getWindow();
        stage.close();
    }
}
