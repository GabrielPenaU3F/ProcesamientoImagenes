package presentation.presenter;

import core.action.filter.ApplyEdgeDetectorByGradientAction;
import core.action.image.GetImageAction;
import domain.customimage.CustomImage;
import domain.FilterSemaphore;
import domain.mask.*;

public class EdgeDetectorPresenter {

    private final GetImageAction getImageAction;
    private final ApplyEdgeDetectorByGradientAction applyEdgeDetectorByGradientAction;

    public EdgeDetectorPresenter(GetImageAction getImageAction,
                                 ApplyEdgeDetectorByGradientAction applyEdgeDetectorByGradientAction) {

        this.getImageAction = getImageAction;
        this.applyEdgeDetectorByGradientAction = applyEdgeDetectorByGradientAction;
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
        applyEdgeDetectorByGradientAction.execute(customImage, prewittXDerivativeMask, prewittYDerivativeMask);
    }

    private void applySobelEdgeDetector(CustomImage customImage) {
        Mask sobelXDerivativeMask = new SobelXDerivativeMask();
        Mask sobelYDerivativeMask = new SobelYDerivativeMask();
        applyEdgeDetectorByGradientAction.execute(customImage, sobelXDerivativeMask, sobelYDerivativeMask);
    }
}
