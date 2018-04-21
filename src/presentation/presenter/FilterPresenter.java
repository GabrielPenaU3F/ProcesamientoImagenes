package presentation.presenter;

import core.action.filter.ApplyFilterAction;
import core.action.image.GetImageAction;
import domain.filter.Mask;
import domain.filter.MeanMask;
import domain.filter.MedianMask;
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

    public void onApplyMeanFilter() {
        int size = Integer.parseInt(view.sizeTextField.getText());
        if (sizeMaskMustBeAnOddInteger(size)) return;
        applyWithMask(new MeanMask(size));
    }

    public void onApplyMedianFilter() {
        int size = Integer.parseInt(view.sizeTextField.getText());
        applyWithMask(new MedianMask(size));
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
