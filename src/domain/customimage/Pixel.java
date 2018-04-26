package domain.customimage;

import javafx.scene.paint.Color;

import java.util.Objects;

public class Pixel {

    private final Integer x;
    private final Integer y;
    private final Color color;

    public Pixel(Integer x, Integer y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pixel pixel = (Pixel) o;
        return Objects.equals(x, pixel.x) &&
                Objects.equals(y, pixel.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}