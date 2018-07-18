package core.service;

import domain.XYPoint;

public class BilateralFunctionsService {

    public double calculateCloseness(XYPoint center, XYPoint currentPosition, double closenessSigma) {
        double distance = this.calculateDistance(center, currentPosition);
        return Math.exp(-0.5 * Math.pow(distance / closenessSigma, 2));
    }

    public double calculateSimilarity(double centerValue, double currentValue, double similaritySigma) {
        double intensityDistance = Math.abs(currentValue - centerValue);
        return Math.exp(-0.5 * Math.pow(intensityDistance / similaritySigma, 2));
    }

    private double calculateDistance(XYPoint center, XYPoint currentPosition) {
        double xDistance = Math.abs(currentPosition.getX() - center.getX());
        double yDistance = Math.abs(currentPosition.getY() - center.getY());
        return Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2)); //Teorema de Pitágoras
    }

}
