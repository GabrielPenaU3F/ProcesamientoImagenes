package presentation.controller;

import core.provider.PresenterProvider;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import presentation.presenter.BilateralPresenter;

public class BilateralFilterSceneController {

    private final BilateralPresenter bilateralPresenter;

    @FXML
    public TextField closenessSigmaTextField;

    @FXML
    public TextField similaritySigmaTextField;

    @FXML
    public TextField maskSize;

    @FXML
    public ChoiceBox<String> imageSystemType;


    public BilateralFilterSceneController() {
        this.bilateralPresenter = PresenterProvider.provideBilateralPresenter(this);
    }

    @FXML
    public void initialize() {
        this.bilateralPresenter.onInitializeView();
    }

    @FXML
    public void onApply() {
        this.bilateralPresenter.onApply();
    }

    public String getClosenessSigmaTextField() {
        return closenessSigmaTextField.getText();
    }

    public String getSimilaritySigmaTextField() {
        return similaritySigmaTextField.getText();
    }

    public String getMaskSize() {
        return maskSize.getText();
    }

    public String getSystemTypeTextField() {
        return imageSystemType.getValue();
    }

    public void putImageSystemType(String name) {
        ObservableList<String> items = imageSystemType.getItems();
        items.add(name);
        imageSystemType.setItems(items);
    }

    public void putDefaultAsFirstImageSystemType() {
        imageSystemType.getSelectionModel().select(0);
    }

    public void closeWindow() {
        Stage stage = (Stage) this.similaritySigmaTextField.getScene().getWindow();
        stage.close();
    }
}
