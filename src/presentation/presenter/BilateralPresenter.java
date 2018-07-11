package presentation.presenter;

import core.action.filter.bilateral.ApplyBilateralFilterAction;
import presentation.controller.BilateralFilterSceneController;

public class BilateralPresenter {

    private final BilateralFilterSceneController view;
    private final ApplyBilateralFilterAction applyBilateralFilterAction;

    public BilateralPresenter(BilateralFilterSceneController view, ApplyBilateralFilterAction applyBilateralFilterAction) {
        this.view = view;
        this.applyBilateralFilterAction = applyBilateralFilterAction;
    }

    public void onApply() {

        if(isValid(view.closenessSigmaTextField.getText()) && isValid(view.similaritySigmaTextField.getText())) {

            //TODO: Apply

        }

    }

    private boolean isValid(String sigma) {
        return (sigma != "") && (Double.parseDouble(sigma) > 0);
    }
}
