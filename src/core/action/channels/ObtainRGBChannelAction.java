package core.action.channels;

import core.repository.ImageRepository;
import domain.customimage.CustomImage;
import domain.Channel;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.Optional;
import java.util.function.BiFunction;

public class ObtainRGBChannelAction {

    private final ImageRepository repository;

    public ObtainRGBChannelAction(ImageRepository repository) {
        this.repository = repository;
    }

    public Image execute(Channel channel) {

        Optional<CustomImage> currentImage = this.repository.getImage();
        if (!currentImage.isPresent()) {
            return new WritableImage(100, 100);
        }

        CustomImage image = currentImage.get();

        int width = image.getWidth();
        int height = image.getHeight();
        WritableImage writableImage = new WritableImage(width, height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        switch (channel) {
            case RED:
                getChannel(width, height, pixelWriter, functionRed(image));
                break;
            case GREEN:
                getChannel(width, height, pixelWriter, functionGreen(image));
                break;
            case BLUE:
                getChannel(width, height, pixelWriter, functionBlue(image));
                break;
        }

        return writableImage;
    }

    private void getChannel(int width, int height, PixelWriter pixelWriter, BiFunction<Integer, Integer, Color> channel) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixelWriter.setColor(x, y, channel.apply(x, y));
            }
        }
    }

    private BiFunction<Integer, Integer, Color> functionRed(CustomImage image) {
        return (x, y) -> {
            int red = image.getRChannelValue(x, y).intValue();
            return Color.rgb(red, 0, 0);
        };
    }

    private BiFunction<Integer, Integer, Color> functionGreen(CustomImage image) {
        return (x, y) -> {
            int green = image.getGChannelValue(x, y).intValue();
            return Color.rgb(0, green, 0);
        };
    }

    private BiFunction<Integer, Integer, Color> functionBlue(CustomImage image) {
        return (x, y) -> {
            int blue = image.getBChannelValue(x, y).intValue();
            return Color.rgb(0, 0, blue);
        };
    }

}
