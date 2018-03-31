package presentation.controller;

import core.provider.PresenterProvider;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import presentation.presenter.ContrastScenePresenter;

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

    }

    @FXML
    public void applyContrast() {
        this.contrastScenePresenter.onApplyContrast();
    }


}
