package core.action.edit.space_domain.operations;

import core.service.ImageOperationsService;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;


public class SumImagesAction {

    private ImageOperationsService imageOperationsService;

    public SumImagesAction(ImageOperationsService imageOperationsService){
        this.imageOperationsService = imageOperationsService;
    }

    public Image execute(Image image1, Image image2) {
        int[][] redChannelResultantValues = this.imageOperationsService.sumRedPixelsValues(image1, image2);
        int[][] greenChannelResultantValues = this.imageOperationsService.sumGreenPixelsValues(image1, image2);
        int[][] blueChannelResultantValues = this.imageOperationsService.sumBluePixelsValues(image1, image2);
        WritableImage resultantImage = new WritableImage((int) image1.getWidth(), (int) image2.getHeight());
        this.imageOperationsService.writeNewPixelsValuesInImage(redChannelResultantValues, greenChannelResultantValues, blueChannelResultantValues, resultantImage);
        return resultantImage;
    }

}
