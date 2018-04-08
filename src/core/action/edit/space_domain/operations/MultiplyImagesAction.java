
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
        //desplazo los valores para que el minimo sea cero
        this.imageOperationsService.displacePixelsValues(redChannelResultantValues);
        this.imageOperationsService.displacePixelsValues(greenChannelResultantValues);
        this.imageOperationsService.displacePixelsValues(blueChannelResultantValues);
        WritableImage resultantImage = new WritableImage((int) image1.getWidth(), (int) image2.getHeight());
        this.imageOperationsService.writeNewPixelsValuesInImage(redChannelResultantValues, greenChannelResultantValues, blueChannelResultantValues, resultantImage);
        return resultantImage;
    }

}

