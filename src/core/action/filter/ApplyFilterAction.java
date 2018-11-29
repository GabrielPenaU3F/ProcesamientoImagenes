package core.action.filter;

import core.service.ImageOperationsService;
import domain.customimage.channel_matrix.RGBChannelMatrix;
import domain.customimage.CustomImage;
import domain.mask.Mask;
import io.reactivex.subjects.PublishSubject;
import javafx.scene.image.Image;

public class ApplyFilterAction {

    private final PublishSubject<Image> onModifiedImagePublishSubject;
    private final ImageOperationsService imageOperationsService;

    public ApplyFilterAction(PublishSubject<Image> imagePublishSubject,
                             ImageOperationsService imageOperationsService) {

        this.onModifiedImagePublishSubject = imagePublishSubject;
        this.imageOperationsService = imageOperationsService;
    }

    public CustomImage execute(CustomImage customImage, Mask mask) {
        RGBChannelMatrix appliedMask = mask.apply(customImage);
        RGBChannelMatrix validImageMatrix = this.imageOperationsService.toValidImageMatrix(appliedMask);
        CustomImage image = new CustomImage(validImageMatrix, customImage.getFormatString());

        onModifiedImagePublishSubject.onNext(image.toFXImage());

        return image;
    }
}
