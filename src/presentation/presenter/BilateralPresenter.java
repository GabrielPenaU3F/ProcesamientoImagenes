package presentation.presenter;

import static domain.customimage.CustomImage.SystemType;

import core.action.filter.bilateral.ApplyBilateralFilterAction;
import core.action.image.GetImageAction;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.subjects.PublishSubject;
import javafx.scene.image.Image;
import presentation.controller.BilateralFilterSceneController;
import presentation.util.ShowResultPopup;

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

    public void onCalculateRecommendedMaskValue(){
        String closenessSigma = "";
        closenessSigma = view.getClosenessSigmaTextField();
        String similaritySigma = "";
        similaritySigma = view.getSimilaritySigmaTextField();
        if (isValidSigma(closenessSigma) && isValidSigma(similaritySigma)){
            int closenessSigma1 = Integer.parseInt(closenessSigma);
            int similaritySigma1 = Integer.parseInt(similaritySigma);
            int recommendedMaskSize = (2 * ((int) (closenessSigma1 + similaritySigma1) / 2)) + 1; //2 * this.getMeanSigma(closenessSigma, similaritySigma) + 1;
            ShowResultPopup.show("Recommended Mask Size", String.valueOf(recommendedMaskSize));
        }

    }

    public void onApply() {
        String closenessSigma = view.getClosenessSigmaTextField();
        String similaritySigma = view.getSimilaritySigmaTextField();
        String systemType = view.getSystemTypeTextField();
        String maskSize = view.getMaskSize();


        if (isValidSigma(closenessSigma) && isValidSigma(similaritySigma) && isValidMaskSize(maskSize)) {

            this.getImageAction.execute().ifPresent(customImage -> {
                double closenessSigma1 = Double.parseDouble(closenessSigma);
                double similaritySigma1 = Double.parseDouble(similaritySigma);
                int maskSize1 = Integer.parseInt(maskSize);
                SystemType imageSystemType = SystemType.valueOf(systemType);
                Image image = applyBilateralFilterAction.execute(customImage, closenessSigma1, similaritySigma1, maskSize1, imageSystemType).toFXImage();
                this.imagePublishSubject.onNext(image);
            });
        }

        view.closeWindow();
    }

    private boolean isValidSigma(String sigma) {
        return (sigma != "") && (Double.parseDouble(sigma) > 0);
    }

    private boolean isValidMaskSize(String maskSize) {

        return (maskSize != "") && (Integer.parseInt(maskSize) > 0) && (Integer.parseInt(maskSize) % 2) != 0 ;
    }

    public void onInitializeView() {
        view.putImageSystemType(SystemType.LAB.name());
        view.putImageSystemType(SystemType.RGB.name());
        view.putDefaultAsFirstImageSystemType();
    }
}
