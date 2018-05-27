package core.action.edgedetector.hough;

import domain.customimage.CustomImage;
import domain.hough.XYRCircle;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.HashMap;
import java.util.Map;

public class CircleHoughTransformAction {

    private final int radiusLowerBound = 0;
    private int radiusUpperBound;
    private final int xCenterLowerBound = 0;
    private int xCenterUpperBound;
    private final int yCenterLowerBound = 0;
    private int yCenterUpperBound;

    private Map<XYRCircle, Integer> parameterMatrix;

    public CustomImage execute(CustomImage originalImage, CustomImage edgedImage, int xCenterDivisions, int yCenterDivisions, int radiusDivisions, double tolerance) {

        this.radiusUpperBound = this.calculateMaximumRadius(edgedImage);
        this.xCenterUpperBound = edgedImage.getWidth();
        this.yCenterUpperBound = edgedImage.getHeight();

        this.createParameterMatrix(xCenterDivisions, yCenterDivisions, radiusDivisions);

        int[][] channel = edgedImage.getRedMatrix();

        for (int x=0; x < channel.length; x++) {
            for (int y=0; y < channel[x].length; y++) {

                if (channel[x][y] == 255) this.evaluateAllCircles(x,y, tolerance);

            }
        }

        int maximumVote = this.findMaximumVote();
        int threshold = (int)(maximumVote - (double)maximumVote*0.10);

        Map<XYRCircle, Integer> acceptedCircles = this.findAcceptedCircles(threshold);

        return this.drawCircles(originalImage, edgedImage.getWidth(), edgedImage.getHeight(), acceptedCircles);

    }

    private CustomImage drawCircles(CustomImage originalImage, Integer width, Integer height, Map<XYRCircle, Integer> acceptedCircles) {

        WritableImage image = new WritableImage(width, height);
        PixelWriter writer = image.getPixelWriter();

        for(int x=0; x < width; x++) {
            for (int y=0; y < height; y++) {
                writer.setColor(x,y, Color.rgb(originalImage.getAverageValue(x,y), originalImage.getAverageValue(x,y), originalImage.getAverageValue(x,y)));
            }
        }

        for(Map.Entry<XYRCircle, Integer>  entry : acceptedCircles.entrySet()) {

            for (int x=0; x < width; x++) {
                for (int y=0; y < height; y++) {
                    if (isInCircumference(x, y, entry.getKey())) writer.setColor(x,y, Color.RED);
                }
            }

        }

        return new CustomImage(image, "png");

    }

    private boolean isInCircumference(int x, int y, XYRCircle circle) {

        int xCenter = circle.getxCenter();
        int yCenter = circle.getyCenter();
        int radius = circle.getRadius();

        Circle outsideCircle = new Circle(xCenter, yCenter, radius);
        Circle insideCircle = new Circle(xCenter, yCenter, radius-1);

        return outsideCircle.contains(x,y) && !insideCircle.contains(x,y);
    }

    private Map<XYRCircle,Integer> findAcceptedCircles(int threshold) {

        Map<XYRCircle, Integer> acceptedCircles = new HashMap<>();

        for (Map.Entry<XYRCircle, Integer> entry : this.parameterMatrix.entrySet()) {
            if (entry.getValue() >= threshold) acceptedCircles.put(entry.getKey(), entry.getValue());
        }

        return acceptedCircles;
    }

    private int findMaximumVote() {
        int maximum = 0;
        for (Map.Entry<XYRCircle, Integer> entry : this.parameterMatrix.entrySet()) {
            if (entry.getValue() > maximum) maximum = entry.getValue();
        }
        return maximum;
    }

    //Este metodo actualiza la matriz para un cierto punto x y dado
    private void evaluateAllCircles(int x, int y, double tolerance) {

        Map<XYRCircle, Integer> updatedMatrix = new HashMap<>();

        for(Map.Entry<XYRCircle, Integer> entry : this.parameterMatrix.entrySet()) {

            XYRCircle currentXYR = entry.getKey();
            int currentXCenter = currentXYR.getxCenter();
            int currentYCenter = currentXYR.getyCenter();
            int currentRadius = currentXYR.getRadius();
            int actualVote = this.parameterMatrix.get(currentXYR);

            if (this.evaluateCircle(x,y, currentXCenter, currentYCenter, currentRadius, tolerance)) {
                updatedMatrix.put(currentXYR, actualVote+1);
            } else {
                updatedMatrix.put(currentXYR, actualVote);
            }
        }
        this.parameterMatrix = updatedMatrix;

    }


    private boolean evaluateCircle(int x, int y, int currentXCenter, int currentYCenter, int radius, double tolerance) {
        return Math.abs((Math.pow(x - currentXCenter, 2) + Math.pow(y - currentYCenter, 2) - Math.pow(radius, 2))) < tolerance*100;
    }

    private void createParameterMatrix(int xCenterDivisions, int yCenterDivisions, int radiusDivisions) {

        this.parameterMatrix = new HashMap<>();

        double xCenterPartitionLength = (this.xCenterUpperBound - this.xCenterLowerBound)/(double)xCenterDivisions;
        double yCenterPartitionLength = (this.yCenterUpperBound - this.yCenterLowerBound)/(double)yCenterDivisions;
        double radiusPartitionLength = (this.radiusUpperBound - this.radiusLowerBound)/(double)radiusDivisions;

        for (int i=0; i < xCenterDivisions; i++) {
            for (int j=0; j < yCenterDivisions; j++) {
                for (int k=0; k < radiusDivisions; k++) {

                    XYRCircle currentXYR = new XYRCircle((int)(this.xCenterLowerBound + i*xCenterPartitionLength), (int)(this.yCenterLowerBound + j*yCenterPartitionLength), (int)(this.radiusLowerBound + k*radiusPartitionLength));
                    this.parameterMatrix.put(currentXYR, 0);

                }
            }
        }
    }

    private int calculateMaximumRadius(CustomImage customImage) {
        if (customImage.getWidth() > customImage.getHeight()) return customImage.getHeight()/2;
        else return customImage.getWidth()/2;
    }


}
