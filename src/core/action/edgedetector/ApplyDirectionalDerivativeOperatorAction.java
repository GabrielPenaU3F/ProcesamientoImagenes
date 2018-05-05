package core.action.edgedetector;

import core.service.ImageOperationsService;
import core.service.MatrixService;
import domain.customimage.ChannelMatrix;
import domain.customimage.CustomImage;
import domain.customimage.RGB;
import domain.mask.Mask;
import io.reactivex.subjects.PublishSubject;
import javafx.scene.image.Image;

public class ApplyDirectionalDerivativeOperatorAction {

    private final ImageOperationsService imageOperationsService;
    private final PublishSubject<Image> imagePublishSubject;
    private final MatrixService matrixService;

    public ApplyDirectionalDerivativeOperatorAction(ImageOperationsService imageOperationsService,
                                                    PublishSubject<Image> imagePublishSubject,
                                                    MatrixService matrixService) {

        this.imageOperationsService = imageOperationsService;
        this.imagePublishSubject = imagePublishSubject;
        this.matrixService = matrixService;
    }

    public void execute(CustomImage customImage,
                        Mask horizontalStraightMask,
                        Mask verticalStraightMask,
                        Mask mainDiagonalMask,
                        Mask secondaryDiagonalMask) {

        ChannelMatrix channelMatrix = applyMasks(customImage, horizontalStraightMask, verticalStraightMask,
                mainDiagonalMask, secondaryDiagonalMask);

        int[][] redChannel = channelMatrix.getRedChannel();
        int[][] greenChannel = channelMatrix.getGreenChannel();
        int[][] blueChannel = channelMatrix.getBlueChannel();
        Image resultantImage = this.matrixService.toImage(redChannel, greenChannel, blueChannel);

        imagePublishSubject.onNext(resultantImage);
    }

    private ChannelMatrix applyMasks(CustomImage image,
                                     Mask horizontalStraightMask, Mask verticalStraightMask,
                                     Mask mainDiagonalMask, Mask secondaryDiagonalMask) {

        Integer width = image.getWidth();
        Integer height = image.getHeight();
        ChannelMatrix channelMatrix = new ChannelMatrix(width, height);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                RGB horizontalRGB = horizontalStraightMask.applyMaskToPixel(image, x, y);
                RGB verticalRGB = verticalStraightMask.applyMaskToPixel(image, x, y);
                RGB mainDiagonalRGB = mainDiagonalMask.applyMaskToPixel(image, x, y);
                RGB secondaryDiagonalRGB = secondaryDiagonalMask.applyMaskToPixel(image, x, y);

                RGB maxRGB = getMaxRGB(horizontalRGB, verticalRGB, mainDiagonalRGB, secondaryDiagonalRGB);

                channelMatrix.setValue(x, y, maxRGB);
            }
        }

        return this.imageOperationsService.toValidImageMatrix(channelMatrix);
    }

    private RGB getMaxRGB(RGB horizontalRGB, RGB verticalRGB, RGB mainDiagonalRGB, RGB secondaryDiagonalRGB) {
        int maxRed = Integer.max(horizontalRGB.getRed(), Integer.max(verticalRGB.getRed(), Integer.max(mainDiagonalRGB.getRed(), secondaryDiagonalRGB.getRed())));
        int maxGreen = Integer.max(horizontalRGB.getGreen(), Integer.max(verticalRGB.getGreen(), Integer.max(mainDiagonalRGB.getGreen(), secondaryDiagonalRGB.getGreen())));
        int maxBlue = Integer.max(horizontalRGB.getBlue(), Integer.max(verticalRGB.getBlue(), Integer.max(mainDiagonalRGB.getBlue(), secondaryDiagonalRGB.getBlue())));

        return new RGB(maxRed, maxGreen, maxBlue);
    }
}
