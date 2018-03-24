package domain.hsvimage;


public class HSVImage {

    private final HSVPixel[][] hsvMatrix;

    public HSVImage(int width, int height) {
        this.hsvMatrix = new HSVPixel[width][height];
    }

    public void setPixel(int i, int j, HSVPixel pixel) { this.hsvMatrix[i][j] = pixel;}

    public double getHue(int i, int j) {
        return this.hsvMatrix[i][j].getHue();
    }

    public double getSaturation(int i, int j) {
        return this.hsvMatrix[i][j].getSaturation();

    }

    public double getValue(int i, int j) {
        return this.hsvMatrix[i][j].getValue();
    }
}
