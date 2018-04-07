package core.action.edit.space_domain.operations;

import core.service.ImageOperationsService;
import domain.customimage.CustomImage;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class MultiplyImageWithScalarNumberAction {

    ImageOperationsService imageOperationsService;

    public MultiplyImageWithScalarNumberAction(ImageOperationsService imageOperationsService){
        this.imageOperationsService = imageOperationsService;
    }

    public Image execute(CustomImage image, int scalarNumber){
        WritableImage resultantImage = new WritableImage(image.getWidth(),image.getHeight());
        int[][] redChannelResultantValues = this.imageOperationsService.multiplyRedPixelsValuesWithScalarNumber(image, scalarNumber);
        int[][] greenChannelResultantValues = this.imageOperationsService.multiplyGreenPixelsValuesWithScalarNumber(image, scalarNumber);
        int[][] blueChannelResultantValues = this.imageOperationsService.multiplyBluePixelsValuesWithScalarNumber(image, scalarNumber);
        this.imageOperationsService.writeNewPixelsValuesInImage(redChannelResultantValues, greenChannelResultantValues, blueChannelResultantValues, resultantImage);
        return resultantImage;
    }
}
