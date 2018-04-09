package core.action.edit.space_domain;

import core.service.ImageOperationsService;
import domain.customimage.CustomImage;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class NormalizeImageAction {

    ImageOperationsService imageOperationsService;

    public NormalizeImageAction(ImageOperationsService imageOperationsService){
        this.imageOperationsService = imageOperationsService;
    }

    //la primer imagen es la que se normaliza
    public Image execute(CustomImage image1, CustomImage image2){
        int resultantImageWidth = this.imageOperationsService.calculateResultantWidth(image1, image2);
        int resultantImageHeight = this.imageOperationsService.calculateResultantHeight(image1, image2);
        WritableImage imageToNormalize = new WritableImage(resultantImageWidth, resultantImageHeight);
        imageToNormalize = this.imageOperationsService.fillImage(imageToNormalize, image1);
        return imageToNormalize;
    }
}
