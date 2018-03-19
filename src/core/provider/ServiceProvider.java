package core.provider;

import core.service.ModifyImageService;
import core.service.ImageRawService;
import core.service.OpenFileService;
import core.service.transformations.TransformRGBtoHSVImageService;
import core.service.*;
import javafx.stage.FileChooser;

public class ServiceProvider {

    public static OpenFileService provideOpenFileService() {
        return new OpenFileService(createFileChooser());
    }

    private static FileChooser createFileChooser() {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open customimage");
        fileChooser.getExtensionFilters()
                .addAll(new FileChooser.ExtensionFilter("All Images", "*.*"), new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                        new FileChooser.ExtensionFilter("PNG", "*.png"), new FileChooser.ExtensionFilter("RAW", "*.raw"),
                        new FileChooser.ExtensionFilter("PGM", "*.pgm"), new FileChooser.ExtensionFilter("PPM", "*.ppm"),
                        new FileChooser.ExtensionFilter("BMP", "*.bmp"));
        return fileChooser;
    }

    public static ImageRawService provideImageRawService() {
        return new ImageRawService();
    }

    public static ModifyImageService provideModifyImageService() {
        return new ModifyImageService();
    }

    public static ImageFigureService provideImageFigureService() {
        return new ImageFigureService();
    }

    public static ImageGradientService provideGradientService() {
        return new ImageGradientService();
    }

    public static TransformRGBtoHSVImageService provideTransformRGBtoHSVImageService() {
        return new TransformRGBtoHSVImageService();
    }
}
