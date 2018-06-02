package core.action.edgedetector;

import java.util.List;

import domain.activecontour.ActiveContour;
import domain.activecontour.ContourCustomImage;
import domain.customimage.CustomImage;
import io.reactivex.Observable;

public class ApplyActiveContourToImageSequenceAction {

    private final ApplyActiveContourAction applyActiveContourAction;

    public ApplyActiveContourToImageSequenceAction(ApplyActiveContourAction applyActiveContourAction) {
        this.applyActiveContourAction = applyActiveContourAction;
    }

    public Observable<CustomImage> execute(List<CustomImage> customImages, ActiveContour activeContour, int steps) {
        return Observable.fromIterable(customImages)
                         .map(customImage -> applyActiveContour(customImage, activeContour, steps))
                         .map(contourCustomImage -> {
                             CustomImage customImage = contourCustomImage.getCustomImage();
                             ActiveContour currentActiveContour = contourCustomImage.getActiveContour();
                             return applyActiveContour(customImage, currentActiveContour, steps);
                         })
                         .map(contourCustomImage -> contourCustomImage.getCustomImage());
    }

    private ContourCustomImage applyActiveContour(CustomImage customImage, ActiveContour activeContour, int steps) {
        return applyActiveContourAction.execute(customImage, activeContour, steps);
    }
}
