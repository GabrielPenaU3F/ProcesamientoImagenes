package core.service;

import domain.customimage.CustomImage;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ImageOperationsService {

    public void fillAuxImages(int[][][] auxImage1, int[][][] auxImage2, CustomImage image1, CustomImage image2) {
        this.completeWithZero(auxImage1);
        this.completeWithZero(auxImage2);
        this.setChannelsPixelsValuesInAuxImage(auxImage1,image1);
        this.setChannelsPixelsValuesInAuxImage(auxImage2,image2);
    }

    public int calculateResultantWidth(CustomImage image1, CustomImage image2){
        int resultantImageWidth = 1;
        if (image1.getWidth() > image2.getWidth()) {
            resultantImageWidth = image1.getWidth();
        } else if (image2.getWidth() >= image1.getWidth()) {
            resultantImageWidth = image2.getWidth();
        }
        return resultantImageWidth;
    }

    public int calculateResultantHeight(CustomImage image1, CustomImage image2){
        int resultantImageHeight = 1;
        if (image1.getHeight() > image2.getHeight()) {
            resultantImageHeight = image1.getHeight();
        } else if (image2.getHeight() >= image1.getHeight()) {
            resultantImageHeight = image2.getHeight();
        }
        return resultantImageHeight;
    }

    public void completeWithZero(int[][][] auxImage){
        for (int i = 0; i < auxImage.length; i++){
            for (int j = 0; j < auxImage[i].length; j++){
                for (int k = 0; k < auxImage[i][j].length; k++){
                    auxImage[i][j][k] = 0;
                }
            }
        }
    }

    public void setChannelsPixelsValuesInAuxImage(int[][][] auxImage, CustomImage image){
        for (int i = 0; i < image.getWidth(); i++){
            for (int j = 0; j < image.getHeight(); j++){
                for (int k = 0; k < auxImage[i][j].length; k++){
                    if(k == 0){
                        auxImage[i][j][k] += image.getRChannelValue(i,j);
                    }
                    else if(k == 1){
                        auxImage[i][j][k] += image.getGChannelValue(i,j);
                    }
                    else if(k == 2){
                        auxImage[i][j][k] += image.getBChannelValue(i,j);
                    }
                }
            }
        }
    }

    public int calculateR(int[][][] auxImage, int channel){
        int actualR = 0;
        for (int i = 0; i < auxImage.length; i++){
            for (int j = 0; j < auxImage[i].length; j++){
                if(auxImage[i][j][channel] > actualR){
                    actualR = auxImage[i][j][channel];
                }
            }
        }
        return actualR;
    }

    public void writeNewPixelsValuesInImage(int[][][] resultantImageRepresentation, int imageRedR, int imageGreenR, int imageBlueR, WritableImage image){
        PixelWriter pixelWriter = image.getPixelWriter();
        for (int i = 0; i < resultantImageRepresentation.length; i++){
            for (int j = 0; j < resultantImageRepresentation[i].length; j++){
                int redPixelValue = 0;
                int greenPixelValue = 0;
                int bluePixelValue = 0;
                for (int k = 0; k < resultantImageRepresentation[i][j].length; k++){
                    if(k == 0){
                        double result = resultantImageRepresentation[i][j][k] * ((double) 255/imageRedR);
                        redPixelValue = (int) result;
                    }
                    else if(k == 1){
                        double result = resultantImageRepresentation[i][j][k] * ((double) 255/imageGreenR);
                        greenPixelValue = (int) result;
                    }
                    else if(k == 2){
                        double result = resultantImageRepresentation[i][j][k] * ((double) 255/imageBlueR);
                        bluePixelValue = (int) result;
                    }
                }
                Color color = Color.rgb(redPixelValue,greenPixelValue,bluePixelValue);
                pixelWriter.setColor(i,j,color);
            }
        }
    }
}
