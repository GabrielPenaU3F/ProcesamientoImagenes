package domain.automaticthreshold;

import javafx.scene.image.Image;

public class GlobalThresholdResult {


    private int iterations;
    private int threshold;
    private Image image;

    public GlobalThresholdResult(Image image, int iterations, int threshold){
        this.image = image;
        this.iterations = iterations;
        this.threshold = threshold;
    }

    public int getIterations() {
        return iterations;
    }


    public int getThreshold() {
        return threshold;
    }

    public Image getImage() {
        return image;
    }

}
