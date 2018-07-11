package presentation.controller;

import core.provider.PresenterProvider;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import presentation.presenter.BilateralPresenter;

public class BilateralFilterSceneController {

    private final BilateralPresenter bilateralPresenter;

    @FXML
    public TextField closenessSigmaTextField;

    @FXML
    public TextField similaritySigmaTextField;


    public BilateralFilterSceneController() {
        this.bilateralPresenter = PresenterProvider.provideBilateralPresenter(this);
    }

    @FXML
    public void onApply() {
        this.bilateralPresenter.onApply();
    }



}
