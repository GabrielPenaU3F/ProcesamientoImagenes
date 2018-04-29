package core.action.filter;

import core.service.ImageOperationsService;
import core.service.MatrixService;
import domain.customimage.ChannelMatrix;
import domain.customimage.CustomImage;
import domain.mask.Mask;
import io.reactivex.subjects.PublishSubject;
import javafx.scene.image.Image;

public class ApplyEdgeDetectorByGradientAction {

    private final ImageOperationsService imageOperationsService;
    private final PublishSubject<Image> imagePublishSubject;
    private final MatrixService matrixService;

    public ApplyEdgeDetectorByGradientAction(ImageOperationsService imageOperationsService,
                                             PublishSubject<Image> imagePublishSubject,
                                             MatrixService matrixService) {

        this.imageOperationsService = imageOperationsService;
        this.imagePublishSubject = imagePublishSubject;
        this.matrixService = matrixService;
    }

    public void execute(CustomImage customImage, Mask xDerivativeMask, Mask yDerivativeMask) {
        //We calculate the partial X and Y derivative matrixes
        ChannelMatrix xDerivateChannelMatrix = xDerivativeMask.apply(customImage);
        ChannelMatrix yDerivateChannelMatrix = yDerivativeMask.apply(customImage);

        //We calculate the gradient by applying the formulae: sqrt(X^2 + Y^2)
        ChannelMatrix xDerivativeSquare = this.imageOperationsService.multiplyChannelMatrixs(xDerivateChannelMatrix, xDerivateChannelMatrix);
        ChannelMatrix yDerivativeSquare = this.imageOperationsService.multiplyChannelMatrixs(yDerivateChannelMatrix, yDerivateChannelMatrix);
        ChannelMatrix gradientMagnitude = imageOperationsService.sqrtChannelMatrixs(imageOperationsService.sumChannelMatrixs(xDerivativeSquare, yDerivativeSquare));

        //The result is normalized and written into an image
        Image resultantImage = this.matrixService.toImage(gradientMagnitude.getRedChannel(), gradientMagnitude.getGreenChannel(), gradientMagnitude.getBlueChannel());

        imagePublishSubject.onNext(resultantImage);
    }
}
