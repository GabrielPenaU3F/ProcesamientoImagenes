package core.action.edgedetector;

import java.util.ArrayList;
import java.util.List;

import domain.activecontour.ActiveContour;
import domain.activecontour.ContourCustomImage;
import domain.activecontour.XYPoint;
import domain.customimage.CustomImage;

public class ApplyActiveContourAction {

    public ContourCustomImage execute(CustomImage customImage, ActiveContour activeContour, int steps) {
        return recursive(customImage, activeContour, steps);
    }

    private ContourCustomImage recursive(CustomImage customImage, ActiveContour activeContour, int steps) {

        ContourCustomImage contourCustomImage = applyActiveContour(customImage, activeContour);

        steps--;
        if (steps == 0) {
            return contourCustomImage;
        }

        return recursive(contourCustomImage.getCustomImage(), contourCustomImage.getActiveContour(), steps);
    }

    private ContourCustomImage applyActiveContour(CustomImage customImage, ActiveContour activeContour) {
        ActiveContour cycleOneContour = applyCycleOne(customImage, activeContour);
        return new ContourCustomImage(customImage, activeContour);
    }

    private ActiveContour applyCycleOne(CustomImage customImage, ActiveContour activeContour) {

        ActiveContour cycleOneContour = ActiveContour.copy(activeContour);

        // Step 1
        List<XYPoint> lOut = cycleOneContour.getlOut();
        List<XYPoint> lIn = cycleOneContour.getlIn();
        int backgroundGrayAverage = cycleOneContour.getBackgroundGrayAverage();
        int objectGrayAverage = cycleOneContour.getObjectGrayAverage();

        // Step 2
        switchIn(customImage, cycleOneContour, lOut, backgroundGrayAverage, objectGrayAverage);

        // Step 3
        cycleOneContour.moveInvalidLInToObject();

        // Step 4
        switchOut(customImage, cycleOneContour, lIn, backgroundGrayAverage, objectGrayAverage);

        // Step 5
        cycleOneContour.moveInvalidLOutToBackground();

        return cycleOneContour;
    }

    private void switchOut(CustomImage customImage, ActiveContour cycleOneContour, List<XYPoint> lIn, int backgroundGrayAverage, int objectGrayAverage) {
        List<XYPoint> addToLOut2 = new ArrayList<>();
        List<XYPoint> addToLIn2 = new ArrayList<>();
        List<XYPoint> toRemoveFromLIn2 = new ArrayList<>();

        for (XYPoint xyPoint : lIn) {

            if (checkFdFunction(xyPoint, customImage, backgroundGrayAverage, objectGrayAverage) < 0) {
                fillSwitchOutLists(cycleOneContour, addToLOut2, addToLIn2, toRemoveFromLIn2, xyPoint);
            }

        }

        cycleOneContour.removeLIn(toRemoveFromLIn2);
        cycleOneContour.addLOut(addToLOut2);
        cycleOneContour.addLIn(addToLIn2);
    }

    private void fillSwitchOutLists(ActiveContour cycleOneContour, List<XYPoint> addToLOut2, List<XYPoint> addToLIn2, List<XYPoint> toRemoveFromLIn2, XYPoint xyPoint) {
        switchOutStepOne(cycleOneContour, addToLOut2, toRemoveFromLIn2, xyPoint);
        switchOutStepTwo(cycleOneContour, addToLIn2, xyPoint);
    }

    private void switchOutStepTwo(ActiveContour cycleOneContour, List<XYPoint> addToLIn2, XYPoint xyPoint) {
        cycleOneContour.getNeighbors(xyPoint).stream()
                     .filter(neighbor -> cycleOneContour.hasValidPosition(neighbor.getX(), neighbor.getY()))
                     .filter(neighbor -> cycleOneContour.belongToObject(neighbor))
                     .forEach(neighbor -> {
                         addToLIn2.add(neighbor);
                         cycleOneContour.addLInToMatrix(neighbor);
                     });
    }

    private void switchOutStepOne(ActiveContour cycleOneContour, List<XYPoint> addToLOut2, List<XYPoint> toRemoveFromLIn2, XYPoint xyPoint) {
        toRemoveFromLIn2.add(xyPoint);
        addToLOut2.add(xyPoint);
        cycleOneContour.addLOutToMatrix(xyPoint);
    }

    private void switchIn(CustomImage customImage, ActiveContour cycleOneContour, List<XYPoint> lOut, int backgroundGrayAverage, int objectGrayAverage) {
        List<XYPoint> removeFromLOut = new ArrayList<>();
        List<XYPoint> addToLIn = new ArrayList<>();
        List<XYPoint> addToLOut = new ArrayList<>();

        for (XYPoint xyPoint : lOut) {

            if (checkFdFunction(xyPoint, customImage, backgroundGrayAverage, objectGrayAverage) > 0) {
                fillSwitchInLists(cycleOneContour, removeFromLOut, addToLIn, addToLOut, xyPoint);
            }
        }
        cycleOneContour.addLIn(addToLIn);
        cycleOneContour.removeLOut(removeFromLOut);
        cycleOneContour.addLOut(addToLOut);
    }

    private void fillSwitchInLists(ActiveContour cycleOneContour, List<XYPoint> removeFromLOut, List<XYPoint> addToLIn, List<XYPoint> addToLOut, XYPoint xyPoint) {
        switchInStepOne(cycleOneContour, removeFromLOut, addToLIn, xyPoint);
        switchInStepTwo(cycleOneContour, addToLOut, xyPoint);
    }

    private void switchInStepTwo(ActiveContour cycleOneContour, List<XYPoint> addToLOut, XYPoint xyPoint) {
        cycleOneContour.getNeighbors(xyPoint).stream()
                     .filter(neighbor -> cycleOneContour.hasValidPosition(neighbor.getX(), neighbor.getY()))
                     .filter(neighbor -> cycleOneContour.belongToBackground(neighbor))
                     .forEach(neighbor -> {
                         addToLOut.add(neighbor);
                         cycleOneContour.addLOutToMatrix(neighbor);
                     });
    }

    private void switchInStepOne(ActiveContour cycleOneContour, List<XYPoint> removeFromLOut, List<XYPoint> addToLIn, XYPoint xyPoint) {
        removeFromLOut.add(xyPoint);
        addToLIn.add(xyPoint);
        cycleOneContour.addLInToMatrix(xyPoint);
    }

    private double checkFdFunction(XYPoint xyPoint, CustomImage customImage, int backgroundGrayAverage, int objectGrayAverage) {
        int imageAverageValue = customImage.getAverageValue(xyPoint.getX(), xyPoint.getY());
        return Math.log(module(backgroundGrayAverage, imageAverageValue) / module(objectGrayAverage, imageAverageValue));
    }

    private double module(int value, int imageValue) {
        return Math.abs(value - imageValue);
    }
}
