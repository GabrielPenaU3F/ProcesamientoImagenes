package presentation.presenter;

import core.action.edgedetector.ApplyEdgeDetectorByGradientAction;
import core.action.image.GetImageAction;
import domain.FilterSemaphore;
import domain.customimage.CustomImage;
import domain.mask.*;
import domain.mask.prewitt.PrewittXDerivativeMask;
import domain.mask.prewitt.PrewittYDerivativeMask;
import domain.mask.sobel.SobelXDerivativeMask;
import domain.mask.sobel.SobelYDerivativeMask;
import io.reactivex.subjects.PublishSubject;
import javafx.scene.image.Image;

public class EdgeDetectorPresenter {

    private final GetImageAction getImageAction;
    private final ApplyEdgeDetectorByGradientAction applyEdgeDetectorByGradientAction;
    private final PublishSubject<Image> imagePublishSubject;

    public EdgeDetectorPresenter(GetImageAction getImageAction,
                                 ApplyEdgeDetectorByGradientAction applyEdgeDetectorByGradientAction,
                                 PublishSubject<Image> imagePublishSubject) {

        this.getImageAction = getImageAction;
        this.applyEdgeDetectorByGradientAction = applyEdgeDetectorByGradientAction;
        this.imagePublishSubject = imagePublishSubject;
    }

    public void onInitialize() {
        this.getImageAction.execute()
                .ifPresent(customImage -> {
                    if (FilterSemaphore.is(Mask.Type.PREWITT)) {
                        this.applyPrewittEdgeDetector(customImage);
                    }

                    if (FilterSemaphore.is(Mask.Type.SOBEL)) {
                        this.applySobelEdgeDetector(customImage);
                    }
                });
    }

    private void applyPrewittEdgeDetector(CustomImage customImage) {
        Mask prewittXDerivativeMask = new PrewittXDerivativeMask();
        Mask prewittYDerivativeMask = new PrewittYDerivativeMask();
        Image edgedImage = applyEdgeDetectorByGradientAction.execute(customImage, prewittXDerivativeMask, prewittYDerivativeMask);
        imagePublishSubject.onNext(edgedImage);
    }

    private void applySobelEdgeDetector(CustomImage customImage) {
        Mask sobelXDerivativeMask = new SobelXDerivativeMask();
        Mask sobelYDerivativeMask = new SobelYDerivativeMask();
        Image edgedImage = applyEdgeDetectorByGradientAction.execute(customImage, sobelXDerivativeMask, sobelYDerivativeMask);
        imagePublishSubject.onNext(edgedImage);
    }
}
