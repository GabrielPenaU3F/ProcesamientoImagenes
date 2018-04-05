
package core.action.edit.space_domain.operations;

import core.action.edit.space_domain.NormalizeImageAction;
import core.service.ImageOperationsService;
import domain.customimage.CustomImage;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class MultiplyImagesAction {

    private CustomImage image1;
    private CustomImage image2;
    private ImageOperationsService imageOperationsService;
    private NormalizeImageAction normalizeImageAction;

    public MultiplyImagesAction(ImageOperationsService imageOperationsService, NormalizeImageAction normalizeImageAction){
        this.imageOperationsService = imageOperationsService;
        this.normalizeImageAction = normalizeImageAction;
    }

    public Image execute(CustomImage image1, CustomImage image2) {
        this.image1 = image1;
        this.image2 = image2;
        Image normalizedImage1 = this.normalizeImageAction.execute(this.image1, this.image2);
        Image normalizedImage2 = this.normalizeImageAction.execute(this.image2, this.image1);
        int[][] redChannelResultantValues = this.imageOperationsService.multiplyRedPixelsValues(normalizedImage1, normalizedImage2);
        int[][] greenChannelResultantValues = this.imageOperationsService.multiplyGreenPixelsValues(normalizedImage1, normalizedImage2);
        int[][] blueChannelResultantValues = this.imageOperationsService.multiplyBluePixelsValues(normalizedImage1, normalizedImage2);
        int resultantRedImageR = this.imageOperationsService.calculateR(redChannelResultantValues);
        int resultantGreenImageR = this.imageOperationsService.calculateR(greenChannelResultantValues);
        int resultantBlueImageR = this.imageOperationsService.calculateR(blueChannelResultantValues);
        WritableImage resultantImage = new WritableImage((int) normalizedImage1.getWidth(), (int) normalizedImage2.getHeight());
        this.imageOperationsService.writeNewPixelsValuesInImage(redChannelResultantValues, greenChannelResultantValues, blueChannelResultantValues, resultantRedImageR, resultantGreenImageR, resultantBlueImageR, resultantImage);
        return resultantImage;
    }

}

