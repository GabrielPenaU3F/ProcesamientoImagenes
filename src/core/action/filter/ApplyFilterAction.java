package core.action.filter;

import core.action.filter.service.MaskService;
import domain.customimage.CustomImage;
import domain.filter.Mask;
import io.reactivex.subjects.PublishSubject;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class ApplyFilterAction {

    private final MaskService maskService;
    private final PublishSubject<Image> onModifiedImagePublishSubject; //Let's put it this name, so we can tell between this and the other ones (I've already created two or three publish subjects)

    public ApplyFilterAction(MaskService maskService, PublishSubject<Image> imagePublishSubject) {
        this.maskService = maskService;
        this.onModifiedImagePublishSubject = imagePublishSubject;
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

    private void applyMask(Mask mask, CustomImage customImage, WritableImage filteredImage, int x, int y) {
        if (Mask.Type.MEAN.equals(mask.getType())) {
            maskService.applyMeanMask(customImage, filteredImage, mask, x, y);
        }

        if (Mask.Type.MEDIAN.equals(mask.getType())) {
            maskService.applyMedianMask(customImage, filteredImage, mask, x, y);
        }

        if(Mask.Type.GAUSSIAN.equals(mask.getType())) {
            maskService.applyGaussianMask(customImage, filteredImage, mask, x, y);
        }
    }
}
