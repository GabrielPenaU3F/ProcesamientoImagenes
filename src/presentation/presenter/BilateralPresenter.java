package presentation.presenter;

import static domain.customimage.CustomImage.SystemType;

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

    public BilateralPresenter(BilateralFilterSceneController view, ApplyBilateralFilterAction applyBilateralFilterAction,
            GetImageAction getImageAction, PublishSubject<Image> imagePublishSubject) {
        this.view = view;
        this.applyBilateralFilterAction = applyBilateralFilterAction;
        this.getImageAction = getImageAction;
        this.imagePublishSubject = imagePublishSubject;
    }

    public void onApply() {

        String closenessSigma = view.getClosenessSigmaTextField();
        String similaritySigma = view.getSimilaritySigmaTextField();
        String systemType = view.getSystemTypeTextField();

        if (isValid(closenessSigma) && isValid(similaritySigma)) {

            this.getImageAction.execute().ifPresent(customImage -> {
                double closenessSigma1 = Double.parseDouble(closenessSigma);
                double similaritySigma1 = Double.parseDouble(similaritySigma);
                SystemType imageSystemType = SystemType.valueOf(systemType);
                Image image = applyBilateralFilterAction.execute(customImage, closenessSigma1, similaritySigma1, imageSystemType).toFXImage();
                this.imagePublishSubject.onNext(image);
            });
        }
    }

    private boolean isValid(String sigma) {
        return (sigma != "") && (Double.parseDouble(sigma) > 0);
    }

    public void onInitializeView() {
        view.putImageSystemType(SystemType.LAB.name());
        view.putImageSystemType(SystemType.RGB.name());
        view.putDefaultAsFirstImageSystemType();
    }
}
