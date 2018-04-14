package presentation.controller;

import core.provider.PresenterProvider;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import presentation.presenter.MediaFilterPresenter;

public class MediaFilterSceneController {

    private final MediaFilterPresenter mediaFilterPresenter;

    @FXML
    public TextField sizeTextField;

    public MediaFilterSceneController() {
        this.mediaFilterPresenter = PresenterProvider.provideMediaFilterPresenter(this);
    }

    @FXML
    public void apply() {
        this.mediaFilterPresenter.onApply();
    }

    public void closeWindow() {
        Stage stage = (Stage) this.sizeTextField.getScene().getWindow();
        stage.close();
    }
}
