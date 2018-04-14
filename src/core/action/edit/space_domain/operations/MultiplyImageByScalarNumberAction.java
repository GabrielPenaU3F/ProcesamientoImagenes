package core.action.edit.space_domain.operations;

import core.action.edit.space_domain.CompressDynamicRangeAction;
import core.service.ImageOperationsService;
import domain.customimage.CustomImage;
import javafx.scene.image.Image;

public class MultiplyImageByScalarNumberAction {

    private ImageOperationsService imageOperationsService;
    private CompressDynamicRangeAction compressDynamicRangeAction;

    public MultiplyImageByScalarNumberAction(ImageOperationsService imageOperationsService,
                                             CompressDynamicRangeAction compressDynamicRangeAction){
        this.imageOperationsService = imageOperationsService;
        this.compressDynamicRangeAction = compressDynamicRangeAction;
    }

    public Image execute(CustomImage image, int scalarNumber){
        int[][] redChannelResultantValues = this.imageOperationsService.multiplyRedPixelsValuesWithScalarNumber(image, scalarNumber);
        int[][] greenChannelResultantValues = this.imageOperationsService.multiplyGreenPixelsValuesWithScalarNumber(image, scalarNumber);
        int[][] blueChannelResultantValues = this.imageOperationsService.multiplyBluePixelsValuesWithScalarNumber(image, scalarNumber);

        //Aplico transformacion de compresion del rango dinamico a cada canal
        redChannelResultantValues = this.compressDynamicRangeAction.execute(redChannelResultantValues);
        greenChannelResultantValues = this.compressDynamicRangeAction.execute(greenChannelResultantValues);
        blueChannelResultantValues = this.compressDynamicRangeAction.execute(blueChannelResultantValues);

        return this.imageOperationsService.writeNewPixelsValuesToImage(redChannelResultantValues,
                greenChannelResultantValues, blueChannelResultantValues);
    }
}
