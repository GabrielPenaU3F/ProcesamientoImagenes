package presentation.presenter;

import core.action.filter.ApplyPrewittFilterAction;
import core.action.filter.ApplyFilterAction;
import core.action.image.GetImageAction;
import domain.filter.mask.GaussianMask;
import domain.filter.mask.Mask;
import domain.filter.mask.MeanMask;
import domain.filter.mask.MedianMask;
import domain.filter.mask.WeightedMedianMask;
import presentation.controller.FilterSceneController;

public class FilterPresenter {

    private final FilterSceneController view;
    private final GetImageAction getImageAction;
    private final ApplyFilterAction applyFilterAction;
    private final ApplyPrewittFilterAction applyPrewittFilterAction;

    public FilterPresenter(FilterSceneController view,
                           GetImageAction getImageAction,
                           ApplyFilterAction applyFilterAction,
                           ApplyPrewittFilterAction applyPrewittFilterAction) {
        this.view = view;
        this.getImageAction = getImageAction;
        this.applyFilterAction = applyFilterAction;
        this.applyPrewittFilterAction = applyPrewittFilterAction;
    }

    public void onApplyMeanFilter() {
        int size = Integer.parseInt(view.textField.getText());
        if (sizeMaskMustBeAnOddInteger(size)) return;
        applyWithMask(new MeanMask(size));
        view.closeWindow();
    }

    public void onApplyMedianFilter() {
        int size = Integer.parseInt(view.textField.getText());
        applyWithMask(new MedianMask(size));
        view.closeWindow();
    }

    public void onApplyWeightedMedianFilter() {
        applyWithMask(new WeightedMedianMask());
        view.closeWindow();
    }

    public void onApplyGaussianFilter() {
        int standardDeviation = Integer.parseInt(view.textField.getText());
        applyWithMask(new GaussianMask(standardDeviation));
        view.closeWindow();
    }

    public void onApplyPrewittFilter() {
        this.getImageAction.execute()
                .ifPresent(customImage -> {
                    applyPrewittFilterAction.execute(customImage);
                    view.closeWindow();
                });
        view.closeWindow();
    }

    private void applyWithMask(Mask mask) {
        this.getImageAction.execute()
                .ifPresent(customImage -> {
                    applyFilterAction.execute(customImage, mask);
                    view.closeWindow();
                });
    }

    private boolean sizeMaskMustBeAnOddInteger(int size) {
        if (size % 2 == 0) {
            view.closeWindow();
            return true;
        }
        return false;
    }
}
