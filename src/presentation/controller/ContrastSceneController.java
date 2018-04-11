package presentation.controller;

import core.provider.PresenterProvider;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import presentation.presenter.space_domain_operations.ContrastScenePresenter;

public class ContrastSceneController {

    private final ContrastScenePresenter contrastScenePresenter;

    @FXML
    public TextField s1Field;
    @FXML
    public TextField s2Field;
    @FXML
    public Label s1ValidationLabel;
    @FXML
    public Label s2ValidationLabel;

    public ContrastSceneController() {
        this.contrastScenePresenter = PresenterProvider.provideContrastScenePresenter(this);
    }

    @FXML
    public void initialize() {
        this.contrastScenePresenter.onInitializeView();
    }

    @FXML
    public void applyContrast() {
        this.contrastScenePresenter.onApplyContrast();
    }

    public void closeWindow() {
        Stage stage = (Stage) this.s1Field.getScene().getWindow();
        stage.close();
    }
}
