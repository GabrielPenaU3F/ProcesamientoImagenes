package core.action.filter;

import core.action.filter.service.MaskService;
import domain.customimage.CustomImage;
import domain.filter.Mask;
import io.reactivex.subjects.PublishSubject;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class ApplyFilterAction {

    private final MaskService maskService;
    private final PublishSubject<Image> imagePublishSubject;

    public ApplyFilterAction(MaskService maskService, PublishSubject<Image> imagePublishSubject) {
        this.maskService = maskService;
        this.imagePublishSubject = imagePublishSubject;
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
        imagePublishSubject.onNext(filtered);
    }

    private void applyMask(Mask mask, CustomImage customImage, WritableImage filteredImage, int x, int y) {

        switch (mask.getType()) {
            case MEDIA:
                maskService.applyMediaMask(customImage, filteredImage, mask, x, y);
        }
    }
}
