package presentation.presenter;

import core.action.filter.bilateral.ApplyBilateralFilterAction;
import core.action.image.GetImageAction;
import io.reactivex.subjects.PublishSubject;
import javafx.scene.image.Image;
import presentation.controller.BilateralFilterSceneController;

public class BilateralPresenter {

    private final BilateralFilterSceneController view;
    private final ApplyBilateralFilterAction applyBilateralFilterAction;
    private final GetImageAction getImageAction;
    private final PublishSubject<Image> imagePublishSubject;

    public BilateralPresenter(BilateralFilterSceneController view, ApplyBilateralFilterAction applyBilateralFilterAction, GetImageAction getImageAction, PublishSubject<Image> imagePublishSubject) {
        this.view = view;
        this.applyBilateralFilterAction = applyBilateralFilterAction;
        this.getImageAction = getImageAction;
        this.imagePublishSubject = imagePublishSubject;
    }

    public void onApply() {

        String closenessSigma = view.closenessSigmaTextField.getText();
        String similaritySigma = view.similaritySigmaTextField.getText();

        if(isValid(closenessSigma) && isValid(similaritySigma)) {

            this.getImageAction.execute().ifPresent(customImage -> {
                this.imagePublishSubject.onNext(applyBilateralFilterAction.execute(customImage, Double.parseDouble(closenessSigma), Double.parseDouble(similaritySigma)).toFXImage());
            });

        }

    }

    private boolean isValid(String sigma) {
        return (sigma != "") && (Double.parseDouble(sigma) > 0);
    }
}
