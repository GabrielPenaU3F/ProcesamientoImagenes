package core.action.histogram;

import core.repository.ImageRepository;
import core.service.HistogramService;
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

        Optional<CustomImage> imageOptional = this.imageRepository.getImage();
        if (!imageOptional.isPresent())
            return SwingFXUtils.toFXImage(new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB), null);

        CustomImage customImage = imageOptional.get();
        WritableImage image = new WritableImage(customImage.getWidth(), customImage.getHeight());
        PixelWriter pixelWriter = image.getPixelWriter();

        double[] histogram = this.histogramService.create(customImage);

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int equalizedValue = calculateCumulativeProbability(customImage, histogram, i, j);
                Color colorRGB = Color.rgb(equalizedValue, equalizedValue, equalizedValue);

                pixelWriter.setColor(i, j, colorRGB);
            }
        }

        CustomImage updated = new CustomImage(SwingFXUtils.fromFXImage(image, null), customImage.getFormatString());
        this.imageRepository.saveModifiedImage(updated);

        return image;
    }

    private int calculateCumulativeProbability(CustomImage customImage, double[] histogram, int i, int j) {
        double value = 0;
        int limit = customImage.getAverageValue(i, j);
        for (int i1 = 0; i1 <= limit; i1++) {
            value += histogram[i1];
        }
        // It is already divided by the total of pixels
        return (int) (255 * value);
    }

}
