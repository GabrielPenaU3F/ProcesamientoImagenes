package core.action.edgedetector.hough;

import domain.customimage.CustomImage;
import domain.hough.RhoThetaLine;
import domain.hough.XYPoint;
import domain.hough.XYRCircle;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

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

    public CustomImage execute(CustomImage edgedImage, int xCenterDivisions, int yCenterDivisions, int radiusDivisions, double tolerance) {

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

        return this.drawCircles(edgedImage.getWidth(), edgedImage.getHeight(), acceptedCircles);

    }

    private CustomImage drawCircles(Integer width, Integer height, Map<XYRCircle, Integer> acceptedCircles) {

        WritableImage image = new WritableImage(width, height);
        PixelWriter writer = image.getPixelWriter();

        for(int x=0; x < width; x++) {
            for (int y=0; y < height; y++) {
                writer.setColor(x,y, Color.BLACK);
            }
        }

        for(Map.Entry<XYRCircle, Integer>  entry : acceptedCircles.entrySet()) {

            int currentXCenter = entry.getKey().getxCenter();
            int currentYCenter = entry.getKey().getyCenter();
            int currentRadius = entry.getKey().getRadius();

            Circle circle = new Circle(currentXCenter, currentYCenter, currentRadius);

            for (int x=0; x < width; x++) {
                for (int y=0; y < height; y++) {

                    if (circle.contains(x,y)) writer.setColor(x,y, Color.WHITE);

                }
            }

        }

        return new CustomImage(image, "png");

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
        return (Math.pow(x - currentXCenter, 2) + Math.pow(y - currentYCenter, 2) - radius) < tolerance;
    }

    private void createParameterMatrix(int xCenterDivisions, int yCenterDivisions, int radiusDivisions) {

        this.parameterMatrix = new HashMap<>();

        double xCenterPartitionLength = (this.xCenterUpperBound - this.xCenterLowerBound)/(double)xCenterDivisions;
        double yCenterPartitionLength = (this.yCenterUpperBound - this.yCenterLowerBound)/(double)yCenterDivisions;
        double radiusPartitionLength = (this.radiusUpperBound - this.radiusLowerBound/(double)radiusDivisions);

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
        if (customImage.getWidth() > customImage.getHeight()) return customImage.getHeight();
        else return customImage.getWidth();
    }


}
