package domain.automaticthreshold;

import javafx.scene.image.Image;

public class OtsuThresholdResult {

    private Image image;
    private int threshold;

    public OtsuThresholdResult(Image image, int threshold){
        this.image = image;
        this.threshold = threshold;
    }

    public Image getImage() {
        return image;
    }

    public int getThreshold() {
        return threshold;
    }
}
