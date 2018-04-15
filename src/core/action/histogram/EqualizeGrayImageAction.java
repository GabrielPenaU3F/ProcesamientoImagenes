package core.action.histogram;

import core.repository.ImageRepository;
import core.service.generation.HistogramService;
import domain.Histogram;
import domain.customimage.CustomImage;
import io.reactivex.subjects.PublishSubject;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class EqualizeGrayImageAction {

    private final HistogramService histogramService;
    private final ImageRepository imageRepository;
    private final PublishSubject<Image> imagePublishSubject;

    public EqualizeGrayImageAction(HistogramService histogramService,
                                   ImageRepository imageRepository,
                                   PublishSubject<Image> imagePublishSubject) {
        this.histogramService = histogramService;
        this.imageRepository = imageRepository;
        this.imagePublishSubject = imagePublishSubject;
    }

    public Image execute(CustomImage customImage, int times) {

        Image equalizedImage = recursive(customImage, times);

        this.imagePublishSubject.onNext(equalizedImage);

        return equalizedImage;
    }

    private Image recursive(CustomImage customImage, int times) {

        Histogram histogram = this.histogramService.create(customImage);
        Image equalizedImage = equalizeImage(customImage, histogram);

        times--;

        if (times == 0) return equalizedImage;

        return recursive(new CustomImage(equalizedImage, customImage.getFormatString()), times);
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
