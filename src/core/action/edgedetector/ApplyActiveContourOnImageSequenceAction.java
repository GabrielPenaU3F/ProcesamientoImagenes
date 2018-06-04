package core.action.edgedetector;

import java.util.ArrayList;
import java.util.List;

import domain.activecontour.ActiveContour;
import domain.activecontour.ContourCustomImage;
import domain.customimage.CustomImage;

public class ApplyActiveContourOnImageSequenceAction {

    private final ApplyActiveContourAction applyActiveContourAction;

    public ApplyActiveContourOnImageSequenceAction(ApplyActiveContourAction applyActiveContourAction) {
        this.applyActiveContourAction = applyActiveContourAction;
    }

    public List<ContourCustomImage> execute(List<CustomImage> customImages, ActiveContour activeContour, int steps) {

        List<ContourCustomImage> contourCustomImages = new ArrayList<>();

        CustomImage first = customImages.get(0);
        ContourCustomImage contourCustomImage = applyActiveContourAction.execute(first, activeContour, steps);
        contourCustomImages.add(contourCustomImage);

        List<CustomImage> list = new ArrayList<>(customImages);
        list.remove(0);

        for (CustomImage customImage : list) {
            ActiveContour previousActiveContour = ActiveContour.copy(contourCustomImage.getActiveContour());
            contourCustomImage = applyActiveContourAction.execute(customImage, previousActiveContour, steps);
            contourCustomImages.add(contourCustomImage);
        }

        return contourCustomImages;
    }

}
