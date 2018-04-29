package presentation.controller;

import core.provider.PresenterProvider;
import domain.FilterSemaphore;
import domain.mask.Mask;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import presentation.presenter.FilterPresenter;

public class FilterSceneController {

    private final FilterPresenter filterPresenter;

    @FXML
    public TextField textField;
    @FXML
    public Label label;

    public FilterSceneController() {
        this.filterPresenter = PresenterProvider.provideFilterPresenter(this);
    }

    @FXML
    public void initialize() {
        update();
    }

    public void update() {
        if (FilterSemaphore.is(Mask.Type.MEAN)) {
            label.setText("Enter size mask | Must be an odd integer");
        }

        if (FilterSemaphore.is(Mask.Type.MEDIAN)) {
            label.setText("Enter size mask");
        }

        if (FilterSemaphore.is(Mask.Type.WEIGHTED_MEDIAN)) {
            label.setText("Only 3x3 mask is available");
            textField.setDisable(true);
        }
    }

    @FXML
    public void apply() {
        this.filterPresenter.onApplyFilter();
    }

    public void closeWindow() {
        Stage stage = (Stage) this.textField.getScene().getWindow();
        stage.close();
    }
}
