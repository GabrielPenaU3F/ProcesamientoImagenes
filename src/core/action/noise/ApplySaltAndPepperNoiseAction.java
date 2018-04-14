package core.action.noise;

import core.service.statistics.RandomNumberGenerationService;
import domain.customimage.CustomImage;
import io.reactivex.subjects.PublishSubject;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ApplySaltAndPepperNoiseAction {

    private final RandomNumberGenerationService randomNumberGenerationService;
    private final PublishSubject<Image> imagePublishSubject;

    public ApplySaltAndPepperNoiseAction(RandomNumberGenerationService randomNumberGenerationService,
                                         PublishSubject<Image> imagePublishSubject) {
        this.randomNumberGenerationService = randomNumberGenerationService;
        this.imagePublishSubject = imagePublishSubject;
    }

    public Image execute(CustomImage customImage, Double pixelToContaminatePercent, Double p0, Double p1) {

        WritableImage imageWithNoise = SwingFXUtils.toFXImage(customImage.getBufferedImage(), null);
        PixelReader originalPixelReader = customImage.getPixelReader();
        PixelWriter imageWithNoisePixelWriter = imageWithNoise.getPixelWriter();

        int totalPixels = customImage.getWidth() * customImage.getHeight();

        Integer totalPixelsToContaminate = (int) (totalPixels * pixelToContaminatePercent / 100);

        customImage.pickNRandomPixels(totalPixelsToContaminate)
                .forEach(pixel -> applyNoise(originalPixelReader, imageWithNoisePixelWriter, p0 * 100, p1 * 100, pixel.getX(), pixel.getY()));

        imagePublishSubject.onNext(imageWithNoise);

        return imageWithNoise;
    }

    private void applyNoise(PixelReader pixelReader, PixelWriter pixelWriter, double p0, double p1, int x, int y) {

        int randomValue = randomNumberGenerationService.withUniformDistribution(0, 100);

        if (randomValue <= p0) {
            pixelWriter.setColor(x, y, Color.rgb(0, 0, 0));
        } else if (randomValue >= p1) {
            pixelWriter.setColor(x, y, Color.rgb(255, 255, 255));
        } else {
            pixelWriter.setColor(x, y, pixelReader.getColor(x, y));
        }
    }
}
