package presentation.presenter;

import core.action.edgedetector.ApplyCannyDetectorAction;
import core.action.filter.ApplyFilterAction;
import core.action.image.GetImageAction;
import domain.customimage.CustomImage;
import domain.mask.filter.GaussianMask;
import io.reactivex.subjects.PublishSubject;
import javafx.scene.image.Image;
import presentation.controller.CannySceneController;

public class CannyPresenter {

    private final CannySceneController view;
    private final GetImageAction getImageAction;
    private final ApplyFilterAction applyFilterAction;
    private final PublishSubject<Image> imagePublishSubject;
    private final ApplyCannyDetectorAction applyCannyDetectorAction;
    private final PublishSubject<Image> cannyPublishSubject;

    public CannyPresenter(CannySceneController view, GetImageAction getImageAction, ApplyFilterAction applyFilterAction,
            PublishSubject<Image> imagePublishSubject, ApplyCannyDetectorAction applyCannyDetectorAction, PublishSubject<Image> cannyPublishSubject) {
        this.view = view;
        this.getImageAction = getImageAction;
        this.applyFilterAction = applyFilterAction;
        this.imagePublishSubject = imagePublishSubject;
        this.applyCannyDetectorAction = applyCannyDetectorAction;
        this.cannyPublishSubject = cannyPublishSubject;
    }

    public void onApply() {

        int sigma = Integer.parseInt(view.sigmaTextField.getText());
        int t1 = Integer.parseInt(view.t1TextField.getText());
        int t2 = Integer.parseInt(view.t2TextField.getText());

        if (isSigmaValid(sigma)) {

            if (areThresholdsValid(t1, t2)) {

                this.getImageAction.execute().ifPresent(customImage -> {

                            CustomImage filteredImage = this.applyFilterAction.execute(customImage, new GaussianMask(sigma));
                            Image canniedImage = this.applyCannyDetectorAction.execute(filteredImage, t1, t2).toFXImage();
                            //NO invertir el orden, o rompe Hough (deberiamos buscar una manera de fixear esto)
                            this.imagePublishSubject.onNext(canniedImage);
                            this.cannyPublishSubject.onNext(canniedImage);

                            this.view.closeWindow();
                        }
                );
            }
        }
    }

    private boolean areThresholdsValid(int t1, int t2) {
        if (t1 < 0 || t2 < 0) {
            return false;
        }
        if (t1 > t2) {
            return false;
        }
        return true;
    }

    private boolean isSigmaValid(double sigma) {
        return sigma > 0;
    }

}
