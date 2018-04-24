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
        if(FilterSemaphore.is(Mask.Type.MEAN)) {
            label.setText("Enter size mask | Must be an odd integer");
        }

        if(FilterSemaphore.is(Mask.Type.MEDIAN)) {
            label.setText("Enter size mask");
        }

        if(FilterSemaphore.is(Mask.Type.WEIGHTED_MEAN)) {
            label.setText("Only 3x3 mask is available");
            textField.setDisable(true);
        }

        if(FilterSemaphore.is(Mask.Type.GAUSSIAN)) {
            label.setText("Enter standard deviation");
        }

        if(FilterSemaphore.is(Mask.Type.PREWITT)) {
            label.setText("Only 3x3 mask is available");
            textField.setDisable(true);
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

        if(FilterSemaphore.is(Mask.Type.WEIGHTED_MEAN)) {
            this.filterPresenter.onApplyWeightedMedianFilter();
        }

        if(FilterSemaphore.is(Mask.Type.GAUSSIAN)) {
            this.filterPresenter.onApplyGaussianFilter();
        }

        if(FilterSemaphore.is(Mask.Type.PREWITT)) {
            this.filterPresenter.onApplyPrewittFilter();
        }
    }

    public void closeWindow() {
        Stage stage = (Stage) this.textField.getScene().getWindow();
        stage.close();
    }
}
