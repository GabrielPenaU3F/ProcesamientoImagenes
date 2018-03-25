package domain.hsvimage;

public class HSVPixel {

    private Double hue; //The color location in the cone, measured in an angle from 0º to 360º
    private Double saturation; //The saturation of the color easured in a number ranged from 0 to 1. Saturation 0 is a pure tone, saturation 1 means white
    private Double value; //The height of the cone in the Z axis. Ranged between 0 and 1. 0 means black.

    public HSVPixel (Double hue, Double saturation, Double value) {
        if (hue >= -1 && hue <= 360) {this.hue = hue;} else throw new RuntimeException("Invalid hue"); //Here a -1 hue is actually not valid, I use it to state that it's an undefined value
        if (saturation >= 0 && saturation <= 1) {this.saturation = saturation;} else throw new RuntimeException("Invalid saturation");
        if (value >= 0 && value <= 1) {this.value = value;} else throw new RuntimeException("Invalid value");
    }

    public Double getHue() {
        return this.hue;
    }

    public Double getSaturation() {
        return this.saturation;
    }

    public Double getValue() {
        return this.value;
    }
}
