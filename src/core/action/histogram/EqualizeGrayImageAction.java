package core.action.histogram;

import core.repository.ImageRepository;
import core.service.generation.HistogramService;
import domain.Histogram;
import domain.customimage.CustomImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;
import java.util.Optional;

public class EqualizeGrayImageAction {

    private HistogramService histogramService;
    private final ImageRepository imageRepository;

    public EqualizeGrayImageAction(HistogramService histogramService, ImageRepository imageRepository) {
        this.histogramService = histogramService;
        this.imageRepository = imageRepository;
    }

    public Image execute() {
        CustomImage customImage = getCustomImage();
        Histogram histogram = this.histogramService.create(getCustomImage());
        Image equalizedImage = equalizeImage(customImage, histogram);

        CustomImage equalizedCustomImage = new CustomImage(SwingFXUtils.fromFXImage(equalizedImage, null), customImage.getFormatString());
        imageRepository.saveModifiedImage(equalizedCustomImage);

        return equalizedImage;
    }

    public Image executeTwice() {
        CustomImage customImage = getCustomImage();
        Histogram histogram = this.histogramService.create(customImage);
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(equalizeImage(customImage, histogram), null);

        CustomImage equalizedImage = new CustomImage(bufferedImage, customImage.getFormatString());
        Histogram histogramFromEqualizedImage = this.histogramService.create(equalizedImage);

        Image equalizedImage2 = equalizeImage(equalizedImage, histogramFromEqualizedImage);
        CustomImage equalizedCustomImage = new CustomImage(SwingFXUtils.fromFXImage(equalizedImage2, null), customImage.getFormatString());
        imageRepository.saveModifiedImage(equalizedCustomImage);

        return equalizedImage2;
    }

    private CustomImage getCustomImage() {
        Optional<CustomImage> imageOptional = this.imageRepository.getImage();
        return imageOptional.orElse(CustomImage.EMPTY);
    }

    private Image equalizeImage(CustomImage customImage, Histogram histogram) {
        WritableImage image = new WritableImage(customImage.getWidth(), customImage.getHeight());
        PixelWriter pixelWriter = image.getPixelWriter();

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {

                Double sK = cumulativeProbability(customImage, histogram, i, j);
                Double sMin = histogram.getMinValue();
                Integer sHat = applyTransform(sK, sMin);

                Color greyValue = Color.rgb(sHat, sHat, sHat);

                pixelWriter.setColor(i, j, greyValue);
            }
        }

        CustomImage updated = new CustomImage(SwingFXUtils.fromFXImage(image, null), customImage.getFormatString());
        this.imageRepository.saveModifiedImage(updated);

        return image;
    }

    private Double cumulativeProbability(CustomImage customImage, Histogram histogram, int x, int y) {
        Double value = 0.0;
        Integer limit = customImage.getAverageValue(x, y);
        for (int i1 = 0; i1 <= limit; i1++) {
            value += histogram.getValues()[i1];
        }

        return value / histogram.getTotalPixels();
    }

    private Integer applyTransform(Double s, Double smin) {
        return (int) (255 * (((s - smin) / (1 - smin))));
    }

}
