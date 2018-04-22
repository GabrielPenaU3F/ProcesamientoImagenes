package core.action.filter;

import core.service.ImageOperationsService;
import core.service.MaskService;
import domain.customimage.CustomImage;
import domain.filter.Mask;
import io.reactivex.subjects.PublishSubject;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ApplyFilterAction {

    private final MaskService maskService;
    private final PublishSubject<Image> onModifiedImagePublishSubject;
    private final ImageOperationsService imageOperationsService;

    public ApplyFilterAction(MaskService maskService,
                             PublishSubject<Image> imagePublishSubject,
                             ImageOperationsService imageOperationsService) {
        this.maskService = maskService;
        this.onModifiedImagePublishSubject = imagePublishSubject;
        this.imageOperationsService = imageOperationsService;
    }

    public void execute(CustomImage customImage, Mask mask) {

        Integer width = customImage.getWidth();
        Integer height = customImage.getHeight();
        WritableImage filtered = new WritableImage(width, height);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                applyMask(mask, customImage, filtered, x, y);
            }
        }
        onModifiedImagePublishSubject.onNext(filtered);
    }

    public CustomImage executeHighPass(CustomImage customImage, Mask mask) {

        Integer width = customImage.getWidth();
        Integer height = customImage.getHeight();
        WritableImage filtered = new WritableImage(width, height);
        int[][] filteredMatrix = new int[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                applyMask(mask, customImage, filteredMatrix, x, y);
            }
        }
        this.imageOperationsService.displacePixelsValues(filteredMatrix);
        this.imageOperationsService.adjustScale(filteredMatrix);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int grayValue = filteredMatrix[x][y];
                Color color = Color.rgb(grayValue, grayValue, grayValue);
                filtered.getPixelWriter().setColor(x, y, color);
            }
        }
        CustomImage filteredCustomImage = new CustomImage(filtered, "png");
        return filteredCustomImage;
    }

    private void applyMask(Mask mask, CustomImage customImage, WritableImage filteredImage, int x, int y) {
        if (Mask.Type.MEAN.equals(mask.getType())) {
            maskService.applyMeanMask(customImage, filteredImage, mask, x, y);
        }

        if (Mask.Type.MEDIAN.equals(mask.getType())) {
            maskService.applyMedianMask(customImage, filteredImage, mask, x, y);
        }

        if (Mask.Type.GAUSSIAN.equals(mask.getType())) {
            maskService.applyGaussianMask(customImage, filteredImage, mask, x, y);
        }
    }

    private void applyMask(Mask mask, CustomImage customImage, int[][] filteredImageMatrix, int x, int y) {
        if (mask.getType().equals(Mask.Type.HIGH_PASS)) {
            maskService.applyHighPassMask(customImage, filteredImageMatrix, mask, x, y);
        }
    }
}
