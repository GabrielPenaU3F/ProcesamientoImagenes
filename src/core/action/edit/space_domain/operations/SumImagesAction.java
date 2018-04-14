package core.action.edit.space_domain.operations;

import core.service.ImageOperationsService;
import javafx.scene.image.Image;

public class SumImagesAction {

    private ImageOperationsService imageOperationsService;

    public SumImagesAction(ImageOperationsService imageOperationsService){
        this.imageOperationsService = imageOperationsService;
    }

    public Image execute(Image image1, Image image2) {
        int[][] redChannelResultantValues = this.imageOperationsService.sumRedPixelsValues(image1, image2);
        int[][] greenChannelResultantValues = this.imageOperationsService.sumGreenPixelsValues(image1, image2);
        int[][] blueChannelResultantValues = this.imageOperationsService.sumBluePixelsValues(image1, image2);
        return this.imageOperationsService.writeNewPixelsValuesToImage(redChannelResultantValues,
                greenChannelResultantValues, blueChannelResultantValues);
    }

}
