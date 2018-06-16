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

        switch(FilterSemaphore.getValue()) {

            case MEAN:
                label.setText("Enter size mask | Must be an odd integer");
                break;

            case MEDIAN:
                label.setText("Enter size mask");
                break;

            case WEIGHTED_MEDIAN:
                label.setText("Only 3x3 mask is available");
                textField.setDisable(true);
                break;

            case GAUSSIAN:
                label.setText("Enter sigma value");
                break;

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
