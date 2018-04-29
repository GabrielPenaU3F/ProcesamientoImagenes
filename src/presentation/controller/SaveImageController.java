package presentation.controller;

import core.provider.PresenterProvider;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import presentation.presenter.SaveImagePresenter;

public class SaveImageController {

    @FXML
    public ListView<String> formatList;
    @FXML
    public TextField outputName;

    private final SaveImagePresenter saveImagePresenter;

    public SaveImageController() {
        this.saveImagePresenter = PresenterProvider.provideSaveImagePresenter(this);
    }

    @FXML
    public void initialize() {
        this.saveImagePresenter.initialize();
    }

    @FXML
    public void onSaveImage() {
        this.saveImagePresenter.saveImage();
    }

    public void closeWindow() {
        Stage stage = (Stage) this.outputName.getScene().getWindow();
        stage.close();
    }
}
