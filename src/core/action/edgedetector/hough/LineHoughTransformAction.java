package core.action.edgedetector.hough;

import domain.customimage.CustomImage;
import domain.hough.RhoThetaLine;
import domain.hough.XYPoint;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import java.util.HashMap;
import java.util.Map;

public class LineHoughTransformAction {

    private final int thetaLowerBound = -90;
    private final int thetaUpperBound = 90;
    private double rhoLowerBound;
    private double rhoUpperBound;

    private Map<RhoThetaLine, Integer> parameterMatrix;

    public CustomImage execute(CustomImage canniedImage, int rhoDivisions, int thetaDivisions, double tolerance) {

        double diagonal = this.calculateDiagonal(canniedImage.getWidth(), canniedImage.getHeight());
        this.rhoLowerBound = -diagonal;
        this.rhoUpperBound = diagonal;
        this.createParameterMatrix(rhoDivisions, thetaDivisions);

        int[][] channel = canniedImage.getRedMatrix();
        
        for (int x=0; x < channel.length; x++) {
            for (int y=0; y < channel[x].length; y++) {
                
                if (channel[x][y] == 255) this.evaluateAllLines(x,y, tolerance);
                
            }
        }

        int maximumVote = this.findMaximumVote();
        int threshold = (int)(maximumVote - (double)maximumVote*0.10);

        Map<RhoThetaLine, Integer> acceptedLines = this.findAcceptedLines(threshold);

        return this.drawLines(canniedImage.getWidth(), canniedImage.getHeight(), acceptedLines);

    }

    private CustomImage drawLines(Integer width, Integer height, Map<RhoThetaLine, Integer> acceptedLines) {

        WritableImage image = new WritableImage(width, height);
        PixelWriter writer = image.getPixelWriter();

        for(int x=0; x < width; x++) {
            for (int y=0; y < height; y++) {
                writer.setColor(x,y, Color.BLACK);
            }
        }

        for(Map.Entry<RhoThetaLine, Integer>  entry : acceptedLines.entrySet()) {

            double currentRho = entry.getKey().getRho();
            double currentTheta = entry.getKey().getTheta();

            XYPoint startPoint = this.getLineStartPoint(currentRho,currentTheta, height);
            XYPoint endPoint = this.getLineEndPoint(currentRho, currentTheta, height, width);

            Line line = new Line(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());

            for (int x=0; x < width; x++) {
                for (int y=0; y < height; y++) {

                    if (line.contains(x,y)) writer.setColor(x,y, Color.WHITE);

                }
            }

        }

        return new CustomImage(image, "png");

    }

    private XYPoint getLineEndPoint(double currentRho, double currentTheta, int imageHeight, int imageWidth) {

        //Vertical line
        if (currentTheta == 0) {
            return new XYPoint((int)currentRho, imageHeight-1);
        }

        double intercept = this.formatTopleftIntercept(this.getIntercept(currentRho, currentTheta), imageHeight);
        double slope = this.getSlope(currentTheta);
        double rightBoundCross = slope*imageWidth + intercept;

        //The line is decreasing and the segment 'ends' on the lower image bound, hence the formulae for x when y=L
        //x = (L-b)/m, y=L
        if (rightBoundCross > imageHeight && slope < 0) {
            return new XYPoint((int)((imageHeight - intercept)/slope), imageHeight);
        }

        //The line is increasing and the segment 'ends' on the upper image bound, hence the formulae for x when y=0
        //x = -b/m, y=0
        if (rightBoundCross < 0 && slope > 0) {
            return new XYPoint((int)(-intercept/slope),0);
        }

        //Any other case the segment 'ends' on the right image bound, and y is the right bound crossing
        return new XYPoint(imageWidth, (int)rightBoundCross);

    }

    private XYPoint getLineStartPoint(double currentRho, double currentTheta, int imageHeight) {

        //Vertical line
        if (currentTheta == 0) {
            return new XYPoint((int)currentRho, 0);
        }

        double intercept = this.formatTopleftIntercept(this.getIntercept(currentRho, currentTheta), imageHeight);
        double slope = this.getSlope(currentTheta);

        //The line is decreasing and the segment 'starts' on the upper image bound, hence the formulae for x when y=0
        //x = -b/m, y=0
        if (intercept < 0 && slope < 0) {
            return new XYPoint((int)(-intercept/slope),0);
        }

        //The line is increasing and the segment 'starts' on the lower image bound, hence the formulae for x when y=L
        //x = (L-b)/m, y=L
        if (intercept > imageHeight && slope > 0) {
            return new XYPoint((int)((imageHeight - intercept)/slope), imageHeight);
        }

        //Any other case the segment 'starts' on the left image bound, and y is the intercept
        return new XYPoint(0, (int)intercept);

    }

    private double formatTopleftIntercept(double intercept, int height) {
        //Porque el origen de coordenadas esta ahora en la esquina superior izquierda
        return height - intercept;
    }

    private double getSlope(double theta) {
        return -1.0/Math.tan(Math.toRadians(theta)); //Recordar que el arco tangente de theta me da la pendiente de la semirrecta perpendicular a la recta de interes. Entonces, esta tiene pendiente opuesta e inversa.
    }

    private double getIntercept(double rho, double theta) {
        return rho/Math.sin(Math.toRadians(theta)); //De esta forma se calcula la ordenada al origen
    }

    private Map<RhoThetaLine,Integer> findAcceptedLines(int threshold) {

        Map<RhoThetaLine, Integer> acceptedLines = new HashMap<>();

        for (Map.Entry<RhoThetaLine, Integer> entry : this.parameterMatrix.entrySet()) {
            if (entry.getValue() >= threshold) acceptedLines.put(entry.getKey(), entry.getValue());
        }

        return acceptedLines;
    }

    private int findMaximumVote() {
        int maximum = 0;
        for (Map.Entry<RhoThetaLine, Integer> entry : this.parameterMatrix.entrySet()) {
            if (entry.getValue() > maximum) maximum = entry.getValue();
        }
        return maximum;
    }

    private void createParameterMatrix(int rhoDivisions, int thetaDivisions) {

        this.parameterMatrix = new HashMap<>();

        double rhoPartitionLength = (this.rhoUpperBound - this.rhoLowerBound)/(double)rhoDivisions;
        double thetaPartitionLength = (this.thetaUpperBound - this.thetaLowerBound)/(double)thetaDivisions;

        for (int i=0; i < rhoDivisions; i++) {
            for (int j=0; j < thetaDivisions; j++) {

                RhoThetaLine currentRhoTheta = new RhoThetaLine(this.rhoLowerBound + i*rhoPartitionLength, this.thetaLowerBound + j*thetaPartitionLength);
                this.parameterMatrix.put(currentRhoTheta, 0);

            }
        }

    }

    private double calculateDiagonal(Integer width, Integer height) {
        return Math.sqrt(Math.pow(width,2) + Math.pow(height,2));
    }

    //Este metodo actualiza la matriz para un cierto punto x y dado
    private void evaluateAllLines(int x, int y, double tolerance) {

        Map<RhoThetaLine, Integer> updatedMatrix = new HashMap<>();
        
        for(Map.Entry<RhoThetaLine, Integer> entry : this.parameterMatrix.entrySet()) {

            RhoThetaLine currentRhoTheta = entry.getKey();
            double currentRho = currentRhoTheta.getRho();
            double currentTheta = currentRhoTheta.getTheta();
            int actualVote = this.parameterMatrix.get(currentRhoTheta);

            if (this.evaluateLine(x,y, currentRho, currentTheta, tolerance)) {
                updatedMatrix.put(currentRhoTheta, actualVote+1);
            } else {
                updatedMatrix.put(currentRhoTheta, actualVote);
            }
        }
        this.parameterMatrix = updatedMatrix;

    }

    private boolean evaluateLine(int x, int y, double currentRho, double currentTheta, double tolerance) {
        return Math.abs((x*Math.cos(Math.toRadians(currentTheta)) + y*Math.sin(Math.toRadians(currentTheta)) - currentRho)) < tolerance;
    }
}
