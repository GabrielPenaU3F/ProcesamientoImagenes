package core.action.edit.space_domain.operations;

import core.action.edit.space_domain.CompressDynamicRangeAction;
import core.service.ImageOperationsService;
import domain.customimage.CustomImage;
import javafx.scene.image.Image;

public class MultiplyImageWithScalarNumberAction {

    private ImageOperationsService imageOperationsService;
    private CompressDynamicRangeAction compressDynamicRangeAction;

    public MultiplyImageWithScalarNumberAction(ImageOperationsService imageOperationsService,
                                               CompressDynamicRangeAction compressDynamicRangeAction){
        this.imageOperationsService = imageOperationsService;
        this.compressDynamicRangeAction = compressDynamicRangeAction;
    }

    public Image execute(CustomImage image, int scalarNumber){
        int[][] redChannelResultantValues = this.imageOperationsService.multiplyRedPixelsValuesWithScalarNumber(image, scalarNumber);
        int[][] greenChannelResultantValues = this.imageOperationsService.multiplyGreenPixelsValuesWithScalarNumber(image, scalarNumber);
        int[][] blueChannelResultantValues = this.imageOperationsService.multiplyBluePixelsValuesWithScalarNumber(image, scalarNumber);
        //Aplico transformacion de compresion del rango dinamico a cada canal
        redChannelResultantValues = this.compressDynamicRangeAction.executeForImageChannelMatrixialRepresentation(redChannelResultantValues);
        greenChannelResultantValues = this.compressDynamicRangeAction.executeForImageChannelMatrixialRepresentation(greenChannelResultantValues);
        blueChannelResultantValues = this.compressDynamicRangeAction.executeForImageChannelMatrixialRepresentation(blueChannelResultantValues);
        //
        Image resultantImage = this.imageOperationsService.writeNewPixelsValuesInImage(redChannelResultantValues,
                greenChannelResultantValues, blueChannelResultantValues, (int) image.getWidth(), (int) image.getHeight());
        return resultantImage;
    }
}
