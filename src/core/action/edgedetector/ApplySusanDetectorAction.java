package core.action.edgedetector;

import domain.customimage.channel_matrix.RGBChannelMatrix;
import domain.customimage.CustomImage;
import domain.mask.Mask;

public class ApplySusanDetectorAction {

    public CustomImage execute(CustomImage customImage, Mask mask) {

        RGBChannelMatrix originalImageMatrix = new RGBChannelMatrix(customImage.getRedMatrix(), customImage.getBlueMatrix(), customImage.getGreenMatrix());
        RGBChannelMatrix maskResult = mask.apply(customImage);

        for (int i = 0; i < originalImageMatrix.getWidth(); i++) {
            for (int j = 0; j < originalImageMatrix.getHeight(); j++) {

                //edge case --> rojo
                if (maskResult.getRedChannel()[i][j] == 255) {
                    originalImageMatrix.getRedChannel()[i][j] = 255;
                    originalImageMatrix.getGreenChannel()[i][j] = 0;
                    originalImageMatrix.getBlueChannel()[i][j] = 0;
                }

                //corner case --> verde
                if (maskResult.getRedChannel()[i][j] == 150) {
                    originalImageMatrix.getRedChannel()[i][j] = 0;
                    originalImageMatrix.getGreenChannel()[i][j] = 255;
                    originalImageMatrix.getBlueChannel()[i][j] = 0;
                }

            }
        }
        return new CustomImage(originalImageMatrix, customImage.getFormatString());
    }
}
