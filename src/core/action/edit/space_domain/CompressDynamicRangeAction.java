package core.action.edit.space_domain;

import core.repository.ImageRepository;
import core.service.statistics.GrayLevelStatisticsService;
import domain.customimage.CustomImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.Optional;

public class CompressDynamicRangeAction {

    private final GrayLevelStatisticsService grayLevelStatisticsService;
    private final ImageRepository imageRepository;

    public CompressDynamicRangeAction(GrayLevelStatisticsService grayLevelStatisticsService, ImageRepository imageRepository) {
        this.grayLevelStatisticsService = grayLevelStatisticsService;
        this.imageRepository = imageRepository;
    }

    public Image execute() {

        Optional<CustomImage> optional = this.imageRepository.getImage();
        if (!optional.isPresent()) {
            return new WritableImage(300, 300);
        }

        Image image = SwingFXUtils.toFXImage(optional.get().getBufferedImage(), null);
        PixelReader reader = image.getPixelReader();
        WritableImage writableImage = new WritableImage((int) image.getWidth(), (int) image.getHeight());
        PixelWriter writer = writableImage.getPixelWriter();

        int max = this.grayLevelStatisticsService.calculateMaxGrayLevel(image);
        double c = this.calculateC(max);
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {

                int oldGrayLevel = (int) (reader.getColor(i, j).getRed() * 255);
                int newGrayLevel = (int) (c * Math.log(1 + oldGrayLevel));
                Color newColor = Color.rgb(newGrayLevel, newGrayLevel, newGrayLevel);
                writer.setColor(i, j, newColor);

            }
        }

        return writableImage;

    }

    public int[][] execute(int[][] channel) {
        int max = this.grayLevelStatisticsService.calculateMaxGrayLevel(channel);
        double c = this.calculateC(max);
        for (int i = 0; i < channel.length; i++) {
            for (int j = 0; j < channel[i].length; j++) {
                int oldGrayLevel = channel[i][j];
                int newGrayLevel = (int) (c * Math.log(1 + oldGrayLevel));
                channel[i][j] = newGrayLevel;
            }
        }
        return channel;
    }

    private double calculateC(int maxGrayLevel) {
        return (255 / (Math.log(1 + maxGrayLevel)));
    }

}
