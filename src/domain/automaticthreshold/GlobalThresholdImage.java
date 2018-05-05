package domain.automaticthreshold;

import javafx.scene.image.Image;

public class GlobalThresholdImage {


    private int iterations;
    private int threshold;
    private Image image;

    public GlobalThresholdImage(){
        this.iterations = 0;
        this.threshold = 0;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

}
