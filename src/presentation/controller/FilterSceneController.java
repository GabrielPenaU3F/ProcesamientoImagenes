package presentation.controller;

import core.provider.PresenterProvider;
import domain.filter.FilterSemaphore;
import domain.filter.Mask;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import presentation.presenter.FilterPresenter;

public class FilterSceneController {

    private final FilterPresenter filterPresenter;

    @FXML
    public TextField sizeTextField;
    @FXML
    public Label labelSize;

    public FilterSceneController() {
        this.filterPresenter = PresenterProvider.provideFilterPresenter(this);
    }

    @FXML
    public void initialize() {
        update();
    }

    public void update() {
        if(FilterSemaphore.is(Mask.Type.MEAN)) {
            labelSize.setText("Enter size mask | Must be an odd integer");
        }

        if(FilterSemaphore.is(Mask.Type.MEDIAN)) {
            labelSize.setText("Enter size mask");
        }
    }

    @FXML
    public void apply() {
        if(FilterSemaphore.is(Mask.Type.MEAN)) {
            this.filterPresenter.onApplyMeanFilter();
        }

        if(FilterSemaphore.is(Mask.Type.MEDIAN)) {
            this.filterPresenter.onApplyMedianFilter();
        }
    }

    public void closeWindow() {
        Stage stage = (Stage) this.sizeTextField.getScene().getWindow();
        stage.close();
    }
}
