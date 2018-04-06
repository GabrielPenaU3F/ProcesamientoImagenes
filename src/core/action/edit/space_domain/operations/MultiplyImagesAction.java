
package core.action.edit.space_domain.operations;

import core.service.ImageOperationsService;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class MultiplyImagesAction {

    private ImageOperationsService imageOperationsService;

    public MultiplyImagesAction(ImageOperationsService imageOperationsService){
        this.imageOperationsService = imageOperationsService;
    }

    public Image execute(Image image1, Image image2) {
        int[][] redChannelResultantValues = this.imageOperationsService.multiplyRedPixelsValues(image1, image2);
        int[][] greenChannelResultantValues = this.imageOperationsService.multiplyGreenPixelsValues(image1, image2);
        int[][] blueChannelResultantValues = this.imageOperationsService.multiplyBluePixelsValues(image1, image2);
        int resultantRedImageR = this.imageOperationsService.calculateR(redChannelResultantValues);
        int resultantGreenImageR = this.imageOperationsService.calculateR(greenChannelResultantValues);
        int resultantBlueImageR = this.imageOperationsService.calculateR(blueChannelResultantValues);
        WritableImage resultantImage = new WritableImage((int) image1.getWidth(), (int) image2.getHeight());
        this.imageOperationsService.writeNewPixelsValuesInImage(redChannelResultantValues, greenChannelResultantValues, blueChannelResultantValues, resultantRedImageR, resultantGreenImageR, resultantBlueImageR, resultantImage);
        return resultantImage;
    }

}

