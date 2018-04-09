package core.action.edit.space_domain.operations;

import core.service.ImageOperationsService;
import domain.customimage.CustomImage;
import javafx.scene.image.Image;

public class MultiplyImageWithScalarNumberAction {

    ImageOperationsService imageOperationsService;

    public MultiplyImageWithScalarNumberAction(ImageOperationsService imageOperationsService){
        this.imageOperationsService = imageOperationsService;
    }

    //modificar: Aplicar transformacion del rango dinamico
    public Image execute(CustomImage image, int scalarNumber){
        int[][] redChannelResultantValues = this.imageOperationsService.multiplyRedPixelsValuesWithScalarNumber(image, scalarNumber);
        int[][] greenChannelResultantValues = this.imageOperationsService.multiplyGreenPixelsValuesWithScalarNumber(image, scalarNumber);
        int[][] blueChannelResultantValues = this.imageOperationsService.multiplyBluePixelsValuesWithScalarNumber(image, scalarNumber);
        Image resultantImage = this.imageOperationsService.writeNewPixelsValuesInImage(redChannelResultantValues,
                greenChannelResultantValues, blueChannelResultantValues, (int) image.getWidth(), (int) image.getHeight());
        return resultantImage;
    }
}
