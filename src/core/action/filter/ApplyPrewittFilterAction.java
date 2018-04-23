package core.action.filter;

import core.service.MaskService;
import core.service.ImageOperationsService;
import core.service.MatrixService;
import domain.customimage.CustomImage;
import domain.filter.Mask;
import domain.filter.XDerivativeMask;
import domain.filter.YDerivativeMask;
import io.reactivex.subjects.PublishSubject;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class ApplyPrewittFilterAction {

    private final MaskService maskService;
    private final ImageOperationsService imageOperationsService;
    private final MatrixService matrixService;
    private final PublishSubject<Image> imagePublishSubject;

    public ApplyPrewittFilterAction(MaskService maskService,
                                    ImageOperationsService imageOperationsService,
                                    MatrixService matrixService,
                                    PublishSubject<Image> imagePublishSubject) {
        this.maskService = maskService;
        this.matrixService = matrixService;
        this.imagePublishSubject = imagePublishSubject;
        this.imageOperationsService = imageOperationsService;
    }

    public void execute(CustomImage customImage) {

        //We calculate the partial X and Y derivative matrixes
        int[][] xDerivative = applyMask(customImage, new XDerivativeMask());
        int[][] yDerivative = applyMask(customImage, new YDerivativeMask());

        //We calculate the gradient by applying the formulae: sqrt(X^2 + Y^2)
        int[][] xDerivativeSquare = imageOperationsService.multiplyGrayPixelsValues(xDerivative, xDerivative);
        int[][] yDerivativeSquare = imageOperationsService.multiplyGrayPixelsValues(yDerivative, yDerivative);
        int[][] gradientMagnitude = imageOperationsService.sqrtGrayPixelsValues(imageOperationsService.sumGrayPixelsValues(xDerivativeSquare, yDerivativeSquare));

        //The result is normalized and written into an image
        Image resultantImage = matrixService.toGrayImage(imageOperationsService.toMatrixedImage(gradientMagnitude));

        imagePublishSubject.onNext(resultantImage);
    }

    private int[][] applyMask(CustomImage image, Mask mask) {
        Integer width = image.getWidth();
        Integer height = image.getHeight();
        int[][] matrixedImage = new int[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double gray = 0;
                int sizeMask = mask.getSize();

                for (int j = y - (sizeMask / 2); j <= y + (sizeMask / 2); j++) {
                    for (int i = x - (sizeMask / 2); i <= x + (sizeMask / 2); i++) {

                        int column = j + (sizeMask / 2) - y;
                        int row = i + (sizeMask / 2) - x;
                        double value = mask.getValue(column, row);

                        if (isPositionValid(width, height, i, j)) {
                            Color color = image.getPixelReader().getColor(i, j);
                            gray += 255 * color.getRed() * value;
                        }
                    }
                }

                matrixedImage[x][y] = (int) gray;
            }
        }

        return matrixedImage;
    }

    private boolean isPositionValid(int width, int height, int i, int j) {
        // Ignore the portion of the mask outside the image.
        return j >= 0 && j < height && i >= 0 && i < width;
    }
}
