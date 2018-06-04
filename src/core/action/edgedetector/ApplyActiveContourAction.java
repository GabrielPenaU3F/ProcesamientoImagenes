package core.action.edgedetector;

import java.util.ArrayList;
import java.util.List;

import domain.activecontour.ActiveContour;
import domain.activecontour.ContourCustomImage;
import domain.activecontour.FdFunction;
import domain.activecontour.FdFunctionMode;
import domain.activecontour.XYPoint;
import domain.customimage.CustomImage;

public class ApplyActiveContourAction {

    public ContourCustomImage execute(CustomImage customImage, ActiveContour activeContour, int steps, double epsilon) {
        return recursive(customImage, activeContour, steps, epsilon);
    }

    public ContourCustomImage executeAutomatically(CustomImage customImage, ActiveContour activeContour, double epsilon) {
        return recursive(customImage, activeContour, Integer.MAX_VALUE, epsilon);
    }

    private ContourCustomImage recursive(CustomImage customImage, ActiveContour activeContour, int steps, double epsilon) {

        ContourCustomImage contourCustomImage = applyActiveContour(customImage, activeContour, epsilon);

        steps--;
        if (steps == 0) {
            return contourCustomImage;
        }

        return recursive(contourCustomImage.getCustomImage(), contourCustomImage.getActiveContour(), steps, epsilon);
    }

    private ContourCustomImage applyActiveContour(CustomImage customImage, ActiveContour activeContour, double epsilon) {

        // Step 1
        List<XYPoint> lOut = activeContour.getlOut();
        List<XYPoint> lIn = activeContour.getlIn();
        int backgroundGrayAverage = activeContour.getBackgroundGrayAverage();
        int objectGrayAverage = activeContour.getObjectGrayAverage();

        // Step 2
        List<XYPoint> removeFromLOut = new ArrayList<>();
        List<XYPoint> addToLIn = new ArrayList<>();
        List<XYPoint> addToLOut = new ArrayList<>();

        for (XYPoint xyPoint : lOut) {

            if (!checkFdFunction(xyPoint, customImage, objectGrayAverage, backgroundGrayAverage, epsilon)) {
                removeFromLOut.add(xyPoint);
                addToLIn.add(xyPoint);
                activeContour.addLInToMatrix(xyPoint);

                activeContour.getNeighbors(xyPoint).stream()
                             .filter(neighbor -> activeContour.hasValidPosition(neighbor.getX(), neighbor.getY()))
                             .filter(neighbor -> activeContour.belongToBackground(neighbor))
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

        // Step 4
        List<XYPoint> addToLOut2 = new ArrayList<>();
        List<XYPoint> addToLIn2 = new ArrayList<>();
        List<XYPoint> toRemoveFromLIn2 = new ArrayList<>();

        for (XYPoint xyPoint : lIn) {

            if (checkFdFunction(xyPoint, customImage, objectGrayAverage, backgroundGrayAverage, epsilon)) {
                toRemoveFromLIn2.add(xyPoint);
                addToLOut2.add(xyPoint);
                activeContour.addLOutToMatrix(xyPoint);

                activeContour.getNeighbors(xyPoint).stream()
                             .filter(neighbor -> activeContour.hasValidPosition(neighbor.getX(), neighbor.getY()))
                             .filter(neighbor -> activeContour.belongToObject(neighbor))
                             .forEach(neighbor -> {
                                 addToLIn2.add(neighbor);
                                 activeContour.addLInToMatrix(neighbor);
                             });
            }
        }

        activeContour.removeLIn(toRemoveFromLIn2);
        activeContour.addLOut(addToLOut2);
        activeContour.addLIn(addToLIn2);

        // Step 5
        activeContour.moveInvalidLOutToBackground();

        return new ContourCustomImage(customImage, activeContour);
    }

    private boolean checkFdFunction(XYPoint xyPoint, CustomImage customImage, int objectGrayAverage, int backgroundGrayAverage, double epsilon) {
        int imageAverageValue = customImage.getAverageValue(xyPoint.getX(), xyPoint.getY());
        if(FdFunctionMode.isClassic()) {
            return FdFunction.classicLower(imageAverageValue, objectGrayAverage, backgroundGrayAverage);
        }

        return FdFunction.epsilonLower(imageAverageValue, objectGrayAverage, epsilon);
    }

    private double module(int value, int imageValue) {
        return Math.abs(value - imageValue);
    }
}
