package presentation.presenter;

import core.action.filter.ApplyFilterAction;
import core.action.image.GetImageAction;
import domain.FilterSemaphore;
import domain.mask.*;
import presentation.controller.FilterSceneController;

public class FilterPresenter {

    private final FilterSceneController view;
    private final GetImageAction getImageAction;
    private final ApplyFilterAction applyFilterAction;

    public FilterPresenter(FilterSceneController view,
                           GetImageAction getImageAction,
                           ApplyFilterAction applyFilterAction) {
        this.view = view;
        this.getImageAction = getImageAction;
        this.applyFilterAction = applyFilterAction;
    }

    public void onApplyFilter() {
        if (FilterSemaphore.is(Mask.Type.MEAN)) {
            this.applyMeanFilter();
        }

        if (FilterSemaphore.is(Mask.Type.MEDIAN)) {
            this.applyMedianFilter();
        }

        if (FilterSemaphore.is(Mask.Type.WEIGHTED_MEDIAN)) {
            this.applyWeightedMedianFilter();
        }

        if (FilterSemaphore.is(Mask.Type.GAUSSIAN)) {
            this.applyGaussianFilter();
        }

        view.closeWindow();
    }

    private void applyMeanFilter() {
        int size = Integer.parseInt(view.textField.getText());
        if (sizeMaskMustBeAnOddInteger(size)) return;
        applyWithMask(new MeanMask(size));
    }

    private void applyMedianFilter() {
        int size = Integer.parseInt(view.textField.getText());
        applyWithMask(new MedianMask(size));
    }

    private void applyWeightedMedianFilter() {
        applyWithMask(new WeightedMedianMask());
    }

    private void applyGaussianFilter() {
        int standardDeviation = Integer.parseInt(view.textField.getText());
        applyWithMask(new GaussianMask(standardDeviation));
    }

    private void applyWithMask(Mask mask) {
        this.getImageAction.execute()
                .ifPresent(customImage -> {
                    applyFilterAction.execute(customImage, mask);
                    view.closeWindow();
                });
    }

    private boolean sizeMaskMustBeAnOddInteger(int size) {
        return size % 2 == 0;
    }
}
