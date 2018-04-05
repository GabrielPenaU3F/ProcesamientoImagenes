package core.action.edit.space_domain.operations;

import core.service.ImageOperationsService;
import domain.customimage.CustomImage;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class SumImagesAction {

    CustomImage image1;
    CustomImage image2;
    ImageOperationsService imageOperationsService;

    public SumImagesAction(ImageOperationsService imageOperationsService){
        this.imageOperationsService = imageOperationsService;
    }

    public Image execute(CustomImage image1, CustomImage image2) {
        this.image1 = image1;
        this.image2 = image2;
        int resultantImageWidth = this.imageOperationsService.calculateResultantWidth(this.image1, this.image2);
        int resultantImageHeight = this.imageOperationsService.calculateResultantHeight(this.image1, this.image2);
        int [][][] auxImage1 = new int[resultantImageWidth][resultantImageHeight][3];
        int [][][] auxImage2 = new int[resultantImageWidth][resultantImageHeight][3];
        this.imageOperationsService.fillAuxImages(auxImage1, auxImage2, this.image1, this.image2);
        int[][][] resultantImageRepresentation = this.sumAuxImages(auxImage1, auxImage2, resultantImageWidth, resultantImageHeight);
        int resultantRedImageR = this.imageOperationsService.calculateR(resultantImageRepresentation, 0);//Red
        int resultantGreenImageR = this.imageOperationsService.calculateR(resultantImageRepresentation, 1);//Green
        int resultantBlueImageR = this.imageOperationsService.calculateR(resultantImageRepresentation, 2);//Blue
        WritableImage resultantImage = new WritableImage(resultantImageWidth, resultantImageHeight);
        this.imageOperationsService.writeNewPixelsValuesInImage(resultantImageRepresentation, resultantRedImageR, resultantGreenImageR, resultantBlueImageR, resultantImage);
        return resultantImage;
    }




    private int[][][] sumAuxImages(int[][][] auxImage1, int[][][] auxImage2, int width, int height){ //ambas matrices tienen el mismo tamanio
        int[][][] resultant_image = new int[width][height][3];
        for (int i = 0; i < auxImage1.length; i++){
            for (int j = 0; j < auxImage1[i].length; j++){
                for (int k = 0; k < auxImage1[i][j].length; k++){
                    resultant_image[i][j][k] = auxImage1[i][j][k] + auxImage2[i][j][k];
                }
            }
        }
        return resultant_image;
    }


}
