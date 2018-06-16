package presentation.controller;

import core.provider.PresenterProvider;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import presentation.presenter.CannyPresenter;

public class CannySceneController {

    @FXML
    public TextField sigmaTextField;
    @FXML
    public TextField t1TextField;
    @FXML
    public TextField t2TextField;

    private final CannyPresenter cannyPresenter;

    public CannySceneController() {
        this.cannyPresenter = PresenterProvider.provideCannyPresenter(this);
    }

    @FXML
    public void onApply() {
        this.cannyPresenter.onApply();
    }

    public void closeWindow() {
        Stage stage = (Stage) this.sigmaTextField.getScene().getWindow();
        stage.close();
    }

}
