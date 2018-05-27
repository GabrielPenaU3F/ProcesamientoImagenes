package core.action.edgedetector.activecontour;

import java.util.ArrayList;
import java.util.List;

import domain.customimage.CustomImage;

public class ApplyActiveContourAction {

    public CustomImage execute(CustomImage customImage, Corners corners, int backgroundGrayAverage, int steps) {
        int objectGrayAverage = getObjectGrayAverage(customImage, corners);
        Integer width = customImage.getWidth();
        Integer height = customImage.getHeight();
        ActiveContour activeContour = new ActiveContour(width, height, corners, backgroundGrayAverage, objectGrayAverage);

        return recursive(customImage, activeContour, steps);
    }

    private int getObjectGrayAverage(CustomImage customImage, Corners corners) {
        int value = 0;
        for (int i = corners.getFirstRow() + 2; i <= corners.getSecondRow() - 2; i++) {
            for (int j = corners.getFirstColumn() + 2; j <= corners.getSecondColumn() - 2; j++) {
                value += customImage.getAverageValue(i, j);
            }
        }
        return value / (customImage.getWidth() * customImage.getHeight());
    }

    private CustomImage recursive(CustomImage customImage, ActiveContour activeContour, int steps) {

        ContourCustomImage contourCustomImage = applyActiveContour(customImage, activeContour);

        steps--;
        if (steps == 0) {
            return contourCustomImage.drawActiveContour();
        }

        return recursive(contourCustomImage.getCustomImage(), contourCustomImage.getActiveContour(), steps);
    }

    private ContourCustomImage applyActiveContour(CustomImage customImage, ActiveContour activeContour) {

        // Step 1
        List<XYPoint> lOut = activeContour.getlOut();
        List<XYPoint> lIn = activeContour.getlIn();

        List<XYPoint> addToLOut = new ArrayList<>();
        List<XYPoint> removeFromLOut = new ArrayList<>();
        List<XYPoint> addToLIn = new ArrayList<>();

        int backgroundGrayAverage = activeContour.getBackgroundGrayAverage();
        int objectGrayAverage = activeContour.getObjectGrayAverage();

        // Step 2
        for (XYPoint xyPoint : lOut) {

            if (checkFdFunction(xyPoint, customImage, backgroundGrayAverage, objectGrayAverage) > 0) {
                removeFromLOut.add(xyPoint);
                addToLIn.add(xyPoint);
                activeContour.addLInToMatrix(xyPoint);

                activeContour.getBackgroundNeighbors(xyPoint)
                             .stream()
                             .filter(neighbor -> activeContour.hasValidPosition(neighbor.getX(), neighbor.getY()))
                             .forEach(neighbor -> {
                                 addToLOut.add(neighbor);
                                 activeContour.addLOutToMatrix(neighbor);
                             });
            }
        }

        activeContour.addLIn(addToLIn);
        activeContour.removeLOut(removeFromLOut);
        activeContour.addLOut(addToLOut);

        // Step 3
        activeContour.moveInvalidLInToObject();

        List<XYPoint> addToLOut2 = new ArrayList<>();
        List<XYPoint> addToLIn2 = new ArrayList<>();
        List<XYPoint> toRemoveFromLIn2 = new ArrayList<>();

        // Step 4
        for (XYPoint xyPoint : lIn) {

            if (checkFdFunction(xyPoint, customImage, backgroundGrayAverage, objectGrayAverage) < 0) {
                toRemoveFromLIn2.add(xyPoint);
                addToLOut2.add(xyPoint);
                activeContour.addLOutToMatrix(xyPoint);

                activeContour.getObjectNeighbors(xyPoint)
                             .forEach(neighbor -> {
                                 addToLIn2.add(neighbor);
                                 activeContour.addLInToMatrix(neighbor);
                             });
            }
        }

        activeContour.addLOut(addToLOut2);
        activeContour.removeLIn(toRemoveFromLIn2);
        activeContour.addLIn(addToLIn2);

        // Step 5
        activeContour.moveInvalidLOutToBackground();

        return new ContourCustomImage(customImage, activeContour);
    }

    private double checkFdFunction(XYPoint xyPoint, CustomImage customImage, int backgroundGrayAverage, int objectGrayAverage) {
        int imageAverageValue = customImage.getAverageValue(xyPoint.getX(), xyPoint.getY());
        return Math.log(module(backgroundGrayAverage, imageAverageValue) / module(objectGrayAverage, imageAverageValue));
    }

    private double module(int value, int imageValue) {
        return Math.sqrt(Math.pow(value - imageValue, 2));
    }
}
