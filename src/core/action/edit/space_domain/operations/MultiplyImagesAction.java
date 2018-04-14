
package core.action.edit.space_domain.operations;

import core.service.ImageOperationsService;
import javafx.scene.image.Image;

public class MultiplyImagesAction {

    private ImageOperationsService imageOperationsService;

    public MultiplyImagesAction(ImageOperationsService imageOperationsService){
        this.imageOperationsService = imageOperationsService;
    }

    public Image execute(Image image1, Image image2) {
        int[][] redChannelResultantValues = this.imageOperationsService.multiplyRedPixelsValues(image1, image2);
        int[][] greenChannelResultantValues = this.imageOperationsService.multiplyGreenPixelsValues(image1, image2);
        int[][] blueChannelResultantValues = this.imageOperationsService.multiplyBluePixelsValues(image1, image2);
        return this.imageOperationsService.writeNewPixelsValuesInImage(redChannelResultantValues,
                greenChannelResultantValues, blueChannelResultantValues);
    }

}

