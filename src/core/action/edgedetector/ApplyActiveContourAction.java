package core.action.edgedetector;

import java.util.ArrayList;
import java.util.List;

import domain.activecontour.ActiveContour;
import domain.activecontour.ActiveContourMode;
import domain.activecontour.ContourCustomImage;
import domain.activecontour.FdFunction;
import domain.activecontour.FdFunctionMode;
import domain.activecontour.XYPoint;
import domain.customimage.CustomImage;
import domain.mask.filter.GaussianMask;

public class ApplyActiveContourAction {

    public ContourCustomImage execute(CustomImage customImage, ActiveContour activeContour, int steps, double epsilon) {
        return recursive(customImage, activeContour, steps, epsilon);
    }

    private ContourCustomImage recursive(CustomImage customImage, ActiveContour activeContour, int steps, double epsilon) {

        ContourCustomImage contourCustomImage = new ContourCustomImage(customImage, activeContour);

        ActiveContour cycleOneContour = contourCustomImage.getActiveContour();
        if (objectHasBeenFound(customImage, cycleOneContour, epsilon)) {
            // Fixme cannot apply cycle two on image sequence
            if(ActiveContourMode.isSingle()) {
                cycleOneContour = applyCycleTwo(cycleOneContour);
            }
            return new ContourCustomImage(customImage, cycleOneContour);
        } else if (steps == 0) {
            return contourCustomImage;
        } else {
            contourCustomImage = applyActiveContour(contourCustomImage.getCustomImage(), contourCustomImage.getActiveContour(), epsilon);
        }

        steps--;
        return recursive(contourCustomImage.getCustomImage(), contourCustomImage.getActiveContour(), steps, epsilon);
    }

    private boolean objectHasBeenFound(CustomImage image, ActiveContour activeContour, double epsilon) {

        int backgroundGrayAverage = activeContour.getBackgroundGrayAverage();
        int objectGrayAverage = activeContour.getObjectGrayAverage();

        for (XYPoint lOutPoint : activeContour.getlOut()) {
            if (!checkFdFunctionIsLowerThanEpsilon(lOutPoint, image, backgroundGrayAverage, objectGrayAverage, epsilon)) {
                return false;
            }
        }

        for (XYPoint lInPoint : activeContour.getlIn()) {
            if (checkFdFunctionIsLowerThanEpsilon(lInPoint, image, backgroundGrayAverage, objectGrayAverage, epsilon)) {
                return false;
            }
        }

        return true;
    }

    private ContourCustomImage applyActiveContour(CustomImage customImage, ActiveContour activeContour, double epsilon) {
        ActiveContour cycleOneContour = applyCycleOne(customImage, activeContour, epsilon);
        return new ContourCustomImage(customImage, cycleOneContour);
    }

    private ActiveContour applyCycleOne(CustomImage customImage, ActiveContour activeContour, double epsilon) {

        ActiveContour cycleOneContour = ActiveContour.copy(activeContour);

        // Step 1
        List<XYPoint> lOut = cycleOneContour.getlOut();
        List<XYPoint> lIn = cycleOneContour.getlIn();
        int backgroundGrayAverage = cycleOneContour.getBackgroundGrayAverage();
        int objectGrayAverage = cycleOneContour.getObjectGrayAverage();

        // Step 2
        switchIn(customImage, cycleOneContour, lOut, backgroundGrayAverage, objectGrayAverage, epsilon);

        // Step 3
        cycleOneContour.moveInvalidLInToObject();

        // Step 4
        switchOut(customImage, cycleOneContour, lIn, backgroundGrayAverage, objectGrayAverage, epsilon);

        // Step 5
        cycleOneContour.moveInvalidLOutToBackground();

        return cycleOneContour;
    }

    private ActiveContour applyCycleTwo(ActiveContour cycleOneContour) {

        ActiveContour cycleTwoContour = ActiveContour.copy(cycleOneContour);

        GaussianMask gaussianMask = new GaussianMask(1);

        // Step 0
        List<XYPoint> lOut = cycleTwoContour.getlOut();
        List<XYPoint> lIn = cycleTwoContour.getlIn();

        //Step 1
        cycleTwoSwitchIn(cycleTwoContour, lOut);

        // Step 2
        cycleTwoContour.moveInvalidLInToObject();

        //Step 3
        cycleTwoSwitchOut(cycleTwoContour, lIn);

        //Step 4
        cycleTwoContour.moveInvalidLOutToBackground();

        return cycleTwoContour;
    }

    private void cycleTwoSwitchIn(ActiveContour cycleTwoContour, List<XYPoint> lOut) {

        List<XYPoint> removeFromLOut = new ArrayList<>();
        List<XYPoint> addToLIn = new ArrayList<>();
        List<XYPoint> addToLOut = new ArrayList<>();

        for (XYPoint xyPoint : lOut) {

            int x = xyPoint.getX();
            int y = xyPoint.getY();
            double newFiValue = new GaussianMask(3).applyMaskToPixel(cycleTwoContour.getContent(), x, y);
            if (newFiValue < 0) {
                fillSwitchInLists(cycleTwoContour, removeFromLOut, addToLIn, addToLOut, xyPoint);
            }
        }

        cycleTwoContour.addLIn(addToLIn);
        cycleTwoContour.removeLOut(removeFromLOut);
        cycleTwoContour.addLOut(addToLOut);
    }

    private void cycleTwoSwitchOut(ActiveContour cycleTwoContour, List<XYPoint> lIn) {

        List<XYPoint> addToLOut2 = new ArrayList<>();
        List<XYPoint> addToLIn2 = new ArrayList<>();
        List<XYPoint> toRemoveFromLIn2 = new ArrayList<>();



        for (XYPoint xyPoint : lIn) {

            int x = xyPoint.getX();
            int y = xyPoint.getY();
            double newFiValue = new GaussianMask(3).applyMaskToPixel(cycleTwoContour.getContent(), x, y);
            if (newFiValue > 0) {
                fillSwitchOutLists(cycleTwoContour, addToLOut2, addToLIn2, toRemoveFromLIn2, xyPoint);
            }
        }

        cycleTwoContour.removeLIn(toRemoveFromLIn2);
        cycleTwoContour.addLOut(addToLOut2);
        cycleTwoContour.addLIn(addToLIn2);
    }

    private void switchIn(CustomImage customImage, ActiveContour cycleOneContour, List<XYPoint> lOut, int backgroundGrayAverage,
            int objectGrayAverage, double epsilon) {
        List<XYPoint> removeFromLOut = new ArrayList<>();
        List<XYPoint> addToLIn = new ArrayList<>();
        List<XYPoint> addToLOut = new ArrayList<>();

        for (XYPoint xyPoint : lOut) {
            if (!checkFdFunctionIsLowerThanEpsilon(xyPoint, customImage, backgroundGrayAverage, objectGrayAverage, epsilon)) {
                fillSwitchInLists(cycleOneContour, removeFromLOut, addToLIn, addToLOut, xyPoint);
            }
        }

        cycleOneContour.addLIn(addToLIn);
        cycleOneContour.removeLOut(removeFromLOut);
        cycleOneContour.addLOut(addToLOut);
    }

    private void switchOut(CustomImage customImage, ActiveContour cycleOneContour, List<XYPoint> lIn, int backgroundGrayAverage,
            int objectGrayAverage, double epsilon) {
        List<XYPoint> addToLOut2 = new ArrayList<>();
        List<XYPoint> addToLIn2 = new ArrayList<>();
        List<XYPoint> toRemoveFromLIn2 = new ArrayList<>();

        for (XYPoint xyPoint : lIn) {
            if (checkFdFunctionIsLowerThanEpsilon(xyPoint, customImage, backgroundGrayAverage, objectGrayAverage, epsilon)) {
                fillSwitchOutLists(cycleOneContour, addToLOut2, addToLIn2, toRemoveFromLIn2, xyPoint);
            }
        }

        cycleOneContour.removeLIn(toRemoveFromLIn2);
        cycleOneContour.addLOut(addToLOut2);
        cycleOneContour.addLIn(addToLIn2);
    }

    private void fillSwitchInLists(ActiveContour cycleOneContour, List<XYPoint> removeFromLOut, List<XYPoint> addToLIn, List<XYPoint> addToLOut,
            XYPoint xyPoint) {
        switchInStepOne(cycleOneContour, removeFromLOut, addToLIn, xyPoint);
        switchInStepTwo(cycleOneContour, addToLOut, xyPoint);
    }

    private void fillSwitchOutLists(ActiveContour cycleOneContour, List<XYPoint> addToLOut2, List<XYPoint> addToLIn2, List<XYPoint> toRemoveFromLIn2,
            XYPoint xyPoint) {
        switchOutStepOne(cycleOneContour, addToLOut2, toRemoveFromLIn2, xyPoint);
        switchOutStepTwo(cycleOneContour, addToLIn2, xyPoint);
    }

    private void switchInStepOne(ActiveContour cycleOneContour, List<XYPoint> removeFromLOut, List<XYPoint> addToLIn, XYPoint xyPoint) {
        removeFromLOut.add(xyPoint);
        addToLIn.add(xyPoint);
        cycleOneContour.updateFiValueForLInPoint(xyPoint);
    }

    private void switchOutStepOne(ActiveContour cycleOneContour, List<XYPoint> addToLOut2, List<XYPoint> toRemoveFromLIn2, XYPoint xyPoint) {
        toRemoveFromLIn2.add(xyPoint);
        addToLOut2.add(xyPoint);
        cycleOneContour.updateFiValueForLOutPoint(xyPoint);
    }

    private void switchInStepTwo(ActiveContour cycleOneContour, List<XYPoint> addToLOut, XYPoint xyPoint) {
        cycleOneContour.getNeighbors(xyPoint).stream()
                       .filter(neighbor -> cycleOneContour.hasValidPosition(neighbor.getX(), neighbor.getY()))
                       .filter(neighbor -> cycleOneContour.belongToBackground(neighbor))
                       .forEach(neighbor -> {
                           addToLOut.add(neighbor);
                           cycleOneContour.updateFiValueForLOutPoint(neighbor);
                       });
    }

    private void switchOutStepTwo(ActiveContour cycleOneContour, List<XYPoint> addToLIn2, XYPoint xyPoint) {
        cycleOneContour.getNeighbors(xyPoint).stream()
                       .filter(neighbor -> cycleOneContour.hasValidPosition(neighbor.getX(), neighbor.getY()))
                       .filter(neighbor -> cycleOneContour.belongToObject(neighbor))
                       .forEach(neighbor -> {
                           addToLIn2.add(neighbor);
                           cycleOneContour.updateFiValueForLInPoint(neighbor);
                       });
    }

    private boolean checkFdFunctionIsLowerThanEpsilon(XYPoint xyPoint, CustomImage customImage,
            int backgroundGrayAverage, int objectGrayAverage, double epsilon) {

        int imageAverageValue = customImage.getAverageValue(xyPoint.getX(), xyPoint.getY());
        if (FdFunctionMode.isClassic()) {
            return FdFunction.lowerThanZero(imageAverageValue, backgroundGrayAverage, objectGrayAverage, 0);
        }

        return FdFunction.greaterThanEpsilon(imageAverageValue, objectGrayAverage, epsilon);
    }
}
