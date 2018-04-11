package presentation.controller;

import core.provider.PresenterProvider;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import presentation.presenter.random_generators.GaussianScenePresenter;

public class GaussianSceneController {

    private final GaussianScenePresenter gaussianScenePresenter;

    @FXML
    public TextField muTextField;

    @FXML
    public TextField sigmaTextField;

    public GaussianSceneController() {
        this.gaussianScenePresenter = PresenterProvider.provideGaussianScenePresenter(this);
    }

    @FXML
    public void generate() {
        this.gaussianScenePresenter.onGenerate();
    }

    public void closeWindow() {
        Stage stage = (Stage) this.muTextField.getScene().getWindow();
        stage.close();
    }

}
