package core.action.channels;

import core.repository.ImageRepository;
import core.service.transformations.TransformRGBtoHSVImageService;
import domain.Channel;
import domain.customimage.CustomImage;
import domain.hsvimage.HSVImage;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.Optional;
import java.util.function.BiFunction;

public class ObtainHSVChannelAction {


    private final ImageRepository repository;
    private final TransformRGBtoHSVImageService transformRGBtoHSVImageService;

    public ObtainHSVChannelAction(ImageRepository imageRepository, TransformRGBtoHSVImageService transformRGBtoHSVImageService) {
        this.repository = imageRepository;
        this.transformRGBtoHSVImageService = transformRGBtoHSVImageService;
    }

    public Image execute(Channel channel) {

        Optional<String> currentImage = this.repository.getCurrentImage();
        if (!currentImage.isPresent()) {
            return new WritableImage(100, 100);
        }

        CustomImage image = this.repository.get(currentImage.get());

        int width = image.getWidth();
        int height = image.getHeight();
        WritableImage writableImage = new WritableImage(width, height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        HSVImage hsVfromRGB = transformRGBtoHSVImageService.createHSVfromRGB(image);

        switch (channel) {
            case HUE:
                getChannel(width, height, pixelWriter, functionHue(hsVfromRGB));
                break;
            case SATURATION:
                getChannel(width, height, pixelWriter, functionSaturation(hsVfromRGB));
                break;
            case VALUE:
                getChannel(width, height, pixelWriter, functionValue(hsVfromRGB));
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

    private BiFunction<Integer, Integer, Color> functionHue(HSVImage hsvImage) {
        return (x, y) -> {
            double hue = hsvImage.getHue(x, y);
            return Color.hsb(hue, 1, 1);
        };
    }

    private BiFunction<Integer, Integer, Color> functionSaturation(HSVImage hsvImage) {
        return (x, y) -> {
            double saturation = hsvImage.getSaturation(x, y);
            return Color.hsb(1, saturation, 1);
        };
    }

    private BiFunction<Integer, Integer, Color> functionValue(HSVImage hsvImage) {
        return (x, y) -> {
            double value = hsvImage.getValue(x, y);
            return Color.hsb(1, 0, value);
        };
    }
}
