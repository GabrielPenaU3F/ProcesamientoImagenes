package core.provider;

import core.service.ImageOperationsService;
import core.service.ImageRawService;
import core.service.ModifyImageService;
import core.service.OpenFileService;
import core.service.generation.HistogramService;
import core.service.generation.ImageFigureService;
import core.service.generation.ImageGradientService;
import core.service.statistics.GrayLevelStatisticsService;
import core.service.statistics.RandomNumberGenerationService;
import core.service.*;
import core.service.transformations.TransformRGBtoHSVImageService;
import javafx.stage.FileChooser;

import java.util.Random;

public class ServiceProvider {

    public static OpenFileService provideOpenFileService() {
        return new OpenFileService(createFileChooser());
    }

    private static FileChooser createFileChooser() {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open customImage");
        fileChooser.getExtensionFilters()
                .addAll(new FileChooser.ExtensionFilter("All Images", "*.*"),
                        new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                        new FileChooser.ExtensionFilter("PNG", "*.png"),
                        new FileChooser.ExtensionFilter("RAW", "*.raw"),
                        new FileChooser.ExtensionFilter("PGM", "*.pgm"),
                        new FileChooser.ExtensionFilter("PPM", "*.ppm"),
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

    public static ImageOperationsService provideImageOperationsService() {
        return new ImageOperationsService(provideGrayLevelStatisticsService());
    }

    public static TransformRGBtoHSVImageService provideTransformRGBtoHSVImageService() {
        return new TransformRGBtoHSVImageService();
    }

    public static GrayLevelStatisticsService provideGrayLevelStatisticsService() {
        return new GrayLevelStatisticsService();
    }

    public static HistogramService provideHistogramService() {
        return new HistogramService();
    }

    public static RandomNumberGenerationService provideRandomNumberGenerationService() {
        return new RandomNumberGenerationService(new Random());
    }

    public static MatrixService provideMatrixService() {
        return new MatrixService();
    }

    public static ApplyThresholdService provideApplyThresholdService(){
        return new ApplyThresholdService(provideModifyImageService());
    }
}
