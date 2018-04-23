package presentation.presenter;

import core.action.filter.ApplyFilterAction;
import core.action.image.GetImageAction;
import domain.filter.GuassianMask;
import domain.filter.Mask;
import domain.filter.MeanMask;
import domain.filter.MedianMask;
import presentation.controller.FilterSceneController;
import presentation.util.InsertSquareMatrixPopup;

import java.util.List;

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
        int size = 3;
        List<Integer> weights = InsertSquareMatrixPopup.show("Insert weighted matrix", 3).get();
        applyWithMask(new MedianMask(size, weights));
        view.closeWindow();
    }

    public void onApplyGaussianFilter() {
        int standardDesviation = Integer.parseInt(view.textField.getText());
        applyWithMask(new GuassianMask(standardDesviation));
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
